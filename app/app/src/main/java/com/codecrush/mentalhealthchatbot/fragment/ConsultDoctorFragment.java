package com.codecrush.mentalhealthchatbot.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.codecrush.mentalhealthchatbot.R;

public class ConsultDoctorFragment extends Fragment
{
    View view;
    RecyclerView RVDoctorList;
    ProgressBar PBRVDoctorList;
    TextView TVDataNotFoundRVDoctorList;
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
        PBRVDoctorList=view.findViewById(R.id.pb_rv_chat_message);
        TVDataNotFoundRVDoctorList=view.findViewById(R.id.tv_data_not_found_rv_chat_message);


//        RVDoctorList=view.findViewById(R.id.rv_doctor_list);
//
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        //RVDoctorList.setLayoutManager(linearLayoutManager);


        return view;
    }
}