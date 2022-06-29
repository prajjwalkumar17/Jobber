package com.rejointech.jobber.APICall;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.rejointech.jobber.Utils.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;

public class APICall {
    public static OkHttpClient okhttpMaster(){
        return new OkHttpClient.Builder()
                .readTimeout(Constants.URL_TIMEOUT, TimeUnit.SECONDS)
                .addNetworkInterceptor(new StethoInterceptor())
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build();
    }

    public static String urlBuilder4http(String url){
        HttpUrl.Builder urlBuilder=HttpUrl.parse(url).newBuilder();
        return urlBuilder.build().toString();
    }

    //TODO Post requests
    public static Request post4SignUp(String url, RequestBody requestBody){
        return new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
    }
    public static RequestBody signupBody(String name,String email,String password,String cnfpassword,String role,String... skills){
        MultipartBody.Builder multipartBody=  new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(Constants.SIGNUPBODY_NAME,name)
                .addFormDataPart(Constants.SIGNUPBODY_EMAIL,email)
                .addFormDataPart(Constants.SIGNUPBODY_PASSWORD,password)
                .addFormDataPart(Constants.SIGNUPBODY_CNFPASSWORD,cnfpassword)
                .addFormDataPart(Constants.SIGNUPBODY_ROLE,role);
        for(String i:skills)
            multipartBody.addFormDataPart(Constants.SIGNUPBODY_SKILLS,i);
            return multipartBody.build();



//
    }
    public static Request post4login(String url, RequestBody requestBody){
        return new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
    }
    public static RequestBody loginBody(String email,String password){
        return  new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(Constants.SIGNUPBODY_EMAIL,email)
                .addFormDataPart(Constants.SIGNUPBODY_PASSWORD,password)
                .build();
    }
    public static Request get4applyjob(String url,String userauthtoken){
        return new Request.Builder()
                .url(url)
                .header("Authorization", Constants.bearer + userauthtoken)
                .build();
    }

    public static Request bookmarkaJob(String url,String userauthtoken){
        return new Request.Builder()
                .url(url)
                .header("Authorization", Constants.bearer + userauthtoken)
                .build();
    }
    //TODO get requests
    public static Request get4featuredJobs(String url){
        return new Request.Builder()
                .url(url)
                .build();
    }
    public static Request get4allJobs(String url){
        return new Request.Builder()
                .url(url)
                .build();
    }
    public static Request get4recommended(String url, String userauthtoken) {
        return new Request.Builder()
                .header("Authorization", Constants.bearer + userauthtoken)
                .url(url)
                .build();
    }
    public static Request get4appliedJobs(String url, String userauthtoken) {
        return new Request.Builder()
                .header("Authorization", Constants.bearer + userauthtoken)
                .url(url)
                .build();
    }
    public static Request get4me(String url, String userauthtoken) {
        return new Request.Builder()
                .header("Authorization", Constants.bearer + userauthtoken)
                .url(url)
                .build();
    }    public static Request get4dp(String url) {
        return new Request.Builder()
                .url(url)
                .build();
    }

}

