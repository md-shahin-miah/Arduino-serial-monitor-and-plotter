package com.ardunioSerial.crux.ui.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;
import com.github.angads25.toggle.widget.LabeledSwitch;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.ardunioSerial.crux.Adapters.DeviceListAdapter;
import com.ardunioSerial.crux.MainActivity;
import com.ardunioSerial.crux.utils.MyApp;
import com.ardunioSerial.crux.utils.MySession;
import com.ardunioSerial.crux.R;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import ru.katso.livebutton.LiveButton;

import static com.ardunioSerial.crux.MainActivity.bluetoothAdapter;
import static com.ardunioSerial.crux.ui.Activity.DeviceListActivity.btSocket;
import static com.ardunioSerial.crux.ui.Activity.DeviceListActivity.isBtConnected;

public class RobotcontrolActivity extends AppCompatActivity {
    ImageButton downbtn, leftbtn, rigthtbtn, upbtn;
    ImageView settings;
    private AdView mAdView;
    Button button1, button2, button3;

    TextView textViewsw1, textViewsw2, sliderdatashow;

    LabeledSwitch switch1, switch2;
    SeekBar seekBar;
    CircleImageView indicatorgreen, indicatorred;

    BluetoothAdapter myBluetoothAdapter;
    DeviceListAdapter deviceListAdapter;
    MySession mySession;
   // InterstitialAd mInterstitialAd;
    TextView refresh;
    Vibrator vibe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robotcontrol);

        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        View decorView = getWindow().getDecorView();
// Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //   refresh=findViewById(R.id.refresh_RCA);
        settings = findViewById(R.id.settings_View_id);
        indicatorgreen = findViewById(R.id.user_online_status_green);
        indicatorred = findViewById(R.id.user_online_status_red);

        upbtn = findViewById(R.id.button_id_up);
        downbtn = findViewById(R.id.button_id_down);
        leftbtn = findViewById(R.id.button_id_left);
        rigthtbtn = findViewById(R.id.button_id_right);

        sliderdatashow = findViewById(R.id.slidertextShow);

        button1 = findViewById(R.id.button_1_id_RC);
        button2 = findViewById(R.id.button_2_id_RC);
        button3 = findViewById(R.id.button_3_id_RC);

        switch1 = findViewById(R.id.switch_1_id_rc);
        switch2 = findViewById(R.id.switch_2_id_rc);

        textViewsw1 = findViewById(R.id.switchtext1);
        textViewsw2 = findViewById(R.id.switchtext2);

        seekBar = findViewById(R.id.seekbar_1_rc);


        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

//        mInterstitialAd = new InterstitialAd(this);
//        mInterstitialAd.setAdUnitId(getString(R.string.admob_interstitial_one));
        AdRequest adRequestInter = new AdRequest.Builder().build();
    //    mInterstitialAd.loadAd(adRequestInter);

        if (bluetoothAdapter.isEnabled()) {
            if (isBtConnected) {
                indicatorgreen.setVisibility(View.VISIBLE);
                indicatorred.setVisibility(View.INVISIBLE);
            } else {
                indicatorred.setVisibility(View.VISIBLE);
            }
        }

        indicatorgreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btSocket != null || btSocket == null) {

                    try {
                        btSocket.close();
                    } catch (Exception e) {
                    }
                    btSocket = null;
                    isBtConnected = false;
                    indicatorgreen.setVisibility(View.GONE);
                    indicatorred.setVisibility(View.VISIBLE);
                    // Toast.makeText(MainActivity.this, "Disconnected", Toast.LENGTH_SHORT).show();


                }
            }
        });
        indicatorred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RobotcontrolActivity.this, DeviceListActivity.class);
                intent.putExtra("key","red");
                startActivity(intent);
                finish();
            }
        });




        mySession = new MySession(this);


        if (mySession.isUserPurchased()) {
            mAdView.setVisibility(View.INVISIBLE);
        } else {
            mAdView.setVisibility(View.VISIBLE);

        }

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RobotcontrolActivity.this, RobotSettingsActivity.class));
                Animatoo.animateSlideRight(RobotcontrolActivity.this);
            }
        });


        getdata();


    }

    @Override
    protected void onResume() {
        getdata();
        if (isBtConnected) {
            indicatorgreen.setVisibility(View.VISIBLE);
            indicatorred.setVisibility(View.INVISIBLE);
        } else {
            indicatorred.setVisibility(View.VISIBLE);
        }
        super.onResume();
    }

    private void getdata() {
        buttonfromshareprefUpDown(upbtn, "up");
        buttonfromshareprefUpDown(downbtn, "down");
        buttonfromshareprefUpDown(leftbtn, "left");
        buttonfromshareprefUpDown(rigthtbtn, "right");

        buttonfromsharepref(button1, "buttonrc1");
        buttonfromsharepref(button2, "buttonrc2");
        buttonfromsharepref(button3, "buttonrc3");

        switchimplementaion(switch1, "sw1", "switchone", textViewsw1);
        switchimplementaion(switch2, "sw2", "switchtwo", textViewsw2);
        getsliderdata(seekBar, "slider");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }


    private final BroadcastReceiver mPairReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                final int state = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.ERROR);
                final int prevState = intent.getIntExtra(BluetoothDevice.EXTRA_PREVIOUS_BOND_STATE, BluetoothDevice.ERROR);
//                if (state == BluetoothDevice.BOND_BONDED && prevState == BluetoothDevice.BOND_BONDING) {
//                    showToast("Paired");
//                } else if (state == BluetoothDevice.BOND_NONE && prevState == BluetoothDevice.BOND_BONDED) {
//                    showToast("Unpaired");
//                }

                deviceListAdapter.notifyDataSetChanged();

            } else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {
                //button.setText("Disconnected");

//                SharedPreferences sharedPreferences = getSharedPreferences("connected", MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putBoolean("connection", false);
//                editor.commit();
//                Toast.makeText(context, "Disconnected", Toast.LENGTH_SHORT).show();


//                indicatorgreen.setVisibility(View.GONE);
//                indicatorred.setVisibility(View.VISIBLE);

            } else if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {
                //button.setText("Connected");
            }
        }
    };


    private void switchimplementaion(LabeledSwitch switchCompat, String btnkey, String swname, TextView textView) {
        SharedPreferences sharedPreferences11 = RobotcontrolActivity.this.getSharedPreferences(btnkey, Context.MODE_PRIVATE);


        String name = sharedPreferences11.getString("name", "1");
        String data = sharedPreferences11.getString("string", "Ss");
        // int time = sharedPreferences11.getInt("time", 1000);

        if (sharedPreferences11.contains("name")) {
            textView.setText(name);

        }

//        if (sharedPreferences11.contains("string")) {

        switchCompat.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if (isOn) {
                    if (sharedPreferences11.contains("string")) {
                        senddata(data);
                    } else {
                        if (swname.equals("switchone")) {
                            senddata("1");

                        } else {
                            senddata("2");


                        }
                    }
                } else {
                    Toast.makeText(RobotcontrolActivity.this, "is truns off", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        }
//        else {
//            if(swname.equals("switchone")){
//                        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//
//                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                                if (isChecked) {
//                                    senddata("s1");
//
//                                } else {
//                                    Toast.makeText(RobotcontrolActivity.this, name + "is truns off", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//
//                        });
//                    }
//            if(swname.equals("switchtwo")){
//                switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//
//                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                        if (isChecked) {
//                            senddata("s2");
//
//                        } else {
//                            Toast.makeText(RobotcontrolActivity.this, name + "is truns off", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                });
//            }
//        }

    }

    private void buttonfromsharepref(Button button, String checkboxKey) {

        SharedPreferences sharedPreferences11 = RobotcontrolActivity.this.getSharedPreferences(checkboxKey, Context.MODE_PRIVATE);


        //   if (sharedPreferences11.contains("checkbox") || sharedPreferences11.contains("data")) {


        boolean checkboxvaluebt1 = sharedPreferences11.getBoolean("checkbox", false);
        String name = sharedPreferences11.getString("name", "Button");
        String datax = sharedPreferences11.getString("data", "no data saved");
        String timex = sharedPreferences11.getString("time", "no data");

        int time = 0;
        try {
            time = Integer.parseInt(timex);
        } catch (NumberFormatException ex) { // handle your exception

        }

        Log.d("data11", name + datax + time + checkboxvaluebt1);
//            if (sharedPreferences11.contains("name")) {
        button.setText(name);
//            }


//            if (checkboxvaluebt1) {
        int Time = time;
        button.setOnTouchListener(new View.OnTouchListener() {

            private Handler mHandler = new Handler();


            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:


                  //     button.setBackgroundResource(R.drawable.rounded_button1);
                        if (checkboxvaluebt1) {
                            mAction.run();
                        } else {
                            //   String data1 = datax + "\n";
                            senddata(datax);
                        }
                        //if (mHandler != null) return true;
//                            mHandler = new Handler();
//                            mHandler.postDelayed(mAction, time);

                        break;
                    case MotionEvent.ACTION_UP:
                     //   button.setBackgroundResource(R.drawable.rounded_buttonrc);

//                            if (mHandler == null) return true;
                        mHandler.removeCallbacks(mAction);
//                            mHandler = null;
                        break;
                }
                return false;
            }

            Runnable mAction = new Runnable() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void run() {
                    //  String data = datax + "\n";
                    senddata(datax);
                    mHandler.postDelayed(this, Time);


                }
            };

        });


        //  }
//          else  {
//
//                button.setOnTouchListener(new View.OnTouchListener() {
//                    @RequiresApi(api = Build.VERSION_CODES.M)
//                    @Override
//                    public boolean onTouch(View v, MotionEvent event) {
//
//                        switch (event.getAction()) {
//                            case MotionEvent.ACTION_DOWN:
//                                button.setBackgroundColor(Color.RED);
//
//                                String data=datax+"\n";
//                                senddata(data);
//
//                                break;
//                            case MotionEvent.ACTION_UP:
//                                // button.setBackgroundColor(R.color.colorEditText);
////                                button.setBackgroundColor(Color.RED);
//                                button.setBackgroundColor(getColor(R.color.colorrobotbuttons));
////                                senddata("S");
////                                return true;
//                                break;
//                        }
//
//                        return false;
//                    }
//                });
//            }
        //   }
    }

    private void getsliderdata(SeekBar seekBar, String name) {

        SharedPreferences sharedPreferencess = RobotcontrolActivity.this.getSharedPreferences(name, Context.MODE_PRIVATE);

        if ((sharedPreferencess.contains("min")) && (sharedPreferencess.contains("max"))) {
            int minslider1 = sharedPreferencess.getInt("min", 10);
            int maxslider1 = sharedPreferencess.getInt("max", 110);
            String sliderString = sharedPreferencess.getString("string", "null");

            Log.d("seek1", String.valueOf(minslider1 + "" + "" + maxslider1));

            int step = 10;
            int max = maxslider1;
            int min = minslider1;

// Ex :
// If you want values from 3 to 5 with a step of 0.1 (3, 3.1, 3.2, ..., 5)
// this means that you have 21 possible values in the seekbar.
// So the range of the seek bar will be [0 ; (5-3)/0.1 = 20].
            seekBar.setMax((max - min) / step);

            seekBar.setOnSeekBarChangeListener(
                    new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress,
                                                      boolean fromUser) {
                            // Ex :
                            // And finally when you want to retrieve the value in the range you
                            // wanted in the first place -> [3-5]
                            //
                            // if progress = 13 -> value = 3 + (13 * 0.1) = 4.3

                            int value = min + (progress * step);
                            String val = sliderString + (value);

                            Log.d("datasl", val);
                            senddata(val);

                            sliderdatashow.setText("" + value + "");

                            // Log.d("seek", String.valueOf(value));
                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {
                        }


                    }
            );


        }
//        else {
//            Toast.makeText(this, "set Value first", Toast.LENGTH_SHORT).show();
//        }


    }

    private void buttonfromshareprefUpDown(ImageButton button, String checkboxKey) {

        SharedPreferences sharedPreferences11 = RobotcontrolActivity.this.getSharedPreferences(checkboxKey, MODE_PRIVATE);
        SharedPreferences sharedPreferences1 = this.getSharedPreferences("release", Context.MODE_PRIVATE);
        String stringrelease = sharedPreferences1.getString("string", "no string saved");
        boolean ischeckrelease = sharedPreferences1.getBoolean("checkbox", false);
        Log.d("strrr", stringrelease);
        //   if (sharedPreferences11.contains("data")){

        if (sharedPreferences11.contains("data")) {
            boolean checkboxvaluebt1 = sharedPreferences11.getBoolean("checkbox", false);
            String data = sharedPreferences11.getString("data", "no data saved");
            String timex = sharedPreferences11.getString("time", "no time");
            Log.d("datalo1", data + timex + checkboxvaluebt1);


            int time = 1000;
            try {
                time = Integer.parseInt(timex);
            } catch (NumberFormatException ex) { // handle your exception

            }


            if (checkboxvaluebt1) {

                int finalTime = time;
                button.setOnTouchListener(new View.OnTouchListener() {

                    private Handler mHandler = new Handler();

                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                button.setBackgroundColor(Color.RED);
                                vibe.vibrate(100);


                                mAction.run();
                                break;
                            case MotionEvent.ACTION_UP:
                                button.setBackgroundColor(getColor(R.color.colorEditText));
                                if (ischeckrelease) {
                                    senddata(stringrelease);
                                }


                                mHandler.removeCallbacks(mAction);
                                break;
                        }
                        return false;
                    }

                    Runnable mAction = new Runnable() {
                        @SuppressLint("ResourceAsColor")
                        @Override
                        public void run() {
                            senddata(data);
                            mHandler.postDelayed(this, finalTime);


                        }
                    };

                });


            } else {

                button.setOnTouchListener(new View.OnTouchListener() {

                    private Handler mHandler = new Handler();

                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @SuppressLint({"ResourceAsColor", "ClickableViewAccessibility"})
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                button.setBackgroundColor(Color.RED);
                                vibe.vibrate(100);

                                senddata(data);
                                return true;
//                                break;
                            case MotionEvent.ACTION_UP:
                                // button.setBackgroundColor(R.color.colorEditText);
                                button.setBackgroundColor(getColor(R.color.colorEditText));
                                if (ischeckrelease) {
                                    senddata(stringrelease);
                                }

                                return true;
//                                break;
                        }
                        return false;
                    }


                });


            }

        } else {
            button.setOnTouchListener(new View.OnTouchListener() {

                private Handler mHandler = new Handler();

                @RequiresApi(api = Build.VERSION_CODES.M)
                @SuppressLint({"ResourceAsColor", "ClickableViewAccessibility"})
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            button.setBackgroundColor(Color.RED);
                            vibe.vibrate(100);

                            if (checkboxKey.equals("up")) {
                                senddata("U" + "\n");
                            }
                            if (checkboxKey.equals("down")) {
                                senddata("D" + "\n");
                            }
                            if (checkboxKey.equals("left")) {
                                senddata("L" + "\n");
                            }
                            if (checkboxKey.equals("right")) {
                                senddata("R" + "\n");
                            }
                            return true;
                        case MotionEvent.ACTION_UP:
                            // button.setBackgroundColor(R.color.colorEditText);
                            button.setBackgroundColor(getColor(R.color.colorEditText));
                            if (ischeckrelease) {
                                senddata(stringrelease);
                            }

                            return true;
                    }
                    return false;
                }


            });
        }

    }


    private void senddata(String value) {

        String val = value + "\n";

        if (btSocket != null) {
            try {


                btSocket.getOutputStream().write(val.getBytes());
//                Toast.makeText(getContext(), "Data sent successfully", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(RobotcontrolActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(RobotcontrolActivity.this, MainActivity.class));
        finish();
//        if (mySession.isUserPurchased()) {
//
//
//        } else {
//            if (MyApp.count == 1 || MyApp.count == 3 || MyApp.count == 6 || MyApp.count == 9 || MyApp.count == 12 || MyApp.count == 15 ||
//                    MyApp.count == 18 || MyApp.count == 21 || MyApp.count == 24 || MyApp.count == 27 || MyApp.count == 30 || MyApp.count == 33
//                    || MyApp.count == 36 || MyApp.count == 40
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
        Animatoo.animateSlideRight(RobotcontrolActivity.this);
    }
}