package com.example.ch10;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ch10.databinding.ActivityTest2Binding;

public class Test2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        ActivityTest2Binding binding = ActivityTest2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.insertButton.setOnClickListener(view -> {
            // 유저 입력 데이터 획득
            String name = binding.nameInputView.getText().toString();
            String address = binding.addressInputView.getText().toString();

            // 테이블에 저장 (테이블이 만들어졌는 지는 신경 쓸 필요없음)
            SQLiteDatabase db = new DBHelper(this).getWritableDatabase();
            // 컬럼 데이터를 ContentValues 로 표현 (Map과 유사)
            ContentValues values = new ContentValues();
            values.put("name", name);
            values.put("address", address);
            //저장
            db.insert("tb_user", null, values);
            db.close();
        });

        binding.queryButton.setOnClickListener(view -> {
            SQLiteDatabase db = new DBHelper(this).getReadableDatabase();
            // columns 가 null 일 경우 -> 모든 컬럼 데이터 가져옴
            // selection 가 null 일 경우 -> 조건 없이 모든 데이터 가져옴
            // ... => select * from tb_user => db.rawQuery("select * from tb_user", null)
            Cursor cursor = db.query("tb_user", null, null, null, null, null, null);
            String result = "";

            while (cursor.moveToNext()) {
                result += cursor.getString(1);
                result += ":";
                result += cursor.getString(2);
                result += "\n";
            }

            binding.resultView.setText(result);
        });
    }
}