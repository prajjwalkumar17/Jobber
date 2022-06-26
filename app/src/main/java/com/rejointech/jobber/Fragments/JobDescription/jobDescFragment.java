package com.rejointech.jobber.Fragments.JobDescription;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rejointech.jobber.Containers.HomeContainer;
import com.rejointech.jobber.R;

public class jobDescFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_job_desc, container, false);
        initLayout();
        return root;
    }

    private void initLayout() {
        ((HomeContainer) getActivity()).setToolbarInvisible();
    }
}