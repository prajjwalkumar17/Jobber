package com.rejointech.jobber.Fragments.Home;

import android.content.Context;
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
import com.rejointech.jobber.Adapters.AdapterpopularJobs;
import com.rejointech.jobber.Decoration.DecorationForRecyclerView;
import com.rejointech.jobber.Fragments.JobDescription.jobDescFragment;
import com.rejointech.jobber.R;
import com.rejointech.jobber.RecyclerClickListeners.RecyclerHomeFeaturedCompleteClick;
import com.rejointech.jobber.Utils.CommonMethods;
import com.rejointech.jobber.Utils.Constants;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class homeFragment extends Fragment {

    RecyclerView featuredJobsRecycler;
    RecyclerView popularJobsRecycler;
    Context thiscontext;
    AdapterFeaturedJobs adapterFeaturedJobs;
    AdapterpopularJobs adapterpopularJobs;
    RecyclerHomeFeaturedCompleteClick recyclerHomeFeaturedCompleteClick;


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
        return root;
    }

    private void popularjobsOnClicks() {
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
                            adapterpopularJobs = new AdapterpopularJobs(myres, getActivity(), thiscontext);
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
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.maincontainerview, new jobDescFragment()).addToBackStack(null).commit();

            }
        };
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
        featuredJobsRecycler = root.findViewById(R.id.featuredJobsRecycler);
        popularJobsRecycler = root.findViewById(R.id.popularJobsRecycler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(thiscontext, 1, GridLayoutManager.HORIZONTAL, false);
        GridLayoutManager gridLayoutManagerpopular = new GridLayoutManager(thiscontext, 1, GridLayoutManager.VERTICAL, false);
        featuredJobsRecycler.setLayoutManager(gridLayoutManager);
        popularJobsRecycler.setLayoutManager(gridLayoutManagerpopular);
        featuredJobsRecycler.addItemDecoration(new DecorationForRecyclerView(thiscontext, R.dimen.dp_2));
        popularJobsRecycler.addItemDecoration(new DecorationForRecyclerView(thiscontext, R.dimen.dp_2));
    }
}