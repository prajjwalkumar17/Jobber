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

public class signupfragment extends Fragment {

    AppCompatEditText name,email,password,cnfpassword,role,skills;
    TextView bot_login;
    AppCompatButton registerbot;
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
        View root=inflater.inflate(R.layout.fragment_signupragment, container, false);

        //init
        bot_login=root.findViewById(R.id.bot_login);
        name=root.findViewById(R.id.name);
        email=root.findViewById(R.id.email);
        password=root.findViewById(R.id.password);
        registerbot=root.findViewById(R.id.loginbot);
        role=root.findViewById(R.id.role);
        skills=root.findViewById(R.id.skills);
        cnfpassword=root.findViewById(R.id.cnfpassword);

        //buttons
        registerbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name = name.getText().toString();
                String Email = email.getText().toString();
                String Role = role.getText().toString();
                String[] Skills = skills.getText().toString().split(",");
                String PasswordConfirmed = cnfpassword.getText().toString();
                String Password = password.getText().toString();


                if (Name.length() == 0 ||
                        Email.length() == 0 ||
                        Password.length() == 0 ||
                        Skills.length==0||
                        PasswordConfirmed.length() == 0 ||
                        !Password.equals(PasswordConfirmed)) {
                    CommonMethods.DisplayShortTOAST(thiscontext, "Check the filled details Properly");

                } else {
                    CommonMethods.DisplayShortTOAST(thiscontext, "Signup in Progress");
                    registerUser(Name,Email,Role,Password,PasswordConfirmed,Skills);

                }
            }
        });
        bot_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.startupViewContainer,new loginFragment()).commit();
            }
        });
        return root;
    }

    private void registerUser(String name, String email, String role, String password, String passwordConfirmed,String... skills) {
         APICall.okhttpMaster().newCall(
                APICall.post4SignUp(APICall.urlBuilder4http(Constants.signupurl),
                        APICall.signupBody(name, email, password, passwordConfirmed,role,skills))
        ).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                CommonMethods.LOGthesite(Constants.LOG, e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                final String myresponse=response.body().string();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject myres = new JSONObject(myresponse);
                            String token=myres.getString("token").toString();
                            savedataToPrefs(token);
//                            CommonMethods.LOGthesite(Constants.LOG, token);
                        }catch (Exception e){
                            CommonMethods.LOGthesite(Constants.LOG, e.getMessage());
                        }

                    }
                });
            }
        });
    }
    private void savedataToPrefs(String token) {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(Constants.TOKENPREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.TOKEN, token);
        editor.apply();
        startActivity(new Intent(getActivity(), HomeContainer.class));
    }
}