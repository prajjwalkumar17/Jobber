package com.rejointech.jobber.Fragments.JobDescription.TabbedFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rejointech.jobber.R;
import com.rejointech.jobber.Utils.Constants;

public class tabRequirementFragment extends Fragment {
Context thiscontext;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        thiscontext= context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_tab_requirement, container, false);
        TextView text=root.findViewById(R.id.text);
        SharedPreferences sharedPreferences = thiscontext.getSharedPreferences(Constants.JDPREFS, Context.MODE_PRIVATE);
        String about_company = sharedPreferences.getString(Constants.JDresponsibilities, "No data found!!!");
        text.setText(about_company);
        return root;
    }
}