package com.ardunioSerial.crux;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class AdWebViewFragment extends Fragment {

    WebView wvLink;
    ProgressBar progressBar;
    AppCompatTextView tvNoInternet;

    public AdWebViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ad_web_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        wvLink = view.findViewById(R.id.wvLink);
        progressBar = view.findViewById(R.id.progressBar);
        tvNoInternet = view.findViewById(R.id.tvNoInternet);


        wvLink.getSettings().setJavaScriptEnabled(true);
        wvLink.getSettings().setBuiltInZoomControls(true);
        wvLink.setWebViewClient(new WebViewController());
        wvLink.loadUrl(StaticValue.CUSTOM_AD_SITE_PCBWAY);
        //progressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorPrimary), android.graphics.PorterDuff.Mode.MULTIPLY);
        wvLink.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (progressBar != null)
                    progressBar.setProgress(newProgress);
            }

            @Nullable
            @Override
            public Bitmap getDefaultVideoPoster() {
                if (super.getDefaultVideoPoster() == null) {
                    return BitmapFactory.decodeResource(view.getContext().getResources(),
                            R.drawable.ok2);
                } else {
                    return super.getDefaultVideoPoster();
                }
            }

        });

        // handling back press
        wvLink.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK
                    && event.getAction() == MotionEvent.ACTION_UP
                    && wvLink.canGoBack()) {
                wvLink.goBack();
                return true;
            }
            return false;
        });

        if (!checkInternetConnection(view.getContext())) {
            wvLink.setVisibility(View.GONE);
            tvNoInternet.setVisibility(View.VISIBLE);
        }else{
            wvLink.setVisibility(View.VISIBLE);
            tvNoInternet.setVisibility(View.GONE);
        }

    }


    @Override
    public void onPause() {
        super.onPause();
        wvLink.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        wvLink.onResume();
    }

    public boolean checkInternetConnection(Context context) {

        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivity != null) {
            NetworkInfo[] inf = connectivity.getAllNetworkInfo();
            if (inf != null)
                for (int i = 0; i < inf.length; i++)
                    if (inf[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }

    public class WebViewController extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
