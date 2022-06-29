package com.rejointech.jobber.Fragments.Profile;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rejointech.jobber.APICall.APICall;
import com.rejointech.jobber.Adapters.AdapterAppliedJobs;
import com.rejointech.jobber.Containers.HomeContainer;
import com.rejointech.jobber.Decoration.DecorationForRecyclerView;
import com.rejointech.jobber.Fragments.JobDescription.jobDescFragment;
import com.rejointech.jobber.Fragments.bookmarksFragment;
import com.rejointech.jobber.R;
import com.rejointech.jobber.RecyclerClickListeners.RecyclerHomeFeaturedCompleteClick;
import com.rejointech.jobber.Startup.StartupContainer;
import com.rejointech.jobber.Utils.CommonMethods;
import com.rejointech.jobber.Utils.Constants;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class profileFragment extends Fragment {
    RecyclerView featuredJobsRecycler;
    AdapterAppliedJobs adapterAppliedJobs;
    String token;
    Context thiscontext;
    RecyclerHomeFeaturedCompleteClick recyclerHomeFeaturedCompleteClick;

    TextView editbot,profilename,current_pos,noofjobsapp,skiilstxt,profileemail,bookmarks,seeallapplied;
    ImageView profiledp;
    AppCompatButton logoutbot;
    ImageView backbot,resumebot;
    LinearLayout bookmarkjobs,allappjobs,linearLayout8;

    String Company_name, Location, Job_role, Job_type, Duration, Salary, Experience_required, id,
            About_company, Application_deadline, Job_description, Responsibilities, Openings_available,
            Total_Applicants, Perks, Featured;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        thiscontext=context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_profile, container, false);
        initLayout();
        initView(root);
        getMe();
        appliedJobs();
        appliedJObsOnclick();
        return root;
    }

    private void getMe() {
        APICall.okhttpMaster().newCall(APICall.get4me(APICall.urlBuilder4http(Constants.getme),token)).enqueue(new Callback() {
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
                                JSONObject data=myres.optJSONObject("data");
                                JSONObject redata=data.optJSONObject("data");
                                String name=redata.optString("Name");
                                String Email=redata.optString("Email");
                                String Current_designation=redata.optString("Current_designation");
                                JSONArray Skills=redata.optJSONArray("Skills");
                                String    dp=redata.optString("Photo");
                                String Resume=redata.optString("Resume");
                                addDatatoPrefs(name,Email,Current_designation,dp,Resume);
                                int jobsapplied=redata.getJSONArray("Jobs_applied").length();
                                int bookmarksdata=redata.getJSONArray("Bookmarked_jobs").length();
                                addDatatoPrefs(name,Email,Current_designation,dp,Resume);
                                //settexts
                                for(int i=0;i<Skills.length();i++){
                                    String skill=Skills.optString(i);
                                    skiilstxt.append(skill+"\n");
                                    if(i== Skills.length()) {
                                        skiilstxt.append(skill);
                                        break;
                                    }

                                }
                                profilename.setText(name);
                                current_pos.setText(Current_designation);
                                profileemail.setText(Email);
                                bookmarks.setText(String.valueOf(bookmarksdata));
                                noofjobsapp.setText(String.valueOf(jobsapplied));



                            } catch (Exception e) {
                                CommonMethods.LOGthesite(Constants.LOG, e.getMessage());

                            }
                        }
                    });
                }
            }
        });
    }

    private void addDatatoPrefs(String name, String email, String current_designation, String dp, String resume) {
        SharedPreferences sharedPreferences =thiscontext.getSharedPreferences(Constants.PROFILEPREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.PROFILEname, name);
        editor.putString(Constants.PROFILEemail, email);
        editor.putString(Constants.PROFILEcurrent_designation, current_designation);
        CommonMethods.LOGthesite(Constants.LOG,dp);
        editor.putString(Constants.PROFILEdp, dp);
        editor.putString(Constants.PROFILEresume, resume);
        editor.apply();
    }

    private void initLayout() {
        ((HomeContainer) getActivity()).setToolbarInvisible();
        ((HomeContainer) getActivity()).setbotInvisible();
        ((HomeContainer) getActivity()).setfabinvisible();
    }

    private void appliedJObsOnclick() {
        recyclerHomeFeaturedCompleteClick = new RecyclerHomeFeaturedCompleteClick() {
            @Override
            public void onItemClick(View v, int position, JSONObject object) {
                String status = object.optString(getActivity().getString(R.string.JOBSfeaturedJob_status));
                String results = object.optString(getActivity().getString(R.string.JOBSfeaturedJob_noofresults));
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

    private void appliedJobs() {
        APICall.okhttpMaster().newCall(APICall.get4appliedJobs(APICall.urlBuilder4http(Constants.appliedJobs),token)).enqueue(new Callback() {
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
                                adapterAppliedJobs = new AdapterAppliedJobs(myres, getActivity(), thiscontext, recyclerHomeFeaturedCompleteClick);
                                featuredJobsRecycler.setAdapter(adapterAppliedJobs);

                            } catch (Exception e) {
                                CommonMethods.LOGthesite(Constants.LOG, e.getMessage());

                            }
                        }
                    });
                }
            }
        });
    }

    private void initView(View root) {
        editbot=root.findViewById(R.id.editbot);
        profilename=root.findViewById(R.id.profilename);
        profileemail=root.findViewById(R.id.profileemail);
        current_pos=root.findViewById(R.id.current_pos);
        noofjobsapp=root.findViewById(R.id.noofjobsapp);
        seeallapplied=root.findViewById(R.id.seeallapplied);
        bookmarks=root.findViewById(R.id.bookmarks);
        skiilstxt=root.findViewById(R.id.skiilstxt);
        linearLayout8=root.findViewById(R.id.linearLayout8);
        logoutbot=root.findViewById(R.id.logoutbot);
        backbot=root.findViewById(R.id.backbot);
        resumebot=root.findViewById(R.id.resumebot);
        bookmarkjobs=root.findViewById(R.id.bookmarkjobs);
        allappjobs=root.findViewById(R.id.allappjobs);
        linearLayout8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.maincontainerview,new resumeviewFragment()).addToBackStack(null).commit();
            }
        });
        bookmarkjobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.maincontainerview,new bookmarksFragment()).addToBackStack(null).commit();

            }
        });
        allappjobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.maincontainerview,new allAppliedJobsFragment()).addToBackStack(null).commit();
            }
        });

        seeallapplied.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.maincontainerview,new allAppliedJobsFragment()).addToBackStack(null).commit();
            }
        });
        SharedPreferences sharedPreferences1 = thiscontext.getSharedPreferences(Constants.PROFILEPREFS, Context.MODE_PRIVATE);
        String dp = sharedPreferences1.getString(Constants.PROFILEdp, "No data found!!!");
        String url=Constants.dpurl+dp;
        profiledp= root.findViewById(R.id.profiledp);
        Picasso.get()
                .load(url)
                .error(R.drawable.defaultdp)
                .into(profiledp);

        SharedPreferences sharedPreferences = thiscontext.getSharedPreferences(Constants.TOKENPREF, Context.MODE_PRIVATE);
        token = sharedPreferences.getString(Constants.TOKEN, "No data found!!!");
        featuredJobsRecycler = root.findViewById(R.id.featuredJobsRecycler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(thiscontext, 1, GridLayoutManager.HORIZONTAL, false);
        featuredJobsRecycler.setLayoutManager(gridLayoutManager);
        featuredJobsRecycler.addItemDecoration(new DecorationForRecyclerView(thiscontext, R.dimen.dp_2));
        backbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });
        logoutbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                        AlertDialog alertDialog = new AlertDialog.Builder(thiscontext).create();
                        alertDialog.setTitle("Logout Confirmation");
                        alertDialog.setMessage("Are you sure you want to Logout?");
                        alertDialog.setIcon(R.drawable.ic_err);

                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Logout", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                alertDialog.dismiss();
                                CommonMethods.DisplayShortTOAST(thiscontext, "You are going to be Logged out");
                                logout();
                            }
                        });

                        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                alertDialog.dismiss();
                            }
                        });
                        alertDialog.show();
            }
        });
    }

    private void logout() {
       SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(Constants.TOKENPREF, Context.MODE_PRIVATE);
       SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.TOKEN, null);
        editor.apply();
        Intent intent = new Intent(getActivity(), StartupContainer.class);
        startActivity(intent);
    }
}