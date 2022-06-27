package com.rejointech.jobber.Fragments.Home;

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

import com.rejointech.jobber.APICall.APICall;
import com.rejointech.jobber.Adapters.AdapterFeaturedJobs;
import com.rejointech.jobber.Adapters.AdapterRecommendedJobs;
import com.rejointech.jobber.Adapters.AdapterpopularJobs;
import com.rejointech.jobber.Decoration.DecorationForRecyclerView;
import com.rejointech.jobber.Fragments.JobDescription.jobDescFragment;
import com.rejointech.jobber.R;
import com.rejointech.jobber.RecyclerClickListeners.RecyclerHomeFeaturedCompleteClick;
import com.rejointech.jobber.RecyclerClickListeners.RecyclerHomePopularOnClick;
import com.rejointech.jobber.RecyclerClickListeners.RecylerHomeRecommendedRecommendedOnClick;
import com.rejointech.jobber.Utils.CommonMethods;
import com.rejointech.jobber.Utils.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class homeFragment extends Fragment {

    RecyclerView featuredJobsRecycler;
    RecyclerView popularJobsRecycler;
    RecyclerView recommendedJobsRecycler;
    Context thiscontext;
    AdapterFeaturedJobs adapterFeaturedJobs;
    AdapterpopularJobs adapterpopularJobs;
    AdapterRecommendedJobs adapterRecommendedJobs;
    RecyclerHomeFeaturedCompleteClick recyclerHomeFeaturedCompleteClick;
    RecyclerHomePopularOnClick recyclerHomePopularOnClick;
    RecylerHomeRecommendedRecommendedOnClick recylerHomeRecommendedRecommendedOnClick;
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
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        initViews(root);
        setFeaturedJobs();
        featuredOnClicks();
        setpopularJobs();
        popularjobsOnClicks();
        setrecommendedJobs();
        recommendedJobsOnClicks();
        return root;
    }

    private void recommendedJobsOnClicks() {
        recylerHomeRecommendedRecommendedOnClick = new RecylerHomeRecommendedRecommendedOnClick() {
            @Override
            public void onItemClick(View v, int position, JSONObject object) {
                String status = object.optString(getActivity().getString(R.string.JOBSfeaturedJob_status));
                String results = object.optString(getActivity().getString(R.string.JOBSfeaturedJob_noofresults));
                if (Integer.parseInt(results) > 0) {
                    JSONArray featuredjobsarray = object.optJSONArray("recommendedJobs");
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

    private void setrecommendedJobs() {
        APICall.okhttpMaster().newCall(APICall.get4recommended(APICall.urlBuilder4http(Constants.recommendedJobsurl), token)).enqueue(new Callback() {
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
                            adapterRecommendedJobs = new AdapterRecommendedJobs(myres, getActivity(), thiscontext, recylerHomeRecommendedRecommendedOnClick);
                            recommendedJobsRecycler.setAdapter(adapterRecommendedJobs);
                        } catch (Exception e) {
                            CommonMethods.LOGthesite(Constants.LOG, e.getMessage());
                        }
                    }
                });
            }
        });
    }

    private void popularjobsOnClicks() {
        recyclerHomePopularOnClick = new RecyclerHomePopularOnClick() {
            @Override
            public void onItemClick(View v, int position, JSONObject object) {
                String status = object.optString(getActivity().getString(R.string.JOBSfeaturedJob_status));
                String results = object.optString(getActivity().getString(R.string.JOBSfeaturedJob_noofresults));
                JSONObject data=object.optJSONObject("data");
                if (Integer.parseInt(results) > 0) {
                    JSONArray featuredjobsarray = data.optJSONArray("allJobs");
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

    private void setpopularJobs() {
        APICall.okhttpMaster().newCall(APICall.get4allJobs(APICall.urlBuilder4http(Constants.allJobsurl))).enqueue(new Callback() {
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
                            adapterpopularJobs = new AdapterpopularJobs(myres, getActivity(), thiscontext, recyclerHomePopularOnClick);
                            popularJobsRecycler.setAdapter(adapterpopularJobs);

                        } catch (Exception e) {
                            CommonMethods.LOGthesite(Constants.LOG, e.getMessage());

                        }
                    }
                });
            }
        });
    }

    private void featuredOnClicks() {
        recyclerHomeFeaturedCompleteClick = new RecyclerHomeFeaturedCompleteClick() {
            @Override
            public void onItemClick(View v, int position, JSONObject object) {
                String status = object.optString(getActivity().getString(R.string.JOBSfeaturedJob_status));
                String results = object.optString(getActivity().getString(R.string.JOBSfeaturedJob_noofresults));
                if (Integer.parseInt(results) > 0) {
                    JSONArray featuredjobsarray = object.optJSONArray(getActivity().getString(R.string.JOBSfeaturedJob_realarray));
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

    private void setFeaturedJobs() {
        APICall.okhttpMaster().newCall(APICall.get4featuredJobs(APICall.urlBuilder4http(Constants.featuredJobsurl))).enqueue(new Callback() {
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
                            adapterFeaturedJobs = new AdapterFeaturedJobs(myres, getActivity(), thiscontext, recyclerHomeFeaturedCompleteClick);
                            featuredJobsRecycler.setAdapter(adapterFeaturedJobs);

                        } catch (Exception e) {
                            CommonMethods.LOGthesite(Constants.LOG, e.getMessage());

                        }
                    }
                });
            }
        });
    }

    private void initViews(View root) {
        SharedPreferences sharedPreferences = thiscontext.getSharedPreferences(Constants.TOKENPREF, Context.MODE_PRIVATE);
        token = sharedPreferences.getString(Constants.TOKEN, "No data found!!!");
        featuredJobsRecycler = root.findViewById(R.id.featuredJobsRecycler);
        popularJobsRecycler = root.findViewById(R.id.popularJobsRecycler);
        recommendedJobsRecycler = root.findViewById(R.id.recommendedJobsRecycler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(thiscontext, 1, GridLayoutManager.HORIZONTAL, false);
        GridLayoutManager gridLayoutManagerrec = new GridLayoutManager(thiscontext, 1, GridLayoutManager.HORIZONTAL, false);
        GridLayoutManager gridLayoutManagerpopular = new GridLayoutManager(thiscontext, 1, GridLayoutManager.VERTICAL, false);
        featuredJobsRecycler.setLayoutManager(gridLayoutManager);
        recommendedJobsRecycler.setLayoutManager(gridLayoutManagerrec);
        popularJobsRecycler.setLayoutManager(gridLayoutManagerpopular);
        featuredJobsRecycler.addItemDecoration(new DecorationForRecyclerView(thiscontext, R.dimen.dp_2));
        recommendedJobsRecycler.addItemDecoration(new DecorationForRecyclerView(thiscontext, R.dimen.dp_2));
        popularJobsRecycler.addItemDecoration(new DecorationForRecyclerView(thiscontext, R.dimen.dp_2));
    }
}