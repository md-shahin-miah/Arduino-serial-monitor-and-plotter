package com.ardunioSerial.crux.ui.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.ardunioSerial.crux.utils.MyApp;
import com.ardunioSerial.crux.utils.MySession;
import com.ardunioSerial.crux.R;

public class RobotSettingsActivity extends AppCompatActivity {
    private AdView mAdView;
    Button buttonSave;


    EditText editTextrelease;
    CheckBox checkBoxrelease;


    EditText editTextdataUp, editTexttimeUp, editTextdataDown, editTexttimeDown,
            editTextdataRight, editTexttimeRight, editTextdataLeft, editTexttimeLeft;

    EditText editTextnamebt1, editTextStringbt1, editTexttimebt1, editTextnamebt2, editTextStringbt2, editTexttimebt2,
            editTextnamebt3, editTextStringbt3, editTexttimebt3;

    EditText editTextnames1, editTextstrings1, editTexttimes1, editTextnames2, editTextstrings2, editTexttimes2, editTextsliderMin, editTextsliderMax, editTextsliderString;

    //checkbox for up/down/left/right
    CheckBox checkBoxup1, checkBoxdown2, checkBoxleft3, checkBoxright4, checkBoxbt1, checkBoxbt2, checkBoxbt3;

   // private InterstitialAd mInterstitialAd;
    MySession mySession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robot_settings);

        buttonSave = findViewById(R.id.save_RC_button_ID);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle("Set data for Robot");

        mAdView = findViewById(R.id.adView_robot_setting);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mySession = new MySession(this);
        if (mySession.isUserPurchased()) {
            mAdView.setVisibility(View.GONE);
        }

        editTextnames1 = findViewById(R.id.name_et_RCT1);
        editTextstrings1 = findViewById(R.id.string_et_RCT1);
        // editTexttimes1 = findViewById(R.id.time_et_RCT1);

        editTextnames2 = findViewById(R.id.name_et_RCT2);
        editTextstrings2 = findViewById(R.id.string_et_RCT2);
        // editTexttimes2 = findViewById(R.id.time_et_RCT2);

        editTextsliderMin = findViewById(R.id.min_number_et_slider);
        editTextsliderMax = findViewById(R.id.max_number_et_slider);
        editTextsliderString = findViewById(R.id.string_number_et_slider);

        editTextdataUp = findViewById(R.id.data_et_G1);
        editTexttimeUp = findViewById(R.id.continuous_time_et_G1);
        checkBoxup1 = findViewById(R.id.continuous_check_G1);

        editTextdataDown = findViewById(R.id.data_et_G2);
        editTexttimeDown = findViewById(R.id.continuous_time_et_G2);
        checkBoxdown2 = findViewById(R.id.continuous_check_G2);

        editTextdataLeft = findViewById(R.id.data_et_G3);
        editTexttimeLeft = findViewById(R.id.continuous_time_et_G3);
        checkBoxleft3 = findViewById(R.id.continuous_check_G3);

        editTextdataRight = findViewById(R.id.data_et_G4);
        editTexttimeRight = findViewById(R.id.continuous_time_et_G4);
        checkBoxright4 = findViewById(R.id.continuous_check_G4);


        editTextnamebt1 = findViewById(R.id.name_et_Rc1);
        editTextStringbt1 = findViewById(R.id.data_et_Rc1);
        editTexttimebt1 = findViewById(R.id.continuous_time_et_RC1);
        checkBoxbt1 = findViewById(R.id.continuous_check_RC1);

        editTextnamebt2 = findViewById(R.id.name_et_Rc2);
        editTextStringbt2 = findViewById(R.id.data_et_Rc2);
        editTexttimebt2 = findViewById(R.id.continuous_time_et_RC2);
        checkBoxbt2 = findViewById(R.id.continuous_check_RC2);

        editTextnamebt3 = findViewById(R.id.name_et_Rc3);
        editTextStringbt3 = findViewById(R.id.data_et_Rc3);
        editTexttimebt3 = findViewById(R.id.continuous_time_et_RC3);
        checkBoxbt3 = findViewById(R.id.continuous_check_RC3);


        checkBoxrelease=findViewById(R.id.checbox_release_string);
        editTextrelease=findViewById(R.id.edittext_release);



        LoadDatabutton("buttonrc1", editTextnamebt1, editTextStringbt1, editTexttimebt1);
        setCheckBox(checkBoxbt1, "buttonrc1");
        LoadDatabutton("buttonrc2", editTextnamebt2, editTextStringbt2, editTexttimebt2);
        setCheckBox(checkBoxbt2, "buttonrc2");
        LoadDatabutton("buttonrc3", editTextnamebt3, editTextStringbt3, editTexttimebt3);
        setCheckBox(checkBoxbt3, "buttonrc3");


        LoadDataslider("slider", editTextsliderMin, editTextsliderMax, editTextsliderString);

        LoadDataSwitch("sw1", editTextnames1, editTextstrings1);
        LoadDataSwitch("sw2", editTextnames2, editTextstrings2);


        LoadDatabuttonUPDOWN("up", editTextdataUp, editTexttimeUp);
        setCheckBox(checkBoxup1, "up");
        LoadDatabuttonUPDOWN("down", editTextdataDown, editTexttimeDown);
        setCheckBox(checkBoxdown2, "down");
        LoadDatabuttonUPDOWN("left", editTextdataLeft, editTexttimeLeft);
        setCheckBox(checkBoxleft3, "left");
        LoadDatabuttonUPDOWN("right", editTextdataRight, editTexttimeRight);
        setCheckBox(checkBoxright4, "right");

        Loadreleasedata();
        setCheckBox(checkBoxrelease, "release");

//        mInterstitialAd = new InterstitialAd(this);
//        mInterstitialAd.setAdUnitId(getString(R.string.admob_interstitial_one));
        AdRequest adRequest1 = new AdRequest.Builder().build();
//        mInterstitialAd.setAdListener(new AdListener() {
//            @Override
//            public void onAdLoaded() {
//
//                super.onAdLoaded();
//            }
//        });
//        mInterstitialAd.loadAd(adRequest1);


        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mySession.isUserPurchased()) {
                    buttonset("buttonrc1", editTextnamebt1, editTextStringbt1, editTexttimebt1, checkBoxbt1);
                    buttonset("buttonrc2", editTextnamebt2, editTextStringbt2, editTexttimebt2, checkBoxbt2);
                    buttonset("buttonrc3", editTextnamebt3, editTextStringbt3, editTexttimebt3, checkBoxbt3);

                    releasestring("release",checkBoxrelease,editTextrelease);
                    updownbuttonset("up", editTextdataUp, editTexttimeUp, checkBoxup1);
                    updownbuttonset("down", editTextdataDown, editTexttimeDown, checkBoxdown2);
                    updownbuttonset("left", editTextdataLeft, editTexttimeLeft, checkBoxleft3);
                    updownbuttonset("right", editTextdataRight, editTexttimeRight, checkBoxright4);

                    switchset("sw1", editTextnames1, editTextstrings1);
                    switchset("sw2", editTextnames2, editTextstrings2);

                    sliderset(editTextsliderMin, editTextsliderMax, editTextsliderString);
                    Toast.makeText(RobotSettingsActivity.this, "Data Saved success", Toast.LENGTH_SHORT).show();


                } else {
                    buttonset("buttonrc1", editTextnamebt1, editTextStringbt1, editTexttimebt1, checkBoxbt1);
                    buttonset("buttonrc2", editTextnamebt2, editTextStringbt2, editTexttimebt2, checkBoxbt2);
                    buttonset("buttonrc3", editTextnamebt3, editTextStringbt3, editTexttimebt3, checkBoxbt3);

                    releasestring("release",checkBoxrelease,editTextrelease);
                    updownbuttonset("up", editTextdataUp, editTexttimeUp, checkBoxup1);
                    updownbuttonset("down", editTextdataDown, editTexttimeDown, checkBoxdown2);
                    updownbuttonset("left", editTextdataLeft, editTexttimeLeft, checkBoxleft3);
                    updownbuttonset("right", editTextdataRight, editTexttimeRight, checkBoxright4);

                    switchset("sw1", editTextnames1, editTextstrings1);
                    switchset("sw2", editTextnames2, editTextstrings2);

                    sliderset(editTextsliderMin, editTextsliderMax, editTextsliderString);
                    Toast.makeText(RobotSettingsActivity.this, "Data Saved success", Toast.LENGTH_SHORT).show();

                 //   mInterstitialAd.show();

                }
            }
        });


    }

    private void releasestring(String release, CheckBox checkBoxrelease, EditText editTextrelease) {
        String stringbt = editTextrelease.getText().toString();


            if (checkBoxrelease.isChecked()) {
                    setSharePrefbuttonRelease(release, true, stringbt);
                }
             else {
                setSharePrefbuttonRelease(release, false, stringbt);
            }

        //}
    }

    private void setSharePrefbuttonRelease(String release, boolean checkbox, String stringbt) {
        SharedPreferences sharedPreferencesslider = getSharedPreferences(release, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferencesslider.edit();
        editor.putBoolean("checkbox", checkbox);
        editor.putString("string", stringbt);
//        editor.putInt("time", time);
        editor.commit();
        //   Toast.makeText(this, "data saved success", Toast.LENGTH_SHORT).show();
    //    Log.d("datalol", string + " " + name);

    }

    private  void  Loadreleasedata(){
        SharedPreferences sharedPreferences1 = this.getSharedPreferences("release", Context.MODE_PRIVATE);
        if ((sharedPreferences1.contains("string"))) {
            String string = sharedPreferences1.getString("string", "no string saved");
            Log.d("str",string);
            editTextrelease.setText(string);
        }

    }
    private void sliderset(EditText editTextmin, EditText editTextmax, EditText edittextsliderString) {

        String minx = editTextmin.getText().toString().trim();
        String maxx = editTextmax.getText().toString().trim();
        String string = edittextsliderString.getText().toString().trim();


        if (minx.isEmpty()) {
            editTextmin.setError("Minimum number required");
            //    Toast.makeText(RobotSettingsActivity.this, "Min/max not saved", Toast.LENGTH_SHORT).show();
        }
        if (maxx.isEmpty()) {
            editTextmax.setError("Maximum number required");

        } else {

            int min = Integer.parseInt(minx);
            int max = Integer.parseInt(maxx);
            setSharePrefforslider("slider", min, max, string);

        }

    }

    private void setSharePrefforslider(String slider, int min, int max, String string) {
        SharedPreferences sharedPreferencesslider = getSharedPreferences(slider, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferencesslider.edit();
        editor.putInt("min", min);
        editor.putInt("max", max);
        editor.putString("string", string);
        editor.commit();
        //   Toast.makeText(this, "data saved success", Toast.LENGTH_SHORT).show();
    }

    private void LoadDataslider(String btnkey, EditText editTextmin, EditText editTextmax, EditText editTextstring) {
        SharedPreferences sharedPreferences1 = this.getSharedPreferences(btnkey, Context.MODE_PRIVATE);
        if ((sharedPreferences1.contains("min")) && (sharedPreferences1.contains("max"))) {
            int min = sharedPreferences1.getInt("min", 10);
            int max = sharedPreferences1.getInt("max", 110);
            String string = sharedPreferences1.getString("string", "no string saved");


            editTextmin.setText(Integer.toString(min));
            editTextmax.setText(Integer.toString(max));
            editTextstring.setText(string);
        }
    }


    private void switchset(String switchkey, EditText editTextname, EditText editTextstring) {

        String name = editTextname.getText().toString();
        String string = editTextstring.getText().toString();
        //  String timex = editTexttime.getText().toString().trim();

        if (string.isEmpty()) {
            editTextstring.setError("String is required");
        } else {
//            int time = Integer.parseInt(timex);
           setshareprefforswitch(switchkey, string, name);

        }
    }

    private void setshareprefforswitch(String btnkey, String string, String name) {
        SharedPreferences sharedPreferencesslider = getSharedPreferences(btnkey, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferencesslider.edit();
        editor.putString("name", name);
        editor.putString("string", string);
//        editor.putInt("time", time);
        editor.commit();
        //   Toast.makeText(this, "data saved success", Toast.LENGTH_SHORT).show();
        Log.d("datalol", string + " " + name);

    }

    private void LoadDataSwitch(String btnkey, EditText editTextname, EditText editTextdata) {
        SharedPreferences sharedPreferences1 = this.getSharedPreferences(btnkey, Context.MODE_PRIVATE);
    //    if ((sharedPreferences1.contains("string")) || (sharedPreferences1.contains("time"))) {

            String name = sharedPreferences1.getString("name", "Switch");
            String data = sharedPreferences1.getString("string", "1");
//            int time = sharedPreferences1.getInt("time", 1000);

        if (sharedPreferences1.contains("string")||sharedPreferences1.contains("name")){
            editTextname.setText(name);
            editTextdata.setText(data);
        }
        else {
            if (btnkey.equals("sw1")){
                editTextname.setText("switch one");
                editTextdata.setText("1");
            }else {
                editTextname.setText("switch two");
                editTextdata.setText("2");
            }
        }

            //   editTexttime.setText(Integer.toString(time));
        }
 //   }


    private void updownbuttonset(String btn, EditText editTextstring, EditText editTexttime, CheckBox checkBox) {

        String stringbt = editTextstring.getText().toString();
        String timebtx = editTexttime.getText().toString().trim();


        if (TextUtils.isEmpty(stringbt)) {
            editTextstring.setError("String is required");
        } else {

            if (checkBox.isChecked()) {
                if (timebtx.isEmpty()) {

                    editTexttime.setError("Time is required when checked");
                } else {
                    setSharePrefbuttonUPDOWN(btn, true, stringbt, timebtx);
                }
            } else {
                setSharePrefbuttonUPDOWN(btn, false, stringbt, timebtx);
            }

        }
    }

    private void setSharePrefbuttonUPDOWN(String btnKey, Boolean checkbox, String data, String time) {

        SharedPreferences sharedPreferencesCB1 = getSharedPreferences(btnKey, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferencesCB1.edit();
        editor.putBoolean("checkbox", checkbox);
        editor.putString("data", data);
        editor.putString("time", time);
        editor.commit();
        //  Toast.makeText(this, "data saved success", Toast.LENGTH_SHORT).show();
        Log.d("datasave", checkbox + data + time);
    }

    private void LoadDatabuttonUPDOWN(String btnkey, EditText editTextdata, EditText editTexttime) {
        SharedPreferences sharedPreferences1 = this.getSharedPreferences(btnkey, Context.MODE_PRIVATE);

        if ((sharedPreferences1.contains("data"))||sharedPreferences1.contains("time")) {

            String data = sharedPreferences1.getString("data", "no data saved");
            String timex = sharedPreferences1.getString("time", "no time");
            int time = -10;

            editTextdata.setText(data);
            try {
                time = Integer.parseInt(timex);
            } catch (NumberFormatException ex) { // handle your exception

            }

            if (time == -10) {

            } else {
                editTexttime.setText(Integer.toString(time));

            }
        }
        else {
            if (btnkey.equals("up")){
               editTextdata.setText("F");

            }
            if (btnkey.equals("down")){
                editTextdata.setText("B");

            }
            if (btnkey.equals("left")){
                editTextdata.setText("L");

            }
            if (btnkey.equals("right")){
                editTextdata.setText("R");

            }

        }
    }

    private void buttonset(String btn, EditText editTextname, EditText editTextstring, EditText editTexttime, CheckBox checkBox) {
        String namebt = editTextname.getText().toString();
        String stringbt = editTextstring.getText().toString();
        String timebtx = editTexttime.getText().toString().trim();


        if (TextUtils.isEmpty(stringbt)) {
            editTextstring.setError("String is required");
        } else {

            if (checkBox.isChecked()) {
                if (timebtx.isEmpty()) {
                    editTexttime.setError("Time is required when checked");
                } else {

                    setSharePrefbutton(btn, true, namebt, stringbt, timebtx);

                }
            } else {
                setSharePrefbutton(btn, false, namebt, stringbt, timebtx);
            }

        }
    }

    private void setSharePrefbutton(String btnKey, Boolean checkbox, String name, String data, String time) {
        SharedPreferences sharedPreferencesCB1 = getSharedPreferences(btnKey, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferencesCB1.edit();
        editor.putBoolean("checkbox", checkbox);
        editor.putString("name", name);
        editor.putString("data", data);
        editor.putString("time", time);
        editor.commit();
//        Toast.makeText(this, "data saved success", Toast.LENGTH_SHORT).show();
        Log.d("datasave", checkbox + name + data + time);
    }

    private void LoadDatabutton(String btnkey, EditText editTextname, EditText editTextdata, EditText editTexttime) {
        SharedPreferences sharedPreferences1 = this.getSharedPreferences(btnkey, Context.MODE_PRIVATE);

//        if ((sharedPreferences1.contains("name")) || (sharedPreferences1.contains("data"))) {

            String name = sharedPreferences1.getString("name", "Button");
            String data = sharedPreferences1.getString("data", "no data saved");
            String timex = sharedPreferences1.getString("time", "no time");

            int time = -1;
            try {
                time = Integer.parseInt(timex);
            } catch (NumberFormatException ex) { // handle your exception

            }
            editTextname.setText(name);
            editTextdata.setText(data);

            if (time==-1){

            }
            else {
                editTexttime.setText((time + ""));
            }


      //  }

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
            return sharedPreferences.getBoolean("checkbox", false);
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

//        if (!mySession.isUserPurchased()) {
//            if (MyApp.count == 1 || MyApp.count%3 == 0
//            ) {
//
//
//                if (mInterstitialAd.isLoaded()) {
//                    mInterstitialAd.show();
//                    //  Log.d(TAG, "onBackPressed: load");
//
//
//                } else {
//                    //   Log.d(TAG, "onBackPressed: noooooooo ");
//                }
//
//            }
//            MyApp.count++;
//
//        }
        Animatoo.animateSlideRight(RobotSettingsActivity.this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        if (item.getItemId() == android.R.id.home) {

            this.finish();
            Animatoo.animateSlideRight(this);
        }
        return super.onOptionsItemSelected(item);
    }
}