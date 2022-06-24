package com.rejointech.jobber.Utils;

public class Constants {
    public static final int SPLASH_TIMEOUT=10;
    public static final int URL_TIMEOUT = 20;
    public static String LOG = "###-----------LOG IS HEre BIG ONE-------------------###############";

    //URL
    public static final String baseurl="https://jobbers-api.herokuapp.com/";
    public static final String versioning=baseurl+"api/v1/";
    public static final String signupurl=versioning+"auth/signup";
    public static final String loginurl=versioning+"auth/login";

    //SIGNUPBODY
    public static final String SIGNUPBODY_NAME = "Name";
    public static final String SIGNUPBODY_EMAIL = "Email";
    public static final String SIGNUPBODY_PASSWORD = "Password";
    public static final String SIGNUPBODY_CNFPASSWORD = "Password_confirm";
    public static final String SIGNUPBODY_ROLE = "Role";
    //SHAREDPREF
    public static final String TOKENPREF = "TOKENPREF";
    public static final String TOKEN = "TOKEN";
}
