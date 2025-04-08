package com.example.ch7.section1;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ch7.R;
import com.example.ch7.databinding.ActivityTest11Binding;

public class Test1_1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        ActivityTest11Binding binding = ActivityTest11Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 퍼미션 조정 다이얼로그를 띄우고 닫혔을 때 사후처리 콜백이 등록되어 있는 곳
        ActivityResultLauncher<String> launcher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {  // callback (true-허락, false-거부)
                    if (isGranted) {
                        Intent intent = new Intent("CH7_ACTION_TWO");
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // normal protectionLevel 로 선언되어 있으면
        // 매니페스트에 <uses-permission> 선언으로 끝
        binding.button1.setOnClickListener(view -> {
            Intent intent = new Intent("CH7_ACTION_ONE");
            startActivity(intent);
        });
        // dangerous protectionLevel 로 선언되어 있다면
        // 퍼미션 상태 체크하고 거부 상태라면 조정 다이얼로그 띄워야 함
        binding.button2.setOnClickListener(view -> {
            // 현재 퍼미션 상태 체크
            if (ContextCompat.checkSelfPermission(this,
                    "com.example.ch7_outer.TWO_PERMISSION") == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent("CH7_ACTION_TWO");
                startActivity(intent);
            } else {
                launcher.launch("com.example.ch7_outer.TWO_PERMISSION");
            }
        });
    }
}