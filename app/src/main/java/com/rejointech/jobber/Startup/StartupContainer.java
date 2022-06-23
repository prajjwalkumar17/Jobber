package com.rejointech.jobber.Startup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.rejointech.jobber.R;

public class StartupContainer extends AppCompatActivity {

    FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starttup_container);
        frameLayout=findViewById(R.id.startupViewContainer);
        addFirstFragment();
    }

    private void addFirstFragment() {
        getSupportFragmentManager().beginTransaction().add(R.id.startupViewContainer,new splash()).commit();
    }
}