package com.codecrush.mentalhealthchatbot.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.codecrush.mentalhealthchatbot.R;


public class ChatFragment extends Fragment
{
    View view;
    RecyclerView RVChatMessage;
    EditText ETMSG;
    ImageView IVSend;
    ProgressBar PBRVChatMessage;
    TextView TVDataNotFoundRVChatMessage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if (view!=null)
        {
            return view;
        }

        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_consult_doctor, container, false);

        ETMSG = view.findViewById(R.id.et_msg);
        IVSend = view.findViewById(R.id.iv_send);
        RVChatMessage = view.findViewById(R.id.rv_chat_message);
        PBRVChatMessage=view.findViewById(R.id.pb_rv_chat_message);
        TVDataNotFoundRVChatMessage=view.findViewById(R.id.tv_data_not_found_rv_chat_message);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        //RVChatMessage.setLayoutManager(linearLayoutManager);

        SharedPreferences usr = getContext().getSharedPreferences("user", MODE_PRIVATE);

        return view;
    }
}