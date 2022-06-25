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
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.maincontainerview, new jobDescFragment()).addToBackStack(null).commit();

            }
        };
    }

    private void setrecommendedJobs() {
        APICall.okhttpMaster().newCall(APICall.get4recommended(APICall.urlBuilder4http(Constants.recommendedJobsurl),token)).enqueue(new Callback() {
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
                            adapterpopularJobs = new AdapterpopularJobs(myres, getActivity(), thiscontext,recyclerHomePopularOnClick);
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