package com.rejointech.jobber.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.rejointech.jobber.APICall.APICall;
import com.rejointech.jobber.Adapters.AdapterAllApliedJobs;
import com.rejointech.jobber.Adapters.AdapterAppliedJobs;
import com.rejointech.jobber.Adapters.AdapterBookmarkedjobs;
import com.rejointech.jobber.Containers.HomeContainer;
import com.rejointech.jobber.Decoration.DecorationForRecyclerView;
import com.rejointech.jobber.Fragments.JobDescription.jobDescFragment;
import com.rejointech.jobber.R;
import com.rejointech.jobber.RecyclerClickListeners.RecyclerHomePopularOnClick;
import com.rejointech.jobber.Utils.CommonMethods;
import com.rejointech.jobber.Utils.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class allAppliedJobsFragment extends Fragment {
    RecyclerView bookmarkedjobs;
    ImageView backbot;
    Context thiscontext;
    AdapterAllApliedJobs adapterBookmarkedjobs;
    RecyclerHomePopularOnClick recyclerHomePopularOnClick;
    String token;
    String Company_name, Location, Job_role, Job_type, Duration, Salary, Experience_required, id,
            About_company, Application_deadline, Job_description, Responsibilities, Openings_available,
            Total_Applicants, Perks, Featured;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        thiscontext = context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_all_applied_jobs, container, false);
        initLayout();
        initViews(root);
        setbookmarkedJobs();
        bookmarkedjobsOnClicks();
        return root;
    }
    private void bookmarkedjobsOnClicks() {
        recyclerHomePopularOnClick = new RecyclerHomePopularOnClick() {
            @Override
            public void onItemClick(View v, int position, JSONObject object) {
                String status = object.optString(getActivity().getString(R.string.JOBSfeaturedJob_status));
                String results=object.optString(getString(R.string.JOBSfeaturedJob_noofresults));
                JSONObject data = object.optJSONObject("data");
                if (Integer.parseInt(results) > 0) {
                    JSONArray featuredjobsarray = data.optJSONArray("jobsApplied");
                    JSONObject realres = featuredjobsarray.optJSONObject(position);
                    Company_name = realres.optString(thiscontext.getString(R.string.JOBSfeaturedJob_compname));
                    Location = realres.optString(thiscontext.getString(R.string.JOBSfeaturedJob_loc));
                    Job_role = realres.optString(thiscontext.getString(R.string.JOBSfeaturedJob_jobROle));
                    Job_type = realres.optString(thiscontext.getString(R.string.JOBSfeaturedJob_type));
                    Duration = realres.optString(thiscontext.getString(R.string.JOBSfeaturedJob_duration));
                    Salary = realres.optString(thiscontext.getString(R.string.JOBSfeaturedJob_salary));
                    Experience_required = realres.optString(thiscontext.getString(R.string.JOBSfeaturedJob_expreq));
                    id = realres.optString("_id");
                    About_company = realres.optString(getString(R.string.JOBSfeaturedJob_aboutcom));
                    Application_deadline = realres.optString(getString(R.string.JOBSfeaturedJob_appdeadline));
                    Job_description = realres.optString(getString(R.string.JOBSfeaturedJob_jobdesc));
                    Responsibilities = realres.optString(getString(R.string.JOBSfeaturedJob_jobresposibility));
                    Openings_available = realres.optString(getString(R.string.JOBSfeaturedJob_openingsavail));
                    Total_Applicants = realres.optString(getString(R.string.JOBSfeaturedJob_totalapplicants));
                    Perks = realres.optString(getString(R.string.JOBSfeaturedJob_perks));
                    Featured = realres.optString(getString(R.string.JOBSfeaturedJob_featured));
                }
                adddataToPrefs(Company_name,id,Job_description,Job_role,Job_type,Duration,Experience_required,Salary,Location,About_company,Responsibilities);
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.maincontainerview, new jobDescFragment()).addToBackStack(null).commit();

            }
        };
    }


    private void setbookmarkedJobs() {
        APICall.okhttpMaster().newCall(APICall.get4recommended(APICall.urlBuilder4http(Constants.appliedJobs), token)).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                CommonMethods.LOGthesite(Constants.LOG, e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                final String myResponse = response.body().string();
                if(getActivity()!=null){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject myres = new JSONObject(myResponse);
                                adapterBookmarkedjobs = new AdapterAllApliedJobs(myres, getActivity(), thiscontext, recyclerHomePopularOnClick);
                                bookmarkedjobs.setAdapter(adapterBookmarkedjobs);
                            } catch (Exception e) {
                                CommonMethods.LOGthesite(Constants.LOG, e.getMessage());
                            }
                        }
                    });
                }
            }
        });
    }
    private void adddataToPrefs(String company_name, String id, String job_description, String job_role,
                                String job_type, String duration, String experience_required, String salary,
                                String location, String about_company, String responsibilities) {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(Constants.JDPREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.JDcompany_name, company_name);
        editor.putString(Constants.JDid, id);
        editor.putString(Constants.JDjob_description, job_description);
        editor.putString(Constants.JDjob_role, job_role);
        editor.putString(Constants.JDjob_type, job_type);
        editor.putString(Constants.JDduration, duration);
        editor.putString(Constants.JDresponsibilities, responsibilities);
        editor.putString(Constants.JDexperience_required, experience_required);
        editor.putString(Constants.JDsalary, salary);
        editor.putString(Constants.JDlocation, location);
        editor.putString(Constants.JDabout_company, about_company);
        CommonMethods.LOGthesite(Constants.LOG,location);
        editor.apply();
    }
    private void initViews(View root) {
        SharedPreferences sharedPreferences = thiscontext.getSharedPreferences(Constants.TOKENPREF, Context.MODE_PRIVATE);
        token = sharedPreferences.getString(Constants.TOKEN, "No data found!!!");
        bookmarkedjobs=root.findViewById(R.id.bookmarkedjobs);
        backbot=root.findViewById(R.id.backbot);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(thiscontext, 1, GridLayoutManager.VERTICAL, false);
        bookmarkedjobs.setLayoutManager(gridLayoutManager);
        bookmarkedjobs.addItemDecoration(new DecorationForRecyclerView(thiscontext, R.dimen.dp_2));
        backbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });
    }

    private void initLayout() {
        ((HomeContainer) requireActivity()).setfabinvisible();
        ((HomeContainer) requireActivity()).setToolbarInvisible();
        ((HomeContainer) requireActivity()).setbotVisible();
    }
}