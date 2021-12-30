package com.ardunioSerial.crux.ui.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ardunioSerial.crux.StaticValue;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.ardunioSerial.crux.utils.MyApp;
import com.ardunioSerial.crux.utils.MySession;
import com.ardunioSerial.crux.R;
import com.google.android.gms.ads.LoadAdError;

import ru.katso.livebutton.LiveButton;


public class ButtonControlActivity extends AppCompatActivity {
    LiveButton savebutton1, savebutton2, savebutton3, savebutton4, savebutton5, savebutton6, savebutton7, savebutton8, savebutton9,
            savebutton10, savebutton11, savebutton12, savebutton13, savebutton14, savebutton15, savebutton16;

    EditText etnamebt1, etnamebt2, etnamebt3, etnamebt4, etnamebt5, etnamebt6,
            etdatabt1, etdatabt2, etdatabt3, etdatabt4, etdatabt5, etdatabt6,
            etconttimebt1, etconttimebt2, etconttimebt3, etconttimebt4, etconttimebt5, etconttimebt6;

    EditText slidername1, slidermin1, slidermax1, sliderstring1, slidername3, slidermin3, slidermax3, sliderstring3,
            slidername2, slidermin2, slidermax2, sliderstring2, slidername4, slidermin4, slidermax4, sliderstring4;

    EditText switchstring1, switchname1, switchtime1, switchstring2, switchname2, switchtime2,
            switchstring3, switchname3, switchtime3, switchstring4, switchname4, switchtime4,
            switchstring5, switchname5, switchtime5, switchstring6, switchname6, switchtime6;

    CheckBox checkBoxbt1, checkBoxbt2, checkBoxbt3, checkBoxbt4, checkBoxbt5, checkBoxbt6;
    CheckBox checkBoxbtS1, checkBoxbtS2, checkBoxbtS3, checkBoxbtS4, checkBoxbtS5, checkBoxbtS6;

    private static final String TAG = "ButtonControlActivity";


    ImageView imageview_Ads_jlcpcb;

    private InterstitialAd mInterstitialAd;
    MySession mySession;
    AdRequest adRequest1;
    CardView cardView1, cardView2, cardView3, cardView4, cardView5, cardView6, cardView7, cardView8, cardView9, cardView10, cardView11, cardView12, cardView13, cardView14, cardView15, cardView16;

    private AdView mAdView;
    RelativeLayout bnner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_control);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle("Set data");

        imageview_Ads_jlcpcb=findViewById(R.id.imageview_Ads_jlcpcb);

        cardView1 = findViewById(R.id.cardview_btc1);
        cardView2 = findViewById(R.id.cardview_btc2);
        cardView3 = findViewById(R.id.cardview_btc3);
        cardView4 = findViewById(R.id.cardview_btc4);
        cardView5 = findViewById(R.id.cardview_btc5);
        cardView6 = findViewById(R.id.cardview_btc6);
        cardView7 = findViewById(R.id.cardview_btc7);
        cardView8 = findViewById(R.id.cardview_btc8);
        cardView9 = findViewById(R.id.cardview_btc9);
        cardView10 = findViewById(R.id.cardview_btc10);
        cardView11 = findViewById(R.id.cardview_btc11);
        cardView12 = findViewById(R.id.cardview_btc12);
        cardView13 = findViewById(R.id.cardview_btc13);
        cardView14 = findViewById(R.id.cardview_btc14);
        cardView15 = findViewById(R.id.cardview_btc15);
        cardView16 = findViewById(R.id.cardview_btc16);


        checkBoxbtS1 = findViewById(R.id.continuous_check_btcC1);
        checkBoxbtS2 = findViewById(R.id.continuous_check_btcC2);
        checkBoxbtS3 = findViewById(R.id.continuous_check_btcC3);
        checkBoxbtS4 = findViewById(R.id.continuous_check_btcC4);
        checkBoxbtS5 = findViewById(R.id.continuous_check_btcC5);
        checkBoxbtS6 = findViewById(R.id.continuous_check_btcC6);


        //save buttons
        savebutton1 = findViewById(R.id.savebutton_btc_1);
        savebutton2 = findViewById(R.id.savebutton_btc_2);
        savebutton3 = findViewById(R.id.savebutton_btc_3);
        savebutton4 = findViewById(R.id.savebutton_btc_4);
        savebutton5 = findViewById(R.id.savebutton_btc_5);
        savebutton6 = findViewById(R.id.savebutton_btc_6);
        savebutton7 = findViewById(R.id.savebutton_btc_7);
        savebutton8 = findViewById(R.id.savebutton_btc_8);
        savebutton9 = findViewById(R.id.savebutton_btc_9);
        savebutton10 = findViewById(R.id.savebutton_btc_10);
        savebutton11 = findViewById(R.id.savebutton_btc_11);
        savebutton12 = findViewById(R.id.savebutton_btc_12);
        savebutton13 = findViewById(R.id.savebutton_btc_13);
        savebutton14 = findViewById(R.id.savebutton_btc_14);
        savebutton15 = findViewById(R.id.savebutton_btc_15);
        savebutton16 = findViewById(R.id.savebutton_btc_16);

        //butttons
        etnamebt1 = findViewById(R.id.name_et_btc11);
        etdatabt1 = findViewById(R.id.data_et_btc_12);
        etconttimebt1 = findViewById(R.id.continuous_et_btc1);

        etnamebt2 = findViewById(R.id.name_et_btc21);
        etdatabt2 = findViewById(R.id.data_et_btc_22);
        etconttimebt2 = findViewById(R.id.continuous_et_btc2);

        etnamebt3 = findViewById(R.id.name_et_btc31);
        etdatabt3 = findViewById(R.id.data_et_btc_32);
        etconttimebt3 = findViewById(R.id.continuous_et_btc3);

        etnamebt4 = findViewById(R.id.name_et_btc41);
        etdatabt4 = findViewById(R.id.data_et_btc_42);
        etconttimebt4 = findViewById(R.id.continuous_et_btc4);

        etnamebt5 = findViewById(R.id.name_et_btc51);
        etdatabt5 = findViewById(R.id.data_et_btc_52);
        etconttimebt5 = findViewById(R.id.continuous_et_btc5);

        etnamebt6 = findViewById(R.id.name_et_btc61);
        etdatabt6 = findViewById(R.id.data_et_btc_62);
        etconttimebt6 = findViewById(R.id.continuous_et_btc6);


//checkboxes
        checkBoxbt1 = findViewById(R.id.continuous_check_btc1);
        checkBoxbt2 = findViewById(R.id.continuous_check_btc2);
        checkBoxbt3 = findViewById(R.id.continuous_check_btc3);
        checkBoxbt4 = findViewById(R.id.continuous_check_btc4);
        checkBoxbt5 = findViewById(R.id.continuous_check_btc5);
        checkBoxbt6 = findViewById(R.id.continuous_check_btc6);

        //sliders
        slidermin1 = findViewById(R.id.start_number_et_btc);
        slidermax1 = findViewById(R.id.end_number_et_btc);
        sliderstring1 = findViewById(R.id.string_et_btc70);
        slidername1 = findViewById(R.id.name_et_btc71);


        slidermin2 = findViewById(R.id.start_number_et_btc2);
        slidermax2 = findViewById(R.id.end_number_et_btc2);
        sliderstring2 = findViewById(R.id.string_et_btc80);
        slidername2 = findViewById(R.id.name_et_btc81);


        slidermin3 = findViewById(R.id.start_number_et_btc3);
        slidermax3 = findViewById(R.id.end_number_et_btc3);
        sliderstring3 = findViewById(R.id.string_et_btc90);
        slidername3 = findViewById(R.id.name_et_btc91);

        slidermin4 = findViewById(R.id.start_number_et_btc4);
        slidermax4 = findViewById(R.id.end_number_et_btc4);
        sliderstring4 = findViewById(R.id.string_et_btc100);
        slidername4 = findViewById(R.id.name_et_btc101);


        //switches


        //switches details
        switchname1 = findViewById(R.id.string_et_btc110);
        switchstring1 = findViewById(R.id.name_et_btc111);
        switchtime1 = findViewById(R.id.name_et_btc112);

        switchname2 = findViewById(R.id.string_et_btc120);
        switchstring2 = findViewById(R.id.name_et_btc121);
        switchtime2 = findViewById(R.id.name_et_btc122);

        switchname3 = findViewById(R.id.string_et_btc130);
        switchstring3 = findViewById(R.id.name_et_btc131);
        switchtime3 = findViewById(R.id.name_et_btc132);

        switchname4 = findViewById(R.id.string_et_btc140);
        switchstring4 = findViewById(R.id.name_et_btc141);
        switchtime4 = findViewById(R.id.name_et_btc142);

        switchname5 = findViewById(R.id.string_et_btc150);
        switchstring5 = findViewById(R.id.name_et_btc151);
        switchtime5 = findViewById(R.id.name_et_btc152);

        switchname6 = findViewById(R.id.string_et_btc160);
        switchstring6 = findViewById(R.id.name_et_btc161);
        switchtime6 = findViewById(R.id.name_et_btc162);

        bnner = findViewById(R.id.ad_layout_button_control);

        mAdView = findViewById(R.id.adView_button_control);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mInterstitialAd = new InterstitialAd(this);
        adRequest1 = new AdRequest.Builder().build();
        mInterstitialAd.setAdUnitId(getString(R.string.admob_interstitial_one));
        mInterstitialAd.setAdListener(new AdListener() {

            @Override
            public void onAdLoaded() {
                Log.d(TAG, "onAdFailedToLoad: " + "loaded");

                super.onAdLoaded();
            }

            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Log.d(TAG, "onAdFailedToLoad: " + loadAdError.getMessage());
            }
        });
        mInterstitialAd.loadAd(adRequest1);

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

        mySession = new MySession(this);
        if (mySession.isUserPurchased()) {
            mAdView.setVisibility(View.GONE);
        }
//        else {
//            bnner.setVisibility(View.VISIBLE);
//        }

        Intent intent = getIntent();
        String value = intent.getStringExtra("fromfragment");


        if (value != null && value.equals("fragment3")) {

            cardView1.setVisibility(View.VISIBLE);
            cardView2.setVisibility(View.VISIBLE);
            cardView3.setVisibility(View.VISIBLE);
            cardView4.setVisibility(View.VISIBLE);
            cardView5.setVisibility(View.VISIBLE);
            cardView6.setVisibility(View.VISIBLE);
            cardView7.setVisibility(View.VISIBLE);
            cardView8.setVisibility(View.VISIBLE);
            cardView9.setVisibility(View.VISIBLE);
            cardView10.setVisibility(View.VISIBLE);
            cardView11.setVisibility(View.VISIBLE);
            cardView12.setVisibility(View.VISIBLE);
            cardView13.setVisibility(View.VISIBLE);
            cardView14.setVisibility(View.VISIBLE);
            cardView15.setVisibility(View.VISIBLE);
            cardView16.setVisibility(View.VISIBLE);
            LoadDatabutton("3btn1", etnamebt1, etdatabt1, etconttimebt1);
            LoadDatabutton("3btn2", etnamebt2, etdatabt2, etconttimebt2);
            LoadDatabutton("3btn3", etnamebt3, etdatabt3, etconttimebt3);
            LoadDatabutton("3btn4", etnamebt4, etdatabt4, etconttimebt4);
            LoadDatabutton("3btn5", etnamebt5, etdatabt5, etconttimebt5);
            LoadDatabutton("3btn6", etnamebt6, etdatabt6, etconttimebt6);

            LoadDataslider("3btn7", slidername1, sliderstring1, slidermin1, slidermax1);
            LoadDataslider("3btn8", slidername2, sliderstring2, slidermin2, slidermax2);
            LoadDataslider("3btn9", slidername3, sliderstring3, slidermin3, slidermax3);
            LoadDataslider("3btn10", slidername4, sliderstring4, slidermin4, slidermax4);

            LoadDataSwitch("3btn11", switchname1, switchstring1, switchtime1);
            LoadDataSwitch("3btn12", switchname2, switchstring2, switchtime2);
            LoadDataSwitch("3btn13", switchname3, switchstring3, switchtime3);
            LoadDataSwitch("3btn14", switchname4, switchstring4, switchtime4);
            LoadDataSwitch("3btn15", switchname5, switchstring5, switchtime5);
            LoadDataSwitch("3btn16", switchname6, switchstring6, switchtime6);


            savebuttonclick3rdfragment();

            setCheckBox(checkBoxbt1, "3btn1");
            setCheckBox(checkBoxbt2, "3btn2");
            setCheckBox(checkBoxbt3, "3btn3");
            setCheckBox(checkBoxbt4, "3btn4");
            setCheckBox(checkBoxbt5, "3btn5");
            setCheckBox(checkBoxbt6, "3btn6");

            //load switch checkbox
            setCheckBox(checkBoxbtS1, "3btn11");
            setCheckBox(checkBoxbtS2, "3btn12");
            setCheckBox(checkBoxbtS3, "3btn13");
            setCheckBox(checkBoxbtS4, "3btn14");
            setCheckBox(checkBoxbtS5, "3btn15");
            setCheckBox(checkBoxbtS6, "3btn16");

        }
        if (value != null && value.equals("fragment2")) {
            cardView1.setVisibility(View.VISIBLE);
            cardView2.setVisibility(View.VISIBLE);
            cardView3.setVisibility(View.VISIBLE);
            cardView4.setVisibility(View.VISIBLE);
            cardView5.setVisibility(View.VISIBLE);
            cardView6.setVisibility(View.VISIBLE);
            cardView7.setVisibility(View.VISIBLE);
            cardView8.setVisibility(View.VISIBLE);
            cardView11.setVisibility(View.VISIBLE);
            cardView12.setVisibility(View.VISIBLE);
            cardView13.setVisibility(View.VISIBLE);

            setCheckBox(checkBoxbt1, "2btn1");
            setCheckBox(checkBoxbt2, "2btn2");
            setCheckBox(checkBoxbt3, "2btn3");
            setCheckBox(checkBoxbt4, "2btn4");
            setCheckBox(checkBoxbt5, "2btn5");
            setCheckBox(checkBoxbt6, "2btn6");

            LoadDatabutton("2btn1", etnamebt1, etdatabt1, etconttimebt1);
            LoadDatabutton("2btn2", etnamebt2, etdatabt2, etconttimebt2);
            LoadDatabutton("2btn3", etnamebt3, etdatabt3, etconttimebt3);
            LoadDatabutton("2btn4", etnamebt4, etdatabt4, etconttimebt4);
            LoadDatabutton("2btn5", etnamebt5, etdatabt5, etconttimebt5);
            LoadDatabutton("2btn6", etnamebt6, etdatabt6, etconttimebt6);

            LoadDataslider("2btn7", slidername1, sliderstring1, slidermin1, slidermax1);
            LoadDataslider("2btn8", slidername2, sliderstring2, slidermin2, slidermax2);


            LoadDataSwitch("2btn11", switchname1, switchstring1, switchtime1);
            LoadDataSwitch("2btn12", switchname2, switchstring2, switchtime2);
            LoadDataSwitch("2btn13", switchname3, switchstring3, switchtime3);

//switch chkbx
            setCheckBox(checkBoxbtS1, "2btn11");
            setCheckBox(checkBoxbtS2, "2btn12");
            setCheckBox(checkBoxbtS3, "2btn13");

            savebuttonclick2ndfragment();


        }
        if (value != null && value.equals("fragment1")) {

            cardView1.setVisibility(View.VISIBLE);
            cardView2.setVisibility(View.VISIBLE);
            cardView3.setVisibility(View.VISIBLE);
            cardView7.setVisibility(View.VISIBLE);
            cardView11.setVisibility(View.VISIBLE);
            cardView12.setVisibility(View.VISIBLE);

            LoadDatabutton("1btn1", etnamebt1, etdatabt1, etconttimebt1);
            LoadDatabutton("1btn2", etnamebt2, etdatabt2, etconttimebt2);
            LoadDatabutton("1btn3", etnamebt3, etdatabt3, etconttimebt3);

            LoadDataslider("1btn7", slidername1, sliderstring1, slidermin1, slidermax1);

            LoadDataSwitch("1btn11", switchname1, switchstring1, switchtime1);
            LoadDataSwitch("1btn12", switchname2, switchstring2, switchtime2);


            setCheckBox(checkBoxbt1, "1btn1");
            setCheckBox(checkBoxbt2, "1btn2");
            setCheckBox(checkBoxbt3, "1btn3");

            //switch chkbx
            setCheckBox(checkBoxbtS1, "1btn11");
            setCheckBox(checkBoxbtS2, "1btn12");

            savebuttonclick1stfragment();

        }


    }


    private void savebuttonclick1stfragment() {
        savebutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                setdatabuttonAndswitch("1btn1", etnamebt1, etdatabt1, etconttimebt1, checkBoxbt1);


            }
        });
        savebutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setdatabuttonAndswitch("1btn2", etnamebt2, etdatabt2, etconttimebt2, checkBoxbt2);

            }
        });
        savebutton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setdatabuttonAndswitch("1btn3", etnamebt3, etdatabt3, etconttimebt3, checkBoxbt3);

            }
        });

        savebutton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setdataSlider("1btn7", sliderstring1, slidername1, slidermin1, slidermax1);
            }
        });


        savebutton11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setdatabuttonAndswitch("1btn11", switchname1, switchstring1, switchtime1, checkBoxbtS1);


            }
        });
        savebutton12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setdatabuttonAndswitch("1btn12", switchname2, switchstring2, switchtime2, checkBoxbtS2);
            }
        });
    }

    private void savebuttonclick2ndfragment() {
        savebutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                setdatabuttonAndswitch("2btn1", etnamebt1, etdatabt1, etconttimebt1, checkBoxbt1);


            }
        });
        savebutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setdatabuttonAndswitch("2btn2", etnamebt2, etdatabt2, etconttimebt2, checkBoxbt2);

            }
        });
        savebutton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setdatabuttonAndswitch("2btn3", etnamebt3, etdatabt3, etconttimebt3, checkBoxbt3);


            }
        });
        savebutton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setdatabuttonAndswitch("2btn4", etnamebt4, etdatabt4, etconttimebt4, checkBoxbt4);


            }
        });
        savebutton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setdatabuttonAndswitch("2btn5", etnamebt5, etdatabt5, etconttimebt5, checkBoxbt5);


            }
        });
        savebutton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setdatabuttonAndswitch("2btn6", etnamebt6, etdatabt6, etconttimebt6, checkBoxbt6);


            }
        });
        savebutton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setdataSlider("2btn7", sliderstring1, slidername1, slidermin1, slidermax1);

            }
        });
        savebutton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setdataSlider("2btn8", sliderstring2, slidername2, slidermin2, slidermax2);

            }
        });
        savebutton11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setdatabuttonAndswitch("2btn11", switchname1, switchstring1, switchtime1, checkBoxbtS1);

            }
        });
        savebutton12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setdatabuttonAndswitch("2btn12", switchname2, switchstring2, switchtime2, checkBoxbtS2);

            }
        });
        savebutton13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setdatabuttonAndswitch("2btn13", switchname3, switchstring3, switchtime3, checkBoxbtS3);

            }
        });
    }

    private void savebuttonclick3rdfragment() {


        savebutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setdatabuttonAndswitch("3btn1", etnamebt1, etdatabt1, etconttimebt1, checkBoxbt1);

            }
        });
        savebutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setdatabuttonAndswitch("3btn2", etnamebt2, etdatabt2, etconttimebt2, checkBoxbt2);

            }
        });
        savebutton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setdatabuttonAndswitch("3btn3", etnamebt3, etdatabt3, etconttimebt3, checkBoxbt3);

            }
        });
        savebutton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setdatabuttonAndswitch("3btn4", etnamebt4, etdatabt4, etconttimebt4, checkBoxbt4);

            }
        });
        savebutton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setdatabuttonAndswitch("3btn5", etnamebt5, etdatabt5, etconttimebt5, checkBoxbt5);

            }
        });
        savebutton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setdatabuttonAndswitch("3btn6", etnamebt6, etdatabt6, etconttimebt6, checkBoxbt6);

            }
        });
        savebutton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setdataSlider("3btn7", sliderstring1, slidername1, slidermin1, slidermax1);


            }
        });
        savebutton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                setdataSlider("3btn8", sliderstring2, slidername2, slidermin2, slidermax2);

            }
        });
        savebutton9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                setdataSlider("3btn9", sliderstring3, slidername3, slidermin3, slidermax3);

            }
        });
        savebutton10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setdataSlider("3btn10", sliderstring4, slidername4, slidermin4, slidermax4);

            }
        });

        savebutton11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setdatabuttonAndswitch("3btn11", switchname1, switchstring1, switchtime1, checkBoxbtS1);

            }
        });
        savebutton12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setdatabuttonAndswitch("3btn12", switchname2, switchstring2, switchtime2, checkBoxbtS2);

            }
        });
        savebutton13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setdatabuttonAndswitch("3btn13", switchname3, switchstring3, switchtime3, checkBoxbtS3);

            }
        });
        savebutton14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setdatabuttonAndswitch("3btn14", switchname4, switchstring4, switchtime4, checkBoxbtS4);

            }
        });
        savebutton15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setdatabuttonAndswitch("3btn15", switchname5, switchstring5, switchtime5, checkBoxbtS5);


            }
        });
        savebutton16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setdatabuttonAndswitch("3btn16", switchname6, switchstring6, switchtime6, checkBoxbtS6);

            }
        });
    }


    private void setCheckBox(CheckBox checkBox, String key) {
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

    private void setSharePrefbutton(String btnKey, Boolean checkbox, String name, String data, String time) {
        SharedPreferences sharedPreferencesCB1 = getSharedPreferences(btnKey, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferencesCB1.edit();
        editor.putBoolean("checkbox", checkbox);
        editor.putString("name", name);
        editor.putString("data", data);
        editor.putString("time", time);
        editor.commit();
        Toast.makeText(this, "data saved success", Toast.LENGTH_SHORT).show();
        Log.d("datasave", checkbox + name + data + time);
    }

    private void setSharePrefforslider(String btnKey, String string, String name, int min, int max) {
        SharedPreferences sharedPreferencesslider = getSharedPreferences(btnKey, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferencesslider.edit();
        editor.putString("string", string);
        editor.putString("name", name);
        editor.putInt("min", min);
        editor.putInt("max", max);
        editor.commit();
        Toast.makeText(this, "data saved success", Toast.LENGTH_SHORT).show();
        Log.d("sldata", string + " " + name + " " + min + " " + max);
    }


    private void LoadDatabutton(String btnkey, EditText editTextname, EditText editTextdata, EditText editTexttime) {
        SharedPreferences sharedPreferences1 = this.getSharedPreferences(btnkey, Context.MODE_PRIVATE);

        if ((sharedPreferences1.contains("name")) || (sharedPreferences1.contains("data"))) {

            String name = sharedPreferences1.getString("name", "no data saved");
            String data = sharedPreferences1.getString("data", "no data saved");
            String time = sharedPreferences1.getString("time", "no time set");

            int timebt = 1000;
            try {
                timebt = Integer.parseInt(time);
            } catch (NumberFormatException ex) { // handle your exception

            }

            editTextname.setText(name);
            editTextdata.setText(data);
            if (time.isEmpty()) {

            } else {
                editTexttime.setText(Integer.toString(timebt));
            }

        }


    }

    private void LoadDataslider(String btnkey, EditText editTextname, EditText editTextstring, EditText editTextmin, EditText editTextmax) {
        SharedPreferences sharedPreferences1 = this.getSharedPreferences(btnkey, Context.MODE_PRIVATE);
        if ((sharedPreferences1.contains("min")) && (sharedPreferences1.contains("max"))) {


            String name = sharedPreferences1.getString("name", "no data saved");
            String string = sharedPreferences1.getString("string", "no data saved");
            int min = sharedPreferences1.getInt("min", -10);
            int max = sharedPreferences1.getInt("max", -110);

            editTextname.setText(name);
            editTextstring.setText(string);

            if (min == -10) {

            } else {
                editTextmin.setText(Integer.toString(min));
            }
            if (max == -110) {

            } else {
                editTextmax.setText(Integer.toString(max));
            }
        }
    }

    private void LoadDataSwitch(String btnkey, EditText editTextname, EditText editTextdata, EditText editTexttime) {
        SharedPreferences sharedPreferences1 = this.getSharedPreferences(btnkey, Context.MODE_PRIVATE);
        if ((sharedPreferences1.contains("data")) || (sharedPreferences1.contains("time"))) {

            String name = sharedPreferences1.getString("name", "no data saved");
            String data = sharedPreferences1.getString("data", "no data saved");
            String time = sharedPreferences1.getString("time", "no time set");
            int timebt = 1000;
            try {
                timebt = Integer.parseInt(time);
            } catch (NumberFormatException ex) { // handle your exception

            }
            editTextname.setText(name);
            editTextdata.setText(data);
            if (time.isEmpty()) {

            } else {
                editTexttime.setText(Integer.toString(timebt));
            }
        }
    }


    public void setdatabuttonAndswitch(String key, EditText editTextname, EditText editTextstring, EditText editTexttime, CheckBox checkBox) {
        String namebt = editTextname.getText().toString().trim();
        String stringbt = editTextstring.getText().toString().trim();

        String timebtx = editTexttime.getText().toString().trim();


        if (TextUtils.isEmpty(stringbt)) {
            editTextstring.setError("String is required");
        } else {

            if (checkBox.isChecked()) {
                if (timebtx.isEmpty()) {
                    editTexttime.setError("Time is required");
                } else {
                    setSharePrefbutton(key, true, namebt, stringbt, timebtx);
                }
            } else {
                setSharePrefbutton(key, false, namebt, stringbt, timebtx);

            }
        }
    }

    public void setdataSlider(String key, EditText editTextstring, EditText editTextname, EditText editTextmin, EditText editTextmax) {

        String string = editTextstring.getText().toString();
        String name = editTextname.getText().toString();
        String minx = editTextmin.getText().toString().trim();
        String maxx = editTextmax.getText().toString().trim();
        int min = 10, max = 110;

        if (minx.isEmpty()) {
            editTextmin.setError("Minimum number required");
        } else if (maxx.isEmpty()) {
            editTextmax.setError("Maximum number required");

        } else {

            try {
                min = Integer.parseInt(minx);

            } catch (NumberFormatException ex) { // handle your exception

            }

            try {
                max = Integer.parseInt(maxx);
            } catch (NumberFormatException ex) { // handle your exception

            }
            setSharePrefforslider(key, string, name, min, max);

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Log.d(TAG, "onBackPressed: " + MyApp.count);
//        if (!(mySession.isUserPurchased())) {
//            if (MyApp.count % 3 == 0 || MyApp.count == 1
//            ) {
//
//
//                if (mInterstitialAd.isLoaded()) {
//                    mInterstitialAd.show();
//
//                    Log.d(TAG, "onBackPressed: load");
//
//
//                } else {
//                    Log.d(TAG, "onBackPressed: noooooooo ");
//                }
//
//            }
//            MyApp.count++;
//
//        }
        //   Log.d(TAG, "onBackPressed: click");
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