package com.ardunioSerial.crux.ui.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ardunioSerial.crux.StaticValue;
import com.ardunioSerial.crux.utils.MySession;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.ardunioSerial.crux.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import ru.katso.livebutton.LiveButton;

public class NewlineActivity extends AppCompatActivity {

    private CheckBox checkBox;
    LiveButton savebutton;

    RelativeLayout relativeLayout;
    AdView  mAdView;
    MySession mySession;

    ImageView imageview_Ads_jlcpcb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newline);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle("Serial Monitor Settings");

        relativeLayout=findViewById(R.id.Relative_serial_set);

        checkBox=findViewById(R.id.checkbox_serial_monitor_setting);
        savebutton=findViewById(R.id.save_serial_button_ID);

        imageview_Ads_jlcpcb=findViewById(R.id.imageview_Ads_jlcpcb);

        mAdView = findViewById(R.id.adView_newline_control);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mySession = new MySession(this);

        if (mySession.isUserPurchased()) {
            mAdView.setVisibility(View.GONE);
        }


        Intent intent=getIntent();
        String value=intent.getStringExtra("key");

        if (value != null && value.equals("1st")){

relativeLayout.setVisibility(View.VISIBLE);

            loadCheckBox(checkBox,value);

            savebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (checkBox.isChecked()){
                        setdata(value,true);
                    }
                    else{
                        setdata(value,false);
                    }

                }
            });

            MySession mySession = new MySession(this);
            if (mySession.isUserPurchased()) {
                imageview_Ads_jlcpcb.setVisibility(View.GONE);
            }

            imageview_Ads_jlcpcb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    webintent(StaticValue.NEW_JLCPCB_MAY21);
                }
            });


        }
        if (value != null &&  value.equals("2nd")){
            relativeLayout.setVisibility(View.VISIBLE);

            loadCheckBox(checkBox,value);

            savebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (checkBox.isChecked()){
                        setdata(value,true);
                    }
                    else{
                        setdata(value,false);
                    }

                }
            });



        }
        if (value != null && value.equals("3rd")){
            relativeLayout.setVisibility(View.VISIBLE);

            loadCheckBox(checkBox,value);

            savebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (checkBox.isChecked()){
                        setdata(value,true);
                    }
                    else{
                        setdata(value,false);
                    }

                }
            });



        }
    }

    private void setdata(String value, boolean b) {
        SharedPreferences sharedPreferences=getSharedPreferences(value,MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("checkbox",b);
        editor.commit();

    }


    private void loadCheckBox(CheckBox checkBox, String key) {
        if (isCheckboxChecked(key)) {
            Log.d("bal", key);
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }
    }


    private Boolean isCheckboxChecked(String key) {
        SharedPreferences sharedPreferences = getSharedPreferences(key, Context.MODE_PRIVATE);
        if ((sharedPreferences.contains("checkbox"))) {
            Log.d("chckbx", String.valueOf(sharedPreferences.getBoolean("checkbox", false)));
            return sharedPreferences.getBoolean("checkbox", false);
        }
        return false;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Animatoo.animateSlideRight(this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        if (item.getItemId() == android.R.id.home) {

            this.finish();
            Animatoo.animateSlideRight(this);
        }
        return super.onOptionsItemSelected(item);
    }

    private void webintent(String link) {
        try {
            if (!URLUtil.isValidUrl(link)) {
                Toast.makeText(this, " This is not a valid link", Toast.LENGTH_LONG).show();
            } else {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(link));
                startActivity(intent);
            }
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, " You don't have any browser to open web page", Toast.LENGTH_LONG).show();
        }
    }

}