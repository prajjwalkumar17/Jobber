package com.rejointech.jobber.Startup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rejointech.jobber.Containers.HomeContainer;
import com.rejointech.jobber.R;
import com.rejointech.jobber.Utils.Constants;
import com.rejointech.jobber.Utils.Typewriter;

public class splash extends Fragment {

    Typewriter typechar;
    Context thiscontext;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        thiscontext=context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_splash, container, false);

        typechar=root.findViewById(R.id.typechar);
        typechar.setText("");
        typechar.setCharDelay(40);
        typechar.animateText("All your Dream Jobs are ahead!!");

        SharedPreferences sharedPreferences=thiscontext.getSharedPreferences(Constants.TOKENPREF,Context.MODE_PRIVATE);
        String token=sharedPreferences.getString(Constants.TOKEN,null);

        Handler handler=new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(getActivity()!=null)
                    if (token == null)
                    getActivity().getSupportFragmentManager().beginTransaction().add(R.id.startupViewContainer, new onboarding1()).commit();
                    else
                        startActivity(new Intent(getActivity(), HomeContainer.class));
            }
        },Constants.SPLASH_TIMEOUT);


        return root;
    }
}