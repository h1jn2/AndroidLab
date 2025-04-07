package com.example.ch6.section6;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ch6.R;

import java.util.ArrayList;

// custom view 개발자 입장의 코드
// 동일한 화면을 출력하는 api 뷰가 없어서 직접 그려야함
// 최상위 View 상속으로 개발
public class MyPlusMinusView extends View {
    Context context;
    int value;
    Bitmap plusBitmap;
    Bitmap minusBitmap;
    Rect plusRect;
    Rect minusRect;
    int textColor;
    // 뷰의 이벤트를 전파받기 위해서 등록한 핸들러를 담는 곳
    ArrayList<OnMyChangeListener> listeners;

    public MyPlusMinusView(Context context) {
        super(context);
        this.context = context;
        init(null);
    }

    public MyPlusMinusView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs);
    }

    public MyPlusMinusView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(attrs);
    }

    // 3개의 생성자 동일 코드
    private void init(AttributeSet attrs) {
        // 드로잉 할 이미지 먼저 준비
        plusBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.plus);
        minusBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.minus);

        // 이미지 그리기 위한 사각형 정보 준비
        plusRect = new Rect(10, 10, 210, 210);
        minusRect = new Rect(400, 10, 600, 210);

        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MyView);
            textColor = array.getColor(R.styleable.MyView_customTextColor, Color.RED);
        }
        listeners = new ArrayList<>();
    }
    // 액태비티에서 이벤트 핸들러 등록하기 위해 호출
    public void setOnMyChangeListener(OnMyChangeListener listener) {
        listeners.add(listener);
    }
    // 뷰의 사이즈를 결정하기 위해 자동 호출
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 액티비티 개발자가 지정한 사이즈 값을 참조해서 계산
        // 액티비티에서 설정한 사이즈 정보 획득
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = 0;
        int height = 0;

        if (widthMode == MeasureSpec.AT_MOST) {
            width = 700;
        } else if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        }

        if (heightMode == MeasureSpec.AT_MOST) {
            height = 250;
        } else if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        }
        // 어떻게 사이즈를 결정하든 최종 setMesasureDimension(width, height)
        // 호출해서 알려준 사이즈가 뷰의 사이즈가 됨
        setMeasuredDimension(width, height);
    }

    // 유저 이벤트 처리 -> 터치 이벤트 처리

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        // 유저가 터치한 지점이 어떤 이미지인 지
        if (plusRect.contains(x, y) && event.getAction() == MotionEvent.ACTION_DOWN) {
            value++;
            invalidate();   // 뷰의 화면이 다시 그려져야 한다는 신호 -> onDraw() 자동 호출
            // 액티비티의 이벤트 핸들러도 실행
            for (OnMyChangeListener listener: listeners) {
                listener.onChange(value);
            }
            return true;
        } else if (minusRect.contains(x, y) && event.getAction() == MotionEvent.ACTION_DOWN) {
            value--;
            invalidate();   // 뷰의 화면이 다시 그려져야 한다는 신호 -> onDraw() 자동 호출
            // 액티비티의 이벤트 핸들러도 실행
            for (OnMyChangeListener listener: listeners) {
                listener.onChange(value);
            }
            return true;
        }
        return false;
    }

    // 최초에 한번 호출
    // invalidate() 호출될 때마다 자동 호출
    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        // 뷰 전체를 지정한 칼라로 칠함 -> 뷰 내용을 지움
        canvas.drawColor(Color.alpha(Color.CYAN));

        Rect plusR = new Rect(0, 0, plusBitmap.getWidth(), plusBitmap.getHeight());
        Rect minusR = new Rect(0, 0, minusBitmap.getWidth(), minusBitmap.getHeight());

        // 그리기 옵션
        Paint paint = new Paint();

        // 이미지 그리기
        canvas.drawBitmap(plusBitmap, plusR, plusRect, null);

        // 문자열 그리기
        paint.setTextSize(80);
        paint.setColor(textColor);
        canvas.drawText(String.valueOf(value), 260, 150, paint);

        canvas.drawBitmap(minusBitmap, minusR, minusRect, null);
    }
}
