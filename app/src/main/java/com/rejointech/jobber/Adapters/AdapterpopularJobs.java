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
import com.rejointech.jobber.Utils.CommonMethods;
import com.rejointech.jobber.Utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterpopularJobs extends RecyclerView.Adapter<AdapterpopularJobs.recycler> {
    JSONObject object;
    Activity myactivity;
    Context thisContext;
    int length;

    public AdapterpopularJobs(JSONObject object, Activity myactivity, Context thisContext) {
        this.object = object;
        this.myactivity = myactivity;
        this.thisContext = thisContext;
    }

    @NonNull
    @Override
    public recycler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_popular,parent,false);
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
        JSONObject data=object.optJSONObject("data");
        if (Integer.parseInt(results)>0){
            JSONArray featuredjobsarray = data.optJSONArray("allJobs");
            JSONObject realres = featuredjobsarray.optJSONObject(position);
            String Company_name = realres.optString(thisContext.getString(R.string.JOBSfeaturedJob_compname));
            String Location = realres.optString(thisContext.getString(R.string.JOBSfeaturedJob_loc));
            String Job_role = realres.optString(thisContext.getString(R.string.JOBSfeaturedJob_jobROle));
            String Job_type = realres.optString(thisContext.getString(R.string.JOBSfeaturedJob_type));
            String Duration = realres.optString(thisContext.getString(R.string.JOBSfeaturedJob_duration));
            String Salary = realres.optString(thisContext.getString(R.string.JOBSfeaturedJob_salary));
            String Experience_required = realres.optString(thisContext.getString(R.string.JOBSfeaturedJob_expreq));
            CommonMethods.LOGthesite(Constants.LOG,Salary+"  /n"+Job_type+"  /n"+Duration+"  /n"
                    +Experience_required+"  /n");
  /*          String id = realres.optString("_id");
            String About_company = realres.optString("About_company");
            String Application_deadline = realres.optString("Application_deadline");
            String Job_description = realres.optString("Job_description");
            String Responsibilities = realres.optString("Responsibilities");
            String Openings_available = realres.optString("Openings_available");
            String Total_Applicants = realres.optString("Total_Applicants");
            String Perks = realres.optString("Perks");
            String Featured = realres.optString("Featured");*/
            holder.recyc_popu_companyname.setText(Company_name);
            holder.recyc_popu_jobname.setText(Job_role);
            holder.recyc_popu_location.setText(Location);
            holder.recyc_popu_salary.setText(Salary+"/years");
        }
    }

    @Override
    public int getItemCount() {
        length = object.optJSONObject("data").optJSONArray("allJobs").length();
        return length;
    }

    public class recycler extends RecyclerView.ViewHolder{
        private TextView recyc_popu_jobname,recyc_popu_companyname,recyc_popu_salary,recyc_popu_location;
        private CircleImageView rec_rec_img;
        public recycler(@NonNull View itemView) {
            super(itemView);
            recyc_popu_jobname=itemView.findViewById(R.id.recyc_popu_jobname);
            recyc_popu_companyname=itemView.findViewById(R.id.recyc_popu_companyname);
            recyc_popu_salary=itemView.findViewById(R.id.recyc_popu_salary);
            recyc_popu_location=itemView.findViewById(R.id.recyc_popu_location);
        }
    }
}
