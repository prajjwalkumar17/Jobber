package com.rejointech.jobber.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rejointech.jobber.R;
import com.rejointech.jobber.RecyclerClickListeners.RecyclerHomePopularOnClick;
import com.rejointech.jobber.RecyclerClickListeners.RecylerHomeRecommendedRecommendedOnClick;
import com.rejointech.jobber.Utils.CommonMethods;
import com.rejointech.jobber.Utils.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterRecommendedJobs extends RecyclerView.Adapter<AdapterRecommendedJobs.recycler> {
    JSONObject object;
    Activity myactivity;
    Context thisContext;
     RecylerHomeRecommendedRecommendedOnClick recylerHomeRecommendedRecommendedOnClick;
    int length;

    public AdapterRecommendedJobs(JSONObject object, Activity myactivity, Context thisContext, RecylerHomeRecommendedRecommendedOnClick recylerHomeRecommendedRecommendedOnClick) {
        this.object = object;
        this.myactivity = myactivity;
        this.thisContext = thisContext;
        this.recylerHomeRecommendedRecommendedOnClick = recylerHomeRecommendedRecommendedOnClick;
    }

    @NonNull
    @Override
    public recycler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_home_recommended,parent,false);
        return new recycler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull recycler holder, int position) {
        try{
            parseAndSetData(position, holder);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void parseAndSetData(int position, recycler holder) {
        String status=object.optString(myactivity.getString(R.string.JOBSfeaturedJob_status));
        String results=object.optString(myactivity.getString(R.string.JOBSfeaturedJob_noofresults));
        CommonMethods.LOGthesite(Constants.LOG,results);
        if (Integer.parseInt(results)>0){
            JSONArray featuredjobsarray = object.optJSONArray("recommendedJobs");
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
            holder.recyc_rec_comnname.setText(Company_name);
            holder.recyc_rec_jobRole.setText(Job_role);
            holder.recyc_rec_Salary.setText(Salary);
        }
    }

    @Override
    public int getItemCount() {
        length= object.optJSONArray("recommendedJobs").length();
        return length;
    }

    public class recycler extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView recyc_rec_jobRole,recyc_rec_comnname,recyc_rec_Salary;
        CircleImageView rec_rec_img;
        public recycler(@NonNull View itemView) {
            super(itemView);
            initViews(itemView);
            itemView.setOnClickListener(this);
        }

        private void initViews(View itemView) {

            rec_rec_img=itemView.findViewById(R.id.rec_rec_img);
            recyc_rec_jobRole=itemView.findViewById(R.id.recyc_rec_jobRole);
            recyc_rec_comnname=itemView.findViewById(R.id.recyc_rec_comnname);
            recyc_rec_Salary=itemView.findViewById(R.id.recyc_rec_Salary);
        }

        @Override
        public void onClick(View view) {
            recylerHomeRecommendedRecommendedOnClick.onItemClick(itemView,getAdapterPosition(),object);
        }
    }
}
