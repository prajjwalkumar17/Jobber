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
import android.widget.ImageView;

import com.rejointech.jobber.APICall.APICall;
import com.rejointech.jobber.Adapters.Homefrag.AdapterFeaturedJobs;
import com.rejointech.jobber.Adapters.Homefrag.AdapterRecommendedJobs;
import com.rejointech.jobber.Adapters.Homefrag.AdapterpopularJobs;
import com.rejointech.jobber.Containers.HomeContainer;
import com.rejointech.jobber.Decoration.DecorationForRecyclerView;
import com.rejointech.jobber.Fragments.JobDescription.jobDescFragment;
import com.rejointech.jobber.Fragments.Profile.profileFragment;
import com.rejointech.jobber.R;
import com.rejointech.jobber.RecyclerClickListeners.RecyclerHomeFeaturedCompleteClick;
import com.rejointech.jobber.RecyclerClickListeners.RecyclerHomePopularOnClick;
import com.rejointech.jobber.RecyclerClickListeners.RecylerHomeRecommendedRecommendedOnClick;
import com.rejointech.jobber.Utils.CommonMethods;
import com.rejointech.jobber.Utils.Constants;
import com.squareup.picasso.Picasso;

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
    ImageView nav_dp;

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
        initLayout();
        initViews(root);
        requests();
        return root;
    }

    private void requests() {
        getme();
        setFeaturedJobs();
        featuredOnClicks();
        setpopularJobs();
        popularjobsOnClicks();
        setrecommendedJobs();
        recommendedJobsOnClicks();
    }

    private void getme() {
        APICall.okhttpMaster().newCall(APICall.get4me(APICall.urlBuilder4http(Constants.getme), token)).enqueue(new Callback() {
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
                            JSONObject data = myres.optJSONObject("data");
                            JSONObject redata = data.optJSONObject("data");
                            String name = redata.optString("Name");
                            String Email = redata.optString("Email");
                            String Current_designation = redata.optString("Current_designation");
                            String dp = redata.optString("Photo");
                            String Resume = redata.optString("Resume");
                            addDatatoPrefs(name, Email, Current_designation, dp, Resume);

                        } catch (Exception e) {
                            CommonMethods.LOGthesite(Constants.LOG, e.getMessage());

                        }
                    }
                });

            }
        });
    }

    private void addDatatoPrefs(String name, String email, String current_designation, String dp, String resume) {
        SharedPreferences sharedPreferences = thiscontext.getSharedPreferences(Constants.PROFILEPREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.PROFILEname, name);
        editor.putString(Constants.PROFILEemail, email);
        editor.putString(Constants.PROFILEcurrent_designation, current_designation);
        editor.putString(Constants.PROFILEdp, dp);
        editor.putString(Constants.PROFILEresume, resume);
        editor.apply();
    }


    private void initLayout() {
        ((HomeContainer) getActivity()).setfabvisible();
        ((HomeContainer) getActivity()).setToolbarVisible();
        ((HomeContainer) getActivity()).setbotVisible();
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
                adddataToPrefs(Company_name, id, Job_description, Job_role, Job_type, Duration, Experience_required, Salary, Location, About_company, Responsibilities);
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
                            String status=myres.optString("status");
                            if(!status.equals("fail")) {
                                adapterRecommendedJobs = new AdapterRecommendedJobs(myres, getActivity(), thiscontext, recylerHomeRecommendedRecommendedOnClick);
                                recommendedJobsRecycler.setAdapter(adapterRecommendedJobs);
                            }else{
                                CommonMethods.DisplayLongTOAST(thiscontext,"Please update your skills to view Recommended jobs");
                            }
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
                JSONObject data = object.optJSONObject("data");
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
                adddataToPrefs(Company_name, id, Job_description, Job_role, Job_type, Duration, Experience_required, Salary, Location, About_company, Responsibilities);

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
                adddataToPrefs(Company_name, id, Job_description, Job_role, Job_type, Duration, Experience_required, Salary, Location, About_company, Responsibilities);

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
        GridLayoutManager gridLayoutManager = new GridLayoutManager(thiscontext, 1, GridLayoutManager.HORIZONTAL, false);
        featuredJobsRecycler.setLayoutManager(gridLayoutManager);
        featuredJobsRecycler.addItemDecoration(new DecorationForRecyclerView(thiscontext, R.dimen.dp_2));
        popularJobsRecycler = root.findViewById(R.id.popularJobsRecycler);
        SharedPreferences sharedPreferences1 = thiscontext.getSharedPreferences(Constants.PROFILEPREFS, Context.MODE_PRIVATE);
        String dp = sharedPreferences1.getString(Constants.PROFILEdp, "No data found!!!");
        String url = Constants.dpurl + dp;
        nav_dp = root.findViewById(R.id.profiledp);
        Picasso.get()
                .load(url)
                .error(R.drawable.defaultdp)
                .into(nav_dp);
        nav_dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.maincontainerview, new profileFragment()).addToBackStack(null).commit();
            }
        });
        recommendedJobsRecycler = root.findViewById(R.id.recommendedJobsRecycler);
        GridLayoutManager gridLayoutManagerrec = new GridLayoutManager(thiscontext, 1, GridLayoutManager.HORIZONTAL, false);
        GridLayoutManager gridLayoutManagerpopular = new GridLayoutManager(thiscontext, 1, GridLayoutManager.VERTICAL, false);
        recommendedJobsRecycler.setLayoutManager(gridLayoutManagerrec);
        popularJobsRecycler.setLayoutManager(gridLayoutManagerpopular);
        recommendedJobsRecycler.addItemDecoration(new DecorationForRecyclerView(thiscontext, R.dimen.dp_2));
        popularJobsRecycler.addItemDecoration(new DecorationForRecyclerView(thiscontext, R.dimen.dp_2));
    }
}