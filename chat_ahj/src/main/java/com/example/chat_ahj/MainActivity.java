package com.example.chat_ahj;

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

import com.example.chat_ahj.databinding.ActivityMainBinding;
import com.example.chat_ahj.databinding.ItemMessageBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ArrayList<Message> datas = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            datas.add(new Message("홍길동 " + i, "안녕하세요 " + i, "1월 " + i + "일"));
        }

        binding.main.setAdapter(new MessageAdapter(datas));
        binding.main.setLayoutManager(new LinearLayoutManager(this));
        binding.main.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

}
class MessageViewHolder extends RecyclerView.ViewHolder {
    ItemMessageBinding binding;
    public MessageViewHolder(ItemMessageBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}

class MessageAdapter extends RecyclerView.Adapter<MessageViewHolder> {
    ArrayList<Message> datas;

    MessageAdapter(ArrayList<Message> datas) {
        this.datas = datas;
    }
    @Override
    public int getItemCount() {
        return datas.size();
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMessageBinding binding = ItemMessageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MessageViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        holder.binding.messageName.setText(datas.get(position).name);
        holder.binding.messageContent.setText(datas.get(position).content);
        holder.binding.messageDate.setText(datas.get(position).date);
    }
}