package com.rejointech.jobber.Containers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.rejointech.jobber.Fragments.Home.homeFragment;
import com.rejointech.jobber.R;
import com.rejointech.jobber.Startup.splash;

public class HomeContainer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        addfirstFragment4User();
    }

    private void addfirstFragment4User() {
            getSupportFragmentManager().beginTransaction().add(R.id.startupViewContainer,new homeFragment()).commit();
    }
}