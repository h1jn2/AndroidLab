package com.example.ch6.section1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ch6.R;
import com.example.ch6.databinding.ActivityTest12Binding;
import com.example.ch6.databinding.ItemRecyclerBinding;

import java.util.ArrayList;

public class Test1_2Activity extends AppCompatActivity {
    ActivityTest12Binding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityTest12Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ArrayList<String> list = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            list.add("item " + i);
        }

        binding.main.setAdapter(new MyAdapter(list));
        // adapter 가 만들어놓은 항목을 배치
        binding.main.setLayoutManager(new LinearLayoutManager(this));
        // 항목 꾸미는 Decoration 은 개발자가 클래스로 만들어 적용하는 것이지만
        // 단순 항목 사이의 구분선 정도는 제공해줌
        binding.main.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

    }
}

// 항목을 구성하기 위해 필요한 뷰를 가지는 역할
class MyViewHolder extends RecyclerView.ViewHolder {
    ItemRecyclerBinding binding;
    MyViewHolder(ItemRecyclerBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}

// ViewHolder 의 뷰 객체를 이용해서 각 항목을 구성 (데이터 출력, 이벤트 등록 등)
class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    ArrayList<String> datas;
    MyAdapter(ArrayList<String> datas) {
        this.datas = datas;
    }

    // 항목 갯수를 판단하기 위해 자동 호출
    @Override
    public int getItemCount() {
        return datas.size();
    }

    // 항목을 구성하기 위해 뷰를 가지는 뷰홀더를 결정하기 위해 자동 호출
    // 이 함수에서 사용하고자 하는 뷰 홀더를 생성해서 리턴
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRecyclerBinding binding = ItemRecyclerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new MyViewHolder(binding);
    }
    // onCreateViewHolder() 에서 리턴시킨 뷰 홀더를 전달받아 작업
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.binding.itemData.setText(datas.get(position));
    }
}














