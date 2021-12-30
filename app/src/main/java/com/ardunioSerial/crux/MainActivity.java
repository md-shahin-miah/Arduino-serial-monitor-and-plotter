package com.ardunioSerial.crux;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ShareCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.ardunioSerial.crux.utils.Appconfig;
//import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialog;
//import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialogListener;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.ardunioSerial.crux.Adapters.DeviceListAdapter;
import com.ardunioSerial.crux.Adapters.PagerAdapter;

import com.ardunioSerial.crux.model.Dataeventclass;
import com.ardunioSerial.crux.ui.Activity.DeviceListActivity;
import com.ardunioSerial.crux.ui.Activity.RobotcontrolActivity;
import com.ardunioSerial.crux.utils.Helper1;
import com.ardunioSerial.crux.utils.MySession;
import com.onesignal.OneSignal;

import org.greenrobot.eventbus.EventBus;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.ardunioSerial.crux.R.*;
import static com.ardunioSerial.crux.StaticValue.JLCPCB_AD_LINK;
import static com.ardunioSerial.crux.StaticValue.NEW_JLCPCB_MAY21;
import static com.ardunioSerial.crux.StaticValue.PCBWAY_AD_LINK;
import static com.ardunioSerial.crux.ui.Activity.DeviceListActivity.btSocket;
import static com.ardunioSerial.crux.ui.Activity.DeviceListActivity.isBtConnected;
import static com.ardunioSerial.crux.utils.Appconfig.PRODUCT_KEY;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Dialog dialogll;
    Dialog dialog;
    Dialog dialogPCBAppOpen;

    ApiService apiService;

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    ViewPager pager;
    TabLayout mTabLayout;
    TabItem firstItem, secondItem, thirdItem;
    PagerAdapter adapter;
    public static ArrayList<String> rowsArrayList = new ArrayList<>();

    String sCurrentversion, sLatestversion;

    public static String productid = "serial_arduino_terminal";

    TextView websitetextheader, versionheader;

    MySession mySession;
    private ProgressDialog progress;

    //    private boolean isBtConnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final String TAG = "MainActivity";


    OutputStream mmOutputStream;
    BluetoothDevice mmDevice;

    ArrayList<String> list = new ArrayList<String>();

    Helper1 helper = new Helper1();
    public static Thread workerThread;
    public static byte[] readBuffer;
    public static int readBufferPosition;
    public static volatile boolean stopWorker = false;
    public static InputStream mmInputStream;
    public static BluetoothAdapter bluetoothAdapter;
    private int REQUEST_CODE_ENABLE_BLUETOOTH = 1;
    String name;

    //   private InterstitialAd mInterstitialAd;

    DeviceListAdapter deviceListAdapter;

    ConnectBT connectBT;

    TextView connected, connectionstart;


    public static BillingProcessor bp;
    public static BillingProcessor bp_unlock;


    NiftyDialogBuilder materialDesignAnimatedDialog;
    private int timetoshowdialog=0;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);

        materialDesignAnimatedDialog = NiftyDialogBuilder.getInstance(this);
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        // admob test device
        List<String> testDeviceIds = Arrays.asList(getString(string.admob_test_device_samsung_a20));
        RequestConfiguration configuration =
                new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build();
        MobileAds.setRequestConfiguration(configuration);


        mySession = new MySession(this);
        Toolbar toolbar = findViewById(id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View actionBarView = layoutInflater.inflate(layout.custom_toolbar, null);
        actionBar.setCustomView(actionBarView);


        connected = findViewById(id.textView_connected_id);
        connectionstart = findViewById(id.textVied_search_id);


        pager = findViewById(id.viewpager);
        mTabLayout = findViewById(id.tablayout);

        firstItem = findViewById(id.firstItem);
        secondItem = findViewById(id.secondItem);
        thirdItem = findViewById(id.thirditem);

        drawerLayout = findViewById(id.drawer);
        navigationView = findViewById(id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, string.open, string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.getDrawerArrowDrawable().setColor(Color.WHITE);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();


        MySession mySession = new MySession(MainActivity.this);


        //version update alert dialog
        new GetLatetestversion().execute();


        View header = navigationView.getHeaderView(0);
        websitetextheader = header.findViewById(R.id.website_id_textview);
        versionheader = header.findViewById(id.version_text);
        try {
            String versionName = this.getPackageManager()
                    .getPackageInfo(this.getPackageName(), 0).versionName;
            versionheader.setText("Version " + versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        websitetextheader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://cruxbd.com/"));
                startActivity(intent);
            }
        });


        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


        if (isBtConnected) {
            connected.setVisibility(View.VISIBLE);
        } else {
            connectionstart.setVisibility(View.VISIBLE);
        }


        connectionstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bluetoothAdapter.isEnabled()) {


                    if (!(isBtConnected) || isBtConnected) {

                        if (btSocket == null || btSocket != null) {

                            SharedPreferences sharedPreferences = getSharedPreferences("devicename", Context.MODE_PRIVATE);

                            if (sharedPreferences.contains("name")) {
                                name = sharedPreferences.getString("name", "null");
                                Log.d("devname", name);
                                Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
                                if (pairedDevices.size() > 0) {

                                    connectBT = new ConnectBT();
                                    connectBT.execute();

                                } else {
                                    Toast.makeText(MainActivity.this, "You don't have paired device", Toast.LENGTH_SHORT).show();

                                    startActivity(new Intent(MainActivity.this, DeviceListActivity.class));
                                }

                            } else {
                                Toast.makeText(MainActivity.this, "You don't have paired device", Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(MainActivity.this, DeviceListActivity.class));
                            }

                        }


                    } else {
                        connectionstart.setVisibility(View.GONE);

                    }


                } else {
                    Toast.makeText(MainActivity.this, "Turn On your Bluetooth first and try again", Toast.LENGTH_SHORT).show();

                    Enablebluetooth();

                }


            }
        });

        connected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btSocket != null || btSocket == null) {

                    try {
                        btSocket.close();
                    } catch (Exception e) {
                    }
                    btSocket = null;
                    isBtConnected = false;
                    // Toast.makeText(MainActivity.this, "Disconnected", Toast.LENGTH_SHORT).show();
                    connected.setVisibility(View.GONE);
                    connectionstart.setVisibility(View.VISIBLE);

                }

            }
        });


        Enablebluetooth();
        if (!(btSocket == null)) {

            beginListenForData1();
        }


        adapter = new PagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, mTabLayout.getTabCount());
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(6);

        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        bp_unlock = new BillingProcessor(this, null, new BillingProcessor.IBillingHandler() {
            @Override
            public void onProductPurchased(String productId, TransactionDetails details) {
                mySession.setUserPurchase(true);
                Toast.makeText(MainActivity.this, "You have already purchased", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPurchaseHistoryRestored() {

            }

            @Override
            public void onBillingError(int errorCode, Throwable error) {

            }

            @Override
            public void onBillingInitialized() {

            }
        });
        bp_unlock.initialize();

        bp = new BillingProcessor(MainActivity.this, Appconfig.LICENCE_KEY, new BillingProcessor.IBillingHandler() {
            @Override
            public void onProductPurchased(String productId, TransactionDetails details) {
                mySession.setUserPurchase(true);
//                new TTFancyGifDialog.Builder(MainActivity.this)
//                        .setTitle("Dear User")
//                        .setMessage("You have removed all ads and unlocked all premium features. Thanks for your support.")
//                        .setPositiveBtnText("OK")
//                        .setPositiveBtnBackground("#22b573")
//                        .setNegativeBtnText("Cancel")
//                        .setNegativeBtnBackground("#c1272d")
//                        .setGifResource(drawable.maker1)
//                        //   .setGifResource(Color.BLUE)
//
//                        //pass your gif, png or jpg
//                        .isCancellable(true)
//                        .OnPositiveClicked(new TTFancyGifDialogListener() {
//                            @Override
//                            public void OnClick() {
//                            }
//                        })
//
//                        .build();


                Dialog dialog=new Dialog(MainActivity.this);
                dialog.setContentView(layout.dialog_exit_congrats);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                Button OkButton=dialog.findViewById(id.buttonOk);
                OkButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      dialog.dismiss();
                    }
                });

                dialog.show();
            }

            @Override
            public void onPurchaseHistoryRestored() {

            }

            @Override
            public void onBillingError(int errorCode, Throwable error) {

            }

            @Override
            public void onBillingInitialized() {

            }
        });
        bp.initialize();


        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        registerReceiver(mPairReceiver, new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED));
        registerReceiver(mPairReceiver, new IntentFilter(BluetoothDevice.ACTION_ACL_CONNECTED));
        registerReceiver(mPairReceiver, new IntentFilter(BluetoothDevice.ACTION_ACL_DISCONNECTED));



        timetoshowdialog++;

      if (!mySession.isUserPurchased()){
          if (isInternetConnected()) {

              if (timetoshowdialog == 1) {
                  DialogAdJLCPCB(NEW_JLCPCB_MAY21);
              }
          }
      }


        // logging request
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://cruxbd,com")
                .build();
        apiService = retrofit.create(ApiService.class);

//
//        pcbWayAdClick();
//        pcbWayAdClick();


    }


    private final BroadcastReceiver mPairReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {

                SharedPreferences sharedPreferences = getSharedPreferences("connected", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("connection", false);
                editor.commit();
                showToast("Disconnected");

//                if (!(mySession.isUserPurchased())) {
//                    mInterstitialAd = new InterstitialAd(MainActivity.this);
//                    mInterstitialAd.setAdUnitId(getString(string.admob_interstitial_one));
//                    AdRequest adRequestInter = new AdRequest.Builder().build();
//                    mInterstitialAd.setAdListener(new AdListener() {
//                        @Override
//                        public void onAdLoaded() {
//
//                            mInterstitialAd.show();
//                        }
//                    });
//                    mInterstitialAd.loadAd(adRequestInter);
//                }

                connectionstart.setVisibility(View.VISIBLE);
                connected.setVisibility(View.INVISIBLE);
            } else if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {
                //button.setText("Connected");
            }
        }
    };


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        if (item.getItemId() == id.nav_device) {
//            if (!(mySession.isUserPurchased())) {
//                mInterstitialAd = new InterstitialAd(MainActivity.this);
//                mInterstitialAd.setAdUnitId(getString(string.admob_interstitial_one));
//                AdRequest adRequestInter = new AdRequest.Builder().build();
//                mInterstitialAd.setAdListener(new AdListener() {
//                    @Override
//                    public void onAdLoaded() {
//
//                        mInterstitialAd.show();
//                    }
//                });
//                mInterstitialAd.loadAd(adRequestInter);
//                startActivity(new Intent(this, DeviceListActivity.class));
//                Animatoo.animateSlideLeft(MainActivity.this);
//            } else {
            startActivity(new Intent(this, DeviceListActivity.class));
            Animatoo.animateSlideLeft(MainActivity.this);
            //  }
        }
        if (item.getItemId() == id.nav_robotcontrol) {
//            if (!(mySession.isUserPurchased())) {
//                mInterstitialAd = new InterstitialAd(MainActivity.this);
//                mInterstitialAd.setAdUnitId(getString(string.admob_interstitial_one));
//                AdRequest adRequestInter = new AdRequest.Builder().build();
//                mInterstitialAd.setAdListener(new AdListener() {
//                    @Override
//                    public void onAdLoaded() {
//
//                        mInterstitialAd.show();
//                    }
//                });
//                mInterstitialAd.loadAd(adRequestInter);
//                startActivity(new Intent(this, RobotcontrolActivity.class));
//                Animatoo.animateSlideLeft(MainActivity.this);
//            } else {

            startActivity(new Intent(this, RobotcontrolActivity.class));
            Animatoo.animateSlideLeft(MainActivity.this);

        }
        if (item.getItemId() == id.nav_appbuy) {

            if (mySession.isUserPurchased()) {
                Toast.makeText(this, "You are using Pro version", Toast.LENGTH_SHORT).show();

            } else {

                Dialog dialog=new Dialog(this);
                dialog.setContentView(R.layout.dialog_app_purchase);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                Button YesButton=dialog.findViewById(R.id.buttonExit);
                Button NoButton=dialog.findViewById(R.id.buttonCancel);
                YesButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity.bp_unlock.purchase(MainActivity.this, PRODUCT_KEY);
                        dialog.dismiss();
                    }
                });
                NoButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }

        }

        if (item.getItemId() == id.nav_privacypolicy) {

            try {
                if (!URLUtil.isValidUrl("https://arduino-serial-monitor.web.app/privacy_policy.html")) {
                    Toast.makeText(this, " This is not a valid link", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://arduino-serial-monitor.web.app/privacy_policy.html"));
                    this.startActivity(intent);
                }
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, " You don't have any browser to open web page", Toast.LENGTH_LONG).show();
            }
            Animatoo.animateSlideLeft(MainActivity.this);
        }

        if (item.getItemId() == id.nav_Recomended_apps) {
            try {
                if (!URLUtil.isValidUrl("https://play.google.com/store/apps/developer?id=CRUX")) {
                    Toast.makeText(this, " This is not a valid link", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://play.google.com/store/apps/developer?id=CRUX"));
                    this.startActivity(intent);
                }
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, " You don't have any browser to open web page", Toast.LENGTH_LONG).show();
            }

        }
        if (item.getItemId() == id.nav_share) {
            ShareCompat.IntentBuilder.from(this)
                    .setType("text/plain")
                    .setChooserTitle("Chooser title")
                    .setText("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())
                    .startChooser();
            Animatoo.animateSlideLeft(MainActivity.this);

        }
        if (item.getItemId() == id.nav_facebook) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse("https://m.facebook.com/Official.CRUX/"));
            startActivity(intent);
            Animatoo.animateSlideLeft(MainActivity.this);
        }

        if (item.getItemId() == id.nav_contacts_us) {
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", "app.cruxbd@gmail.com", null));
            intent.putExtra(Intent.EXTRA_SUBJECT, "subject");
            intent.putExtra(Intent.EXTRA_TEXT, "message");
            startActivity(Intent.createChooser(intent, "Choose an Email client :"));
            Animatoo.animateSlideLeft(MainActivity.this);
        }


        return false;
    }

    public void Enablebluetooth() {

        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Your device doesn't support bluetooth communication", Toast.LENGTH_SHORT).show();
        } else {
            //Ask to the user turn the bluetooth on
            Intent turnBTon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnBTon, REQUEST_CODE_ENABLE_BLUETOOTH);

        }

    }

    public static void beginListenForData1() {
        final Handler handler = new Handler();
        final byte delimiter = 10; //This is the ASCII code for a newline character

        // stopWorker = false;
        readBufferPosition = 0;
        readBuffer = new byte[1024];
        try {
            mmInputStream = btSocket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }


        workerThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted() && !stopWorker) {
                try {
                    int bytesAvailable = mmInputStream.available();
                    if (bytesAvailable > 0) {
                        byte[] packetBytes = new byte[bytesAvailable];
                        mmInputStream.read(packetBytes);
                        for (int i = 0; i < bytesAvailable; i++) {
                            byte b = packetBytes[i];
                            if (b == delimiter) {
                                byte[] encodedBytes = new byte[readBufferPosition];
                                System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                                final String data = new String(encodedBytes, StandardCharsets.US_ASCII);
                                readBufferPosition = 0;
                                handler.post(() -> {
                                    Log.d("sala", data);


                                    EventBus.getDefault().post(new Dataeventclass(data));


                                });
                            } else {
                                readBuffer[readBufferPosition++] = b;
                            }
                        }
                    }
                } catch (IOException ex) {
                    Log.d(TAG, "beginListenForData worker thread exception: " + ex.getMessage());
                    stopWorker = true;
                }
            }
        });
        workerThread.start();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute() {
            //show a progress dialog
            progress = ProgressDialog.show(MainActivity.this, "Connecting...", "Please wait!!!");
        }

        //while the progress dialog is shown, the connection is done in background
        @Override
        protected Void doInBackground(Void... devices) {
            try {
                if (btSocket == null || !isBtConnected) {
                    //get the mobile bluetooth device
                    bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

                    //connects to the device's address and checks if it's available
                    BluetoothDevice dispositivo = bluetoothAdapter.getRemoteDevice(name);
                    //create a RFCOMM (SPP) connection
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);
                    //btSocket = dispositivo.createRfcommSocketToServiceRecord(myUUID);
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();//start connection
                }
            } catch (IOException e) {
                //if the try failed, you can check the exception here
                ConnectSuccess = false;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            if (!ConnectSuccess) {

                isBtConnected = false;
                showToast("Connection Failed. Is it a SPP Bluetooth? Try again.");
                connectionstart.setVisibility(View.VISIBLE);
                connected.setVisibility(View.INVISIBLE);
            } else {
                showToast("Connected");
                isBtConnected = true;
                beginListenForData1();
                connectionstart.setVisibility(View.INVISIBLE);
                connected.setVisibility(View.VISIBLE);
                SharedPreferences sharedPreferences = getSharedPreferences("connected", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("connection", true);
                editor.commit();
            }

            progress.dismiss();
        }
    }


    // universal method for all toasts

    public void showToast(String toastMesg) {
        Toast.makeText(getBaseContext(), toastMesg, Toast.LENGTH_SHORT).show();
    }
//
//    private void beginListenForData() {
//        int stopTime = 0;
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            public void run() {
//                while (!stopWorker){
//                    Toast.makeText(MainActivity.this, "ok", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }, stopTime);
//    }


//    @Override
//    protected void onStart() {
//        super.onStart();
//
//
//    }

//    @Override
//    public void onDestroy() {
//        unregisterReceiver(mPairReceiver);
//
//        timetoshowdialog=0;
//
//        // cancel async task to get rid of memory leak
//        if (connectBT != null) {
//            connectBT.cancel(true);
//        }
//        if (bp != null) {
//            bp.release();
//        }
//
//        if (bp_unlock != null) {
//            bp_unlock.release();
//        }
//        super.onDestroy();
//    }


//    @Override
//    public void onBackPressed() {
//        finishAffinity();
//    }

    private class GetLatetestversion extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {

                sLatestversion = Jsoup.connect("https://play.google.com/store/apps/details?id=" + getPackageName())
                        .timeout(3000)
                        .get()
                        .select("div.hAyfc:nth-child(4)>" +
                                "span:nth-child(2) > div:nth-child(1)" +
                                ">span:nth-child(1)")
                        .first()
                        .ownText();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return sLatestversion;
        }

        @Override
        protected void onPostExecute(String s) {
            sCurrentversion = BuildConfig.VERSION_NAME;
            if (sLatestversion != null) {
                float cVersion = Float.parseFloat(sCurrentversion);
                float lVersion = Float.parseFloat(sLatestversion);
                if (lVersion > cVersion) {
                    UpdateAlertDialog();
                }
            }
        }
    }

    private void UpdateAlertDialog() {


        materialDesignAnimatedDialog.withTitle("Update Available")
                .withMessage("Do you want to update? ")
                .withDialogColor("#1c90ec").withButton1Text("Cancel").withButton2Text("Ok")
                .withDuration(700).withEffect(Effectstype.Shake)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        materialDesignAnimatedDialog.cancel();
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                        materialDesignAnimatedDialog.cancel();
                    }
                }).show();

    }




    private void pcbWayAdClick() {
        Call<ResponseBody> pcbWayAdClickResponse = apiService.getPcbWayAdClick();
        pcbWayAdClickResponse.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, "onResponse: pcbWay icd ad click done");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure: pcbWay icd ad click failed");
            }
        });
    }






    @Override
    public void onBackPressed() {
        if (!mySession.isUserPurchased()){
            DialogOnExitPcbWay(PCBWAY_AD_LINK);
        }
        else{
            finishAffinity();
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void DialogOnExitPcbWay(String link) {
        if (isInternetConnected()) {
            dialogll = new Dialog(this);
            dialogll.setContentView(layout.dialog_exit_ad);
            dialogll.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            TextView skipbutton = dialogll.findViewById(R.id.skipid);
            TextView exitbuttonText = dialogll.findViewById(R.id.exit_button);
            WebView webView = dialogll.findViewById(R.id.web_view_Ad);
            webView.getSettings().setJavaScriptEnabled(true);


            webView.loadUrl(link);

            webView.setWebViewClient(new WebViewClient() {


                public void onPageStarted(
                        WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    skipbutton.setVisibility(View.VISIBLE);
                    exitbuttonText.setVisibility(View.VISIBLE);

                }

            });


            webView.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_MOVE) {
                        return false;
                    }

                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        webintent(link);
                    }

                    return false;
                }
            });

            skipbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogll.dismiss();
                    finishAffinity();
//                    MainActivity.super.onBackPressed();
                }
            });

            exitbuttonText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogll.dismiss();
                    finishAffinity();
//                    MainActivity.super.onBackPressed();

                }
            });
            dialogll.show();
        } else {

//            MainActivity.super.onBackPressed();

            finishAffinity();
        }
    }

//    private void DialogAdFirst(String link) {
//
//        dialog = new Dialog(this);
//        dialog.setContentView(R.layout.dialog_open);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//        TextView skipbutton = dialog.findViewById(R.id.skipid_open);
//        TextView skipbuttonText = dialog.findViewById(R.id.skip_button_id_open);
//        WebView webView = dialog.findViewById(R.id.web_view_Ad_open);
//        webView.getSettings().setJavaScriptEnabled(true);
//
//
//        webView.loadUrl(link);
//
//        webView.setWebViewClient(new WebViewClient() {
//
//            public void onPageStarted(
//                    WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//                dialog.show();
//                skipbutton.setVisibility(View.VISIBLE);
//                skipbuttonText.setVisibility(View.VISIBLE);
//            }
//
//        });
//
//
//        webView.setOnTouchListener(new View.OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_MOVE) {
//                    return false;
//                }
//
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    webintent(link);
//                }
//
//                return false;
//            }
//        });
//
//
//        final Handler handler = new Handler();
//        final Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                if (dialog.isShowing()) {
//                    dialog.dismiss();
//                    DialogStartPCBWAY();
//                }
//            }
//        };
//
//
//        skipbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//                handler.removeCallbacks(runnable);
//                DialogStartPCBWAY();
//            }
//        });
//        skipbuttonText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//                handler.removeCallbacks(runnable);
//                DialogStartPCBWAY();
//            }
//        });
//
//        handler.postDelayed(runnable, 30000);
//
//    }

//    private void DialogStartPCBWAY() {
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                // TODO Auto-generated method stub
//                DialogAdPCBWAY(PCBWAY_AD_LINK,1000*60);
//            }
//        }, 1000 * 20);
//    }

//    @SuppressLint("SetJavaScriptEnabled")
//    private void DialogAdPCBWAY(String link, int time) {
//
//        dialogPCBAppOpen = new Dialog(this);
//        dialogPCBAppOpen.setContentView(R.layout.dialog_open);
//        dialogPCBAppOpen.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//        TextView skipbutton = dialogPCBAppOpen.findViewById(R.id.skipid_open);
//        TextView skipbuttonText = dialogPCBAppOpen.findViewById(R.id.skip_button_id_open);
//        WebView webView = dialogPCBAppOpen.findViewById(R.id.web_view_Ad_open);
//        webView.getSettings().setJavaScriptEnabled(true);
//
//
//        webView.loadUrl(link);
//
//        webView.setWebViewClient(new WebViewClient() {
//
//
//            public void onPageStarted(
//                    WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//                dialogPCBAppOpen.show();
//                skipbutton.setVisibility(View.VISIBLE);
//                skipbuttonText.setVisibility(View.VISIBLE);
//            }
//
//        });
//
//
//        webView.setOnTouchListener(new View.OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_MOVE) {
//                    return false;
//                }
//
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    webintent(link);
//                }
//
//                return false;
//            }
//        });
//
//
//        final Handler handler = new Handler();
//        final Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                if (dialogPCBAppOpen.isShowing()) {
//                    dialogPCBAppOpen.dismiss();
//                }
//            }
//        };
//
//
//        skipbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialogPCBAppOpen.dismiss();
//                handler.removeCallbacks(runnable);
//            }
//        });
//        skipbuttonText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialogPCBAppOpen.dismiss();
//                handler.removeCallbacks(runnable);
//            }
//        });
//        handler.postDelayed(runnable, time);
//
//    }

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // cancel async task to get rid of memory leak
        if (connectBT != null) {
            connectBT.cancel(true);
        }
        if (bp != null) {
            bp.release();
        }

        if (bp_unlock != null) {
            bp_unlock.release();
        }

        timetoshowdialog = 0;
        if (dialogll != null && dialogll.isShowing() || dialog != null && dialog.isShowing() || dialogPCBAppOpen != null && dialogPCBAppOpen.isShowing()) {
            dialogll.cancel();
            dialog.cancel();
            dialogPCBAppOpen.cancel();
        }
    }
    public boolean isInternetConnected() {

        ConnectivityManager connectivity = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

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

    private void DialogAdJLCPCB(String link) {

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_jlcpcb_open);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView skipbutton = dialog.findViewById(R.id.skipid_open);
//        Button Downloadbutton = dialog.findViewById(R.id.dowload_Qxm_button);
        TextView skipbuttonText = dialog.findViewById(R.id.skip_button_id_open);
        ImageView appOpenImage = dialog.findViewById(R.id.jlcpcb_app_open);
//        webView.getSettings().setJavaScriptEnabled(true);


        appOpenImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webintent(link);
            }
        });

//        Downloadbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                webintent(PLAYSTORE_LINK_QXM);
//            }
//        });

//        webView.loadUrl(link);
//
//        webView.setWebViewClient(new WebViewClient() {
//
//            public void onPageStarted(
//                    WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//                dialog.show();
//                skipbutton.setVisibility(View.VISIBLE);
//                skipbuttonText.setVisibility(View.VISIBLE);
//            }
//
//        });


//        webView.setOnTouchListener(new View.OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_MOVE) {
//                    return false;
//                }
//
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    webintent(link);
//                }
//
//                return false;
//            }
//        });


        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (dialog.isShowing()) {
                    dialog.dismiss();
//                    DialogStartPCBWAY(link);
                }
            }
        };


        skipbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                handler.removeCallbacks(runnable);
//                DialogStartPCBWAY(link);
            }
        });
        skipbuttonText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                handler.removeCallbacks(runnable);
//                DialogStartPCBWAY(link);
            }
        });
        dialog.show();
        handler.postDelayed(runnable, 30000);

    }


}

//delete iap history
//adb shell pm clear com.android.vending