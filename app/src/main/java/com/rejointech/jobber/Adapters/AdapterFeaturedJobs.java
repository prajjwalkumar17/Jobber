package com.rejointech.jobber.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rejointech.jobber.R;
import com.rejointech.jobber.RecyclerClickListeners.RecyclerHomeFeaturedCompleteClick;
import com.rejointech.jobber.Utils.CommonMethods;
import com.rejointech.jobber.Utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AdapterFeaturedJobs extends RecyclerView.Adapter<AdapterFeaturedJobs.recycler> {

    JSONObject object;
    Activity myactivity;
    Context thisContext;
    RecyclerHomeFeaturedCompleteClick recyclerHomeFeaturedCompleteClick;
    int length;

    public AdapterFeaturedJobs(JSONObject object, Activity myactivity, Context thisContext, RecyclerHomeFeaturedCompleteClick recyclerHomeFeaturedCompleteClick) {
        this.object = object;
        this.myactivity = myactivity;
        this.thisContext = thisContext;
        this.recyclerHomeFeaturedCompleteClick = recyclerHomeFeaturedCompleteClick;
    }

    @NonNull
    @Override
    public AdapterFeaturedJobs.recycler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_featured, parent, false);
        return new recycler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFeaturedJobs.recycler holder, int position) {

        try {
            parseAndSetData(position, holder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseAndSetData(int position, recycler holder) {
        String status = object.optString(myactivity.getString(R.string.JOBSfeaturedJob_status));
        String results = object.optString(myactivity.getString(R.string.JOBSfeaturedJob_noofresults));
        if (Integer.parseInt(results) > 0) {
            JSONArray featuredjobsarray = object.optJSONArray(myactivity.getString(R.string.JOBSfeaturedJob_realarray));
            JSONObject realres = featuredjobsarray.optJSONObject(position);
            String Company_name = realres.optString(thisContext.getString(R.string.JOBSfeaturedJob_compname));
            String Location = realres.optString(thisContext.getString(R.string.JOBSfeaturedJob_loc));
            String Job_role = realres.optString(thisContext.getString(R.string.JOBSfeaturedJob_jobROle));
            String Job_type = realres.optString(thisContext.getString(R.string.JOBSfeaturedJob_type));
            String Duration = realres.optString(thisContext.getString(R.string.JOBSfeaturedJob_duration));
            String Salary = realres.optString(thisContext.getString(R.string.JOBSfeaturedJob_salary));
            String Experience_required = realres.optString(thisContext.getString(R.string.JOBSfeaturedJob_expreq));
  /*          String id = realres.optString("_id");
            String About_company = realres.optString("About_company");
            String Application_deadline = realres.optString("Application_deadline");
            String Job_description = realres.optString("Job_description");
            String Responsibilities = realres.optString("Responsibilities");
            String Openings_available = realres.optString("Openings_available");
            String Total_Applicants = realres.optString("Total_Applicants");
            String Perks = realres.optString("Perks");
            String Featured = realres.optString("Featured");*/
            holder.rec_feat_comname.setText(Company_name);
            holder.rec_feat_jobRole.setText(Job_role);
            holder.rec_feat_jobloc.setText(Location);
            holder.rec_feat_jobloc.setText(Location);
            holder.rec_feat_jobsal.setText(Salary);
            holder.rec_feat_jobtype.setText(Job_type);
            holder.rec_feat_expreq.setText(Experience_required + "years+");
            holder.rec_feat_duration.setText(Duration);


        }
    }

    @Override
    public int getItemCount() {

        length = object.optJSONArray("featuredJobs").length();
        if (length < Constants.noofFeaturedJobsonHomesc)
            return length;
        else
            return Constants.noofFeaturedJobsonHomesc;
    }

    public class recycler extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView rec_feat_jobsal, rec_feat_jobloc, rec_feat_jobRole, rec_feat_duration,
                rec_feat_comname, rec_feat_jobtype, rec_feat_expreq;
        private ImageView recycler_feat_bookmarkbot, rec_feat_joblogo, rec_feat_completeselbot;

        public recycler(@NonNull View itemView) {
            super(itemView);
            initViews(itemView);
            itemView.setOnClickListener(this);
        }

        private void initViews(View itemView) {
            rec_feat_jobsal = itemView.findViewById(R.id.rec_feat_jobsal);
            rec_feat_jobloc = itemView.findViewById(R.id.rec_feat_jobloc);
            rec_feat_jobRole = itemView.findViewById(R.id.rec_feat_jobRole);
            rec_feat_comname = itemView.findViewById(R.id.rec_feat_comname);
            rec_feat_duration = itemView.findViewById(R.id.rec_feat_duration);
            rec_feat_jobtype = itemView.findViewById(R.id.rec_feat_jobtype);
            rec_feat_expreq = itemView.findViewById(R.id.rec_feat_expreq);
//            rec_feat_joblogo=itemView.findViewById(R.id.rec_feat_joblogo);
//            recycler_feat_bookmarkbot=itemView.findViewById(R.id.recycler_feat_bookmarkbot);
//            rec_feat_completeselbot=itemView.findViewById(R.id.rec_feat_completeselbot);
        }

        @Override
        public void onClick(View view) {
            recyclerHomeFeaturedCompleteClick.onItemClick(itemView, getAdapterPosition(), object);
        }
    }
}
