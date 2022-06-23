package com.rejointech.jobber.Startup;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rejointech.jobber.R;
import com.rejointech.jobber.Utils.Constants;
import com.rejointech.jobber.Utils.Typewriter;

public class splash extends Fragment {

    Typewriter typechar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_splash, container, false);

        typechar=root.findViewById(R.id.typechar);
        typechar.setText("");
        typechar.setCharDelay(40);
        typechar.animateText("All your Dream Jobs are ahead!!");


        Handler handler=new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(getActivity()!=null){
                    getActivity().getSupportFragmentManager().beginTransaction().add(R.id.startupViewContainer,new onboarding1()).commit();
                }
            }
        },Constants.SPLASH_TIMEOUT);


        return root;
    }
}