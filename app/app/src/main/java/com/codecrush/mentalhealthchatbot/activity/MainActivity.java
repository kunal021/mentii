package com.codecrush.mentalhealthchatbot.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.codecrush.mentalhealthchatbot.R;
import com.codecrush.mentalhealthchatbot.fragment.ChatFragment;
import com.codecrush.mentalhealthchatbot.fragment.ConsultDoctorFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity
{
    ImageView IVChatList,IVLogout;
    FrameLayout FrameLayout;
    BottomNavigationView bottomNavbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IVChatList = findViewById(R.id.iv_chat_list);
        IVLogout = findViewById(R.id.iv_logout);
        bottomNavbar = findViewById(R.id.navigation);
        FrameLayout=findViewById(R.id.framelayout);
        IVLogout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        });

        IVChatList.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        });


        bottomNavbar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {

                int id=item.getItemId();

                if(id==R.id.chat)
                {
                    loadFragment(new ChatFragment(),1);
                }
                else if (id==R.id.consult_doctor)
                {
                    loadFragment(new ConsultDoctorFragment(),1);
                }


                return true;
            }
        });
        bottomNavbar.setSelectedItemId(R.id.chat);


    }

    public void loadFragment(Fragment fragment, int flag) {
        FragmentManager fm = getSupportFragmentManager();

        // Use a tag to identify the fragment in the back stack
        String fragmentTag = fragment.getClass().getSimpleName();
        //Log.d("TAGss", fragmentTag);
        Fragment existingFragment = fm.findFragmentByTag(fragmentTag);

        // Only add the fragment if it is not already in the back stack
        if (existingFragment == null || !existingFragment.isAdded())
        {
            FragmentTransaction ft = fm.beginTransaction();

            ft.replace( R.id.framelayout, fragment, fragmentTag);
            ft.addToBackStack(fragmentTag);
            ft.commit();
        }
        else
        {
            //---
        }


    }

}