package com.rejointech.jobber.Startup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rejointech.jobber.APICall.APICall;
import com.rejointech.jobber.Containers.HomeContainer;
import com.rejointech.jobber.R;
import com.rejointech.jobber.Utils.CommonMethods;
import com.rejointech.jobber.Utils.Constants;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class loginFragment extends Fragment {

    AppCompatEditText email, password;
    AppCompatButton loginbot;
    Context thisContext;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        thisContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_login, container, false);
        //init
        TextView bot_register;
        email = root.findViewById(R.id.email);
        loginbot = root.findViewById(R.id.loginbot);
        password = root.findViewById(R.id.password);
        bot_register = root.findViewById(R.id.bot_register);
        //Buttons
        loginbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = email.getText().toString();
                String Password = password.getText().toString();
                if (Email.equals("") || Password.equals(""))
                    CommonMethods.DisplayShortTOAST(thisContext, "Please enter email and password to continue");
                else {
                    APICall.okhttpMaster().newCall(APICall.post4login(APICall.urlBuilder4http(Constants.loginurl),
                            APICall.loginBody(Email, Password))).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            CommonMethods.LOGthesite(Constants.LOG, e.getMessage());
                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            final String myResponse = response.body().string();
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        JSONObject myres = new JSONObject(myResponse);
                                        String status=myres.optString("status");
                                        String msg=myres.optString("message");
                                        if(status.equals("fail")){
                                            CommonMethods.DisplayLongTOAST(thisContext,msg);
                                        }else {
                                            String token = myres.getString("token").toString();
                                            savedataToPrefs(token);
                                        }
                                    } catch (Exception e) {
                                        CommonMethods.DisplayLongTOAST(thisContext,e.getMessage());
                                    }
                                }
                            });
                        }
                    });
                }

            }
        });

        bot_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.startupViewContainer, new signupfragment()).commit();
            }
        });
        return root;
    }

    private void savedataToPrefs(String token) {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(Constants.TOKENPREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.TOKEN, token);
        editor.apply();
        startActivity(new Intent(getActivity(), HomeContainer.class));
    }
}