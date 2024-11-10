package com.codecrush.mentalhealthchatbot.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codecrush.mentalhealthchatbot.R;
import com.codecrush.mentalhealthchatbot.adapter.RecyclerPastChatAdapter;

import java.util.ArrayList;

public class PastChatActivity extends AppCompatActivity {

    RecyclerView rv_pastchat;

    ArrayList<String> arrdata = new ArrayList<>();

    ImageView iv_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_chat);

        iv_back=findViewById(R.id.back);

         rv_pastchat=findViewById(R.id.rv_pastchat);
         rv_pastchat.setLayoutManager(new LinearLayoutManager(this));

         arrdata.add("First Chat");
         arrdata.add("Second Chat");
         arrdata.add("Third Chat");
         arrdata.add("Fourth Chat");


        RecyclerPastChatAdapter adapter= new RecyclerPastChatAdapter(this,arrdata);
        rv_pastchat.setAdapter(adapter);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}