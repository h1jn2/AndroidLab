package com.example.ch10;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SQLiteDatabase db = openOrCreateDatabase("testdb", MODE_PRIVATE, null);

        // 데이터를 저장하려면 테이블이 있어야 함
        // 하나의 관련된 데이터 여러 건을 저장하기 위한 개념이 테이블
        // 현재 우리가 원하는 테이블이 있는지 확인
        // sqlite_master 에 데이터베이스의 테이블 정보가 등록됨
        // 그 곳에 우리가 원하는 이름의 테이블이 등록되어 있는 지 확인
        String query = "select name from sqlite_master where type='table' and name=?";
        Cursor cursor = db.rawQuery(query, new String[]{"test1_tb"});
        boolean exists = cursor.getCount() > 0; // 획득된 것이 있다면 테이블이 존재
        cursor.close();

        if (!exists) {
            // 테이블이 없다면 만들어 줌
            db.execSQL("create table test1_tb (" +
                    "_id integer primary key autoincrement," +
                    "title text," +
                    "content text)");
            // 데이터 하나 저장
            for (int i = 1; i <= 10; i++) {
                db.execSQL("insert into test1_tb (title, content) values (?,?)",
                        new String[]{"title" + i, "content" + i});
            }
        }

        Cursor cursor1 = db.rawQuery("select * from test1_tb", null);
        while (cursor1.moveToNext()) {
            Log.d("kkang", cursor1.getInt(0) + ":" + cursor1.getString(1)
            + ":" + cursor1.getString(2));
        }
        cursor1.close();
    }

}















