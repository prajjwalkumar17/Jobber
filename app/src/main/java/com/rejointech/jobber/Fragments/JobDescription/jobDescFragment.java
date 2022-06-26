package com.rejointech.jobber.Fragments.JobDescription;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.rejointech.jobber.Containers.HomeContainer;
import com.rejointech.jobber.Fragments.JobDescription.TabbedFragments.tabReviewsFragment;
import com.rejointech.jobber.Fragments.JobDescription.TabbedFragments.tabRequirementFragment;
import com.rejointech.jobber.Fragments.JobDescription.TabbedFragments.tababoutFragment;
import com.rejointech.jobber.Fragments.JobDescription.TabbedFragments.tabdescriptionFragment;
import com.rejointech.jobber.R;
import com.rejointech.jobber.Utils.CommonMethods;
import com.rejointech.jobber.Utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class jobDescFragment extends Fragment {
    TabLayout tabLayout;
    ViewPager viewpager;
    Context thisContext;
    String company_name, location, job_role, job_type, duration, salary, experience_required, id,
            about_company, job_description, responsibilities, openings_available,
            total_Applicants, perks, featured;
    TextView rec_feat_jobRole,rec_feat_comname,rec_feat_duration,rec_feat_jobtype,rec_feat_expreq,
            rec_feat_jobsal,rec_feat_jobloc;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        thisContext=context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_job_desc, container, false);
        initLayout();
        getdataToShow();
        initView(root);
        setdata();
        return root;
    }

    private void setdata() {
        rec_feat_jobRole.setText(job_role);
        rec_feat_comname.setText(company_name);
        rec_feat_duration.setText(duration);
        rec_feat_jobtype.setText(job_type);
        rec_feat_expreq.setText(experience_required+"years+");
        if(!salary.equals("unspecified"))
        rec_feat_jobsal.setText(salary+"/year");
        else
            rec_feat_jobsal.setText(salary);
        rec_feat_jobloc.setText(location);

    }

    private void getdataToShow() {
        SharedPreferences sharedPreferences = thisContext.getSharedPreferences(Constants.JDPREFS, Context.MODE_PRIVATE);
        company_name = sharedPreferences.getString(Constants.JDcompany_name, "No data found!!!");
        id = sharedPreferences.getString(Constants.JDid, "No data found!!!");
        job_description = sharedPreferences.getString(Constants.JDjob_description, "No data found!!!");
        job_role = sharedPreferences.getString(Constants.JDjob_role, "No data found!!!");
        job_type = sharedPreferences.getString(Constants.JDjob_type, "No data found!!!");
        duration = sharedPreferences.getString(Constants.JDduration, "No data found!!!");
        experience_required = sharedPreferences.getString(Constants.JDexperience_required, "No data found!!!");
        salary = sharedPreferences.getString(Constants.JDsalary, "No data found!!!");
        location = sharedPreferences.getString(Constants.JDlocation, "No data found!!!");
        about_company = sharedPreferences.getString(Constants.JDabout_company, "No data found!!!");
        responsibilities = sharedPreferences.getString(Constants.JDresponsibilities, "No data found!!!");
    }

    private void initView(View root) {
        rec_feat_jobRole=root.findViewById(R.id.rec_feat_jobRole);
        rec_feat_comname=root.findViewById(R.id.rec_feat_comname);
        rec_feat_duration=root.findViewById(R.id.rec_feat_duration);
        rec_feat_jobtype=root.findViewById(R.id.rec_feat_jobtype);
        rec_feat_expreq=root.findViewById(R.id.rec_feat_expreq);
        rec_feat_jobsal=root.findViewById(R.id.rec_feat_jobsal);
        rec_feat_jobloc=root.findViewById(R.id.rec_feat_jobloc);
        tabLayout=root.findViewById(R.id.tablayout);
        viewpager=root.findViewById(R.id.viewpager);
        ArrayList<String> tabs=new ArrayList<>();
        tabs.add("Description");
        tabs.add("Requirements");
        tabs.add("About");
        tabs.add("Reviews");
        prepareTabs(viewpager,tabs);
        tabLayout.setupWithViewPager(viewpager);
    }

    private void prepareTabs(ViewPager viewpager, ArrayList<String> tabs) {
        MainAdapter mainAdapter=new MainAdapter(getChildFragmentManager(),FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mainAdapter.addFragment(new tabdescriptionFragment(),"Description");
        mainAdapter.addFragment(new tabRequirementFragment(),"Requirements");
        mainAdapter.addFragment(new tababoutFragment(),"About");
//        mainAdapter.addFragment(new tabReviewsFragment(),"Reviews");
        tabReviewsFragment fragment=new tabReviewsFragment();
        viewpager.setAdapter(mainAdapter);

    }

    private void initLayout() {
        ((HomeContainer) getActivity()).setToolbarInvisible();
        ((HomeContainer) getActivity()).setbotInvisible();
    }

    private class MainAdapter extends FragmentPagerAdapter {
        ArrayList<String> arrayList=new ArrayList<>();
        List<Fragment> fragmentList=new ArrayList<>();

        public void addFragment(Fragment fragment,String title){
            arrayList.add(title);
            fragmentList.add(fragment);
        }
        public MainAdapter(@NonNull FragmentManager fm,int behavior) {
            super(fm,behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return arrayList.get(position);
        }
    }
}