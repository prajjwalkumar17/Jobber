package com.rejointech.jobber.Utils;

public class Constants {
    public static final int SPLASH_TIMEOUT=10;
    public static final int URL_TIMEOUT = 20;
    public static String bearer = "Bearer ";
    public static String LOG = "###-----------LOG IS HEre BIG ONE-------------------###############";
    public static int noofFeaturedJobsonHomesc=3;

    //URL
    public static final String baseurl="https://jobbers-api.herokuapp.com/";
    public static final String versioning=baseurl+"api/v1/";
    public static final String signupurl=versioning+"auth/signup";
    public static final String loginurl=versioning+"auth/login";
    public static final String featuredJobsurl=versioning+"jobs/featured";
    public static final String recommendedJobsurl=versioning+"jobs/recommended";
    public static final String allJobsurl=versioning+"jobs/";
    public static final String dpurl=baseurl+"ProfilePics/";
    public static String appliedJobs=versioning+"users/appliedJobs";
    public static String bookmarkedjobs=versioning+"jobs/myBookmarks";
    public static String applytoJob=versioning+"jobs/apply/";
    public static String bookmarktheJob=versioning+"jobs/bookmark/";
    public static String getme=versioning+"users/me";

    //SIGNUPBODY
    public static final String SIGNUPBODY_NAME = "Name";
    public static final String SIGNUPBODY_EMAIL = "Email";
    public static final String SIGNUPBODY_PASSWORD = "Password";
    public static final String SIGNUPBODY_CNFPASSWORD = "Password_confirm";
    public static final String SIGNUPBODY_ROLE = "Role";
    //SHAREDPREF
    public static final String TOKENPREF = "TOKENPREF";
    public static final String TOKEN = "TOKEN";

    public static final String JDPREFS="JSPREFS";
    public static final String JDid="id";
    public static final String JDjob_description="JDjob_description";
    public static final String JDjob_role="JDjob_role";
    public static final String JDjob_type="JDjob_type";
    public static final String JDduration="JDduration";
    public static final String JDexperience_required="JDexperience_required";
    public static final String JDsalary="JDsalary";
    public static final String JDcompany_name="company_name";
    public static final String JDlocation="JDlocation";
    public static final String JDabout_company="JDabout_company";
    public static String JDresponsibilities="JDresponsibilities";


    public static final String PROFILEPREFS = "PROFILEPREFS";
    public static String PROFILEemail="PROFILEemail";
    public static String PROFILEname="PROFILEname";
    public static String PROFILEcurrent_designation="PROFILEcurrent_designation";
    public static String PROFILEdp="PROFILEdp";
    public static String PROFILEresume="PROFILEresume";

}
