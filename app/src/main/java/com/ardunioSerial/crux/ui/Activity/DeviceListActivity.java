package com.ardunioSerial.crux.ui.Activity;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.bluetooth.le.BluetoothLeScanner;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.ardunioSerial.crux.Adapters.DeviceListAdapter;

import com.ardunioSerial.crux.model.Dataeventclass;
import com.ardunioSerial.crux.MainActivity;
import com.ardunioSerial.crux.utils.MySession;
import com.ardunioSerial.crux.R;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;


public class DeviceListActivity extends AppCompatActivity {

    private static final String TAG = "DeviceListActivity";
    public static final int REQUEST_ENABLE_BT = 10;

 AdView mAdView;
    MySession mySession;
    // declaring necessary variables and objects

    private ListView mListView;
    private DeviceListAdapter deviceListAdapter;
    private ArrayList<BluetoothDevice> mDeviceList;
    RecyclerView rvDeviceList;
    LinearLayoutManager linearLayoutManager;

    EventBus myEventBus = EventBus.getDefault();

    Button button;

  //  private  InterstitialAd mInterstitialAd;
    // This variables are required for connecting
    String address = null;
    private ProgressDialog progress;


    BluetoothAdapter myBluetoothAdapter = null;
    public static BluetoothSocket btSocket = null;
    BluetoothLeScanner bluetoothLeScanner = null;
    public static boolean isBtConnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    String intentvalue="";
    ConnectBT connectBT;
    String name;

    boolean isvalueRed=false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices);

mySession=new MySession(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // initializing everything
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle("Connect your device");

        myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        //makeBtEnable();
        Set<BluetoothDevice> pairedDevices = myBluetoothAdapter.getBondedDevices();
        ArrayList<BluetoothDevice> list = new ArrayList<>();
        list.addAll(pairedDevices);

     //   mDeviceList = getIntent().getExtras().getParcelableArrayList("device.list");
//        Log.d(TAG, "onCreate total device count: " + mDeviceList.size());
        // mListView = (ListView) findViewById(R.id.lv_paired);
        rvDeviceList = findViewById(R.id.rvDeviceList);



        mAdView = findViewById(R.id.adView_Devicelist);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        MySession mySession=new MySession(this);
        if (mySession.isUserPurchased()){
            mAdView.setVisibility(View.INVISIBLE);
        }
        else {
            mAdView.setVisibility(View.VISIBLE);

        }


        Intent intent=getIntent();
        Bundle b = intent.getExtras();
        if (b!=null){
            intentvalue= (String) b.get("key");
        }

        if (intentvalue.equals("red")){
            isvalueRed=true;
        }
        else {
            isvalueRed=false;
        }



        deviceListAdapter = new DeviceListAdapter(list);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvDeviceList.setLayoutManager(linearLayoutManager);
        rvDeviceList.addItemDecoration(new DividerItemDecoration(rvDeviceList.getContext(), DividerItemDecoration.VERTICAL));
        rvDeviceList.setAdapter(deviceListAdapter);

        deviceListAdapter.setOnPairButtonClickListener(position -> {
            BluetoothDevice device = list.get(position);
            if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
                unpairDevice(device);
            } else {
                showToast("Pairing...");
                pairDevice(device);
            }
        });


        deviceListAdapter.setOnConnectButtonClickListener((position, view) -> {
            BluetoothDevice device = list.get(position);


            if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
                address = device.getAddress();
                 name=device.getName();
               connectBT = new ConnectBT();
                connectBT.execute();

                Log.d("Mac ADD: ", address);
                EventBus.getDefault().post(new Dataeventclass(name));
                Log.d("Mac name: ", name);

                SharedPreferences sharedPreferences=this.getSharedPreferences("devicename",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("name",address);
                editor.commit();
            }

        });


        // registering a broadcast listener for getting bluetooth changing update
        registerReceiver(mPairReceiver, new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED));
        registerReceiver(mPairReceiver, new IntentFilter(BluetoothDevice.ACTION_ACL_CONNECTED));
        registerReceiver(mPairReceiver, new IntentFilter(BluetoothDevice.ACTION_ACL_DISCONNECTED));
    }

    // destroying  broadcast listener
    @Override
    public void onDestroy() {
        unregisterReceiver(mPairReceiver);

        // cancel async task to get rid of memory leak
        if (connectBT != null) {
            connectBT.cancel(true);
        }
        super.onDestroy();

    }


    // method for pairing device
    private void pairDevice(BluetoothDevice device) {
        try {
            Method method = device.getClass().getMethod("createBond", (Class[]) null);
            method.invoke(device, (Object[]) null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // method for unpairing device
    private void unpairDevice(BluetoothDevice device) {
        try {
            Method method = device.getClass().getMethod("removeBond", (Class[]) null);
            method.invoke(device, (Object[]) null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // broadcast listener
    private final BroadcastReceiver mPairReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                final int state = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.ERROR);
                final int prevState = intent.getIntExtra(BluetoothDevice.EXTRA_PREVIOUS_BOND_STATE, BluetoothDevice.ERROR);
                if (state == BluetoothDevice.BOND_BONDED && prevState == BluetoothDevice.BOND_BONDING) {
                    showToast("Paired");
                } else if (state == BluetoothDevice.BOND_NONE && prevState == BluetoothDevice.BOND_BONDED) {
                    showToast("Unpaired");
                }

                deviceListAdapter.notifyDataSetChanged();

            } else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {
                //button.setText("Disconnected");
                isBtConnected = false;

//                showToast("Disconnected");
            } else if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {
                //button.setText("Connected");
            }
        }
    };

    // AnynchTask class for connecting with remote bluetooth device

    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute() {
            //show a progress dialog
            progress = ProgressDialog.show(DeviceListActivity.this, "Connecting...", "Please wait!!!");
        }

        //while the progress dialog is shown, the connection is done in background
        @Override
        protected Void doInBackground(Void... devices) {
            try {
                if (btSocket == null || !isBtConnected) {
                    //get the mobile bluetooth device
                    myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

                    //connects to the device's address and checks if it's available
                    BluetoothDevice dispositivo = myBluetoothAdapter.getRemoteDevice(address);
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

                showToast("Connection Failed. Is it a SPP Bluetooth? Try again.");
//                if (!(mySession.isUserPurchased())) {
//
//                    mInterstitialAd = new InterstitialAd(DeviceListActivity.this);
//                    mInterstitialAd.setAdUnitId(getString(R.string.admob_interstitial_one));
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
            } else {
                showToast("Connected");
                isBtConnected = true;


//                if (!(mySession.isUserPurchased())) {
//
//                    mInterstitialAd = new InterstitialAd(DeviceListActivity.this);
//                    mInterstitialAd.setAdUnitId(getString(R.string.admob_interstitial_one));
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

                Log.d("value11",intentvalue);

                if (isvalueRed){
                    Intent intent = new Intent(DeviceListActivity.this, RobotcontrolActivity.class);
                    startActivity(intent);
                    Animatoo.animateSlideLeft(DeviceListActivity.this);
                    finish();
                }
                else {
                    Intent intent = new Intent(DeviceListActivity.this, MainActivity.class);
                    startActivity(intent);
                    Animatoo.animateSlideLeft(DeviceListActivity.this);
                    finish();
                }

            }

            progress.dismiss();
        }
    }


    // universal method for all toasts

    public void showToast(String toastMesg) {
        Toast.makeText(getBaseContext(), toastMesg, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onPause() {
        super.onPause();
        isvalueRed=false;
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Animatoo.animateSlideRight(DeviceListActivity.this);
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