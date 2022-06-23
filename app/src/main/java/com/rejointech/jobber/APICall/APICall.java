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

    public static Request post4SignUp(String url, RequestBody requestBody){
        return new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
    }
    public static RequestBody signupBody(String name,String email,String password,String cnfpassword,String role){
        return  new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(Constants.SIGNUPBODY_NAME,name)
                .addFormDataPart(Constants.SIGNUPBODY_EMAIL,email)
                .addFormDataPart(Constants.SIGNUPBODY_PASSWORD,password)
                .addFormDataPart(Constants.SIGNUPBODY_CNFPASSWORD,cnfpassword)
                .addFormDataPart(Constants.SIGNUPBODY_ROLE,role)
                .build();
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

}
