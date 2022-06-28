package com.rejointech.jobber.Fragments.Profile;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.rejointech.jobber.Containers.HomeContainer;
import com.rejointech.jobber.R;
import com.rejointech.jobber.Utils.CommonMethods;
import com.rejointech.jobber.Utils.Constants;


public class resumeviewFragment extends Fragment {
    WebView webView;
    Context thiscontext;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        thiscontext = context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        initScreen();
        View root=inflater.inflate(R.layout.fragment_resumeview, container, false);
        final ProgressDialog progressDialog = new ProgressDialog(thiscontext);
        progressDialog.setMessage("Loading Data...");
        progressDialog.setCancelable(false);
         webView=root.findViewById(R.id.resumeview);
        SharedPreferences sharedPreferences = thiscontext.getSharedPreferences(Constants.PROFILEPREFS, Context.MODE_PRIVATE);
        String url = sharedPreferences.getString(Constants.PROFILEresume, "No data found!!!");
        String resumeLink=Constants.resumeurl+url;

        CommonMethods.LOGthesite(Constants.LOG,resumeLink);

        webView.requestFocus();
        WebSettings settings = webView.getSettings();
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(true);
        settings.setJavaScriptEnabled(true);
//        webView.setWebViewClient(new Callback());
//        webView.loadUrl(resumeLink);
        String urlString = resumeLink;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setPackage("com.android.chrome");
        try {
            thiscontext.startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            // Chrome browser presumably not installed so allow user to choose instead
            intent.setPackage(null);
            thiscontext.startActivity(intent);
        }
//        webView.loadUrl("https://www.google.com/");
        onPageFinished(webView,resumeLink);


        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress < 100) {
                    progressDialog.show();
                }
                if (progress == 100) {
                    progressDialog.dismiss();
                }
            }
        });

        return root;
    }
    public void onPageFinished(WebView view, String url) {
        if (view.getTitle().equals(""))
            view.reload();
    }
    private void initScreen() {
        ((HomeContainer) getActivity()).setToolbarInvisible();
        ((HomeContainer) getActivity()).setDrawerLocked();
        ((HomeContainer) getActivity()).setbotInvisible();
        ((HomeContainer) getActivity()).setfabinvisible();
    }

    private class Callback extends WebViewClient {
        @Override
        public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
            return false;
        }
    }
}