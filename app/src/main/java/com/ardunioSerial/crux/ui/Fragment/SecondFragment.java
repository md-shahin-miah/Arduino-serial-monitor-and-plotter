package com.ardunioSerial.crux.ui.Fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
//import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialog;
//import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialogListener;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.github.mikephil.charting.charts.LineChart;
import com.ardunioSerial.crux.Adapters.RecyclerViewAdapter;
import com.ardunioSerial.crux.model.Dataeventclass;
import com.ardunioSerial.crux.MainActivity;
import com.ardunioSerial.crux.ui.Activity.NewlineActivity;
import com.ardunioSerial.crux.utils.MySession;
import com.ardunioSerial.crux.R;
import com.ardunioSerial.crux.ui.Activity.ButtonControlActivity;
import com.ardunioSerial.crux.ui.Activity.GraphControlActivity;
import com.ardunioSerial.crux.utils.Helper1;
import com.yarolegovich.lovelydialog.LovelySaveStateHandler;

import net.cachapa.expandablelayout.ExpandableLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.ardunioSerial.crux.MainActivity.rowsArrayList;
import static com.ardunioSerial.crux.ui.Activity.DeviceListActivity.btSocket;
import static com.ardunioSerial.crux.utils.Appconfig.PRODUCT_KEY;


public class SecondFragment extends Fragment {
    private ArrayList<String> rowsArrayList2 = new ArrayList<>();
    private boolean recyclershow = false;


    private MySession mySession;
    private BillingProcessor billingProcessor;
    private boolean ReadyToPurchase = false;
    ImageView imageViewPayment;

    //String datas;
    public static boolean IsUSerPurchased = false;
    TextView editbuttons;

    private static final String TAG = "GraphActivity";
    Helper1 helper = new Helper1();


    Thread workerThread;
    byte[] readBuffer;
    int readBufferPosition;
    volatile boolean stopWorker = false;
    InputStream mmInputStream;
    private TextView textView;
    private EditText messageinput;
    private ImageButton sendbutton;
    LineChart vtGraph1, vtgraph2;

    String startstringsp2ndprgh;
    String startstringsp1;
    String labelgp1, labelgp2;

    ExpandableLayout expandableLayout1, expandableLayout2, expandableLayout3;

    // RelativeLayout expandableLayout3;
    AppCompatTextView expandtext1, expandtext2, expandtext3;
    TextView editgp1, editgp2, SliderName, SliderName2;

    LinearLayout linearGraphlayout;

    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;

    Button button1, button2, button3, button4, button5, button6;
    SeekBar seekBar1, seekBar2;
//    ArrayList<String> rowsArrayList = new ArrayList<>();

    boolean isLoading = false;


    SwitchCompat switch1, switch2, switch3;

    private LovelySaveStateHandler saveStateHandler;

    TextView textViewEdit_serial_monitor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_second, container, false);


        saveStateHandler = new LovelySaveStateHandler();

        mySession = new MySession(getContext());

        textViewEdit_serial_monitor = root.findViewById(R.id.setings_serial_2);

        editbuttons = root.findViewById(R.id.editable_button_textview_2nd);
        button1 = root.findViewById(R.id.button_1_id_2nd);
        button2 = root.findViewById(R.id.button_2_id_2nd);
        button3 = root.findViewById(R.id.button_3_id_2nd);
        button4 = root.findViewById(R.id.button_4_id_2nd);
        button5 = root.findViewById(R.id.button_5_id_2nd);
        button6 = root.findViewById(R.id.button_6_id_2nd);

        linearGraphlayout = root.findViewById(R.id.graph_view_layout_id_2nd);

        expandtext1 = root.findViewById(R.id.expanbale_gp_control1_id_2nd);
        expandtext2 = root.findViewById(R.id.expanbale_gp_control2_id_2nd);
        expandtext3 = root.findViewById(R.id.expanbale_gp_control3_id_2nd);

        editgp1 = root.findViewById(R.id.editable_gp1_id_2nd);
        editgp2 = root.findViewById(R.id.editable_gp2_id_2nd);

        expandableLayout1 = root.findViewById(R.id.expandableLayout_Gp1_id_2nd);
        expandableLayout2 = root.findViewById(R.id.expandableLayout_Gp2_id_2nd);
        expandableLayout3 = root.findViewById(R.id.expandableLayout_Gp3_id_2nd);

        recyclerView = root.findViewById(R.id.recycler_id_2nd);
        messageinput = root.findViewById(R.id.send_message_edittext_2nd);
        sendbutton = root.findViewById(R.id.message_send_button_2nd);

        vtGraph1 = root.findViewById(R.id.linechart1_id_2nd);
        vtgraph2 = root.findViewById(R.id.linechart2_id_2nd);

        seekBar1 = root.findViewById(R.id.seekbar_1_2nd);
        seekBar2 = root.findViewById(R.id.seekbar_2_2nd);

        SliderName = root.findViewById(R.id.slider_name1_text_id_2nd);
        SliderName2 = root.findViewById(R.id.slider_name2_text_id_2nd);


        switch1 = root.findViewById(R.id.switch_id_1_2nd);
        switch2 = root.findViewById(R.id.switch_id_2_2nd);
        switch3 = root.findViewById(R.id.switch_id_3_2nd);


        editbuttons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mySession.isUserPurchased()) {
                    Intent intent = new Intent(getContext(), ButtonControlActivity.class);
                    intent.putExtra("fromfragment", "fragment2");
                    startActivity(intent);
                    Animatoo.animateSlideLeft(getContext());
                } else {



                    Dialog dialog=new Dialog(getContext());
                    dialog.setContentView(R.layout.dialog_app_purchase);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    Button YesButton=dialog.findViewById(R.id.buttonExit);
                    Button NoButton=dialog.findViewById(R.id.buttonCancel);
                    YesButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MainActivity.bp_unlock.purchase(getActivity(), PRODUCT_KEY);
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

//                    new TTFancyGifDialog.Builder((Activity) getActivity())
//                            .setTitle("This Features are premium!")
//                            .setMessage("Are you sure you want to Purchase? ")
//                            .setPositiveBtnText("YES")
//                            .setPositiveBtnBackground("#22b573")
//                            .setNegativeBtnText("NO")
//                            .setNegativeBtnBackground("#c1272d")
//                            .setGifResource(R.drawable.dollar)     //pass your gif, png or jpg
//                            .isCancellable(true)
//                            .OnPositiveClicked(new TTFancyGifDialogListener() {
//                                @Override
//                                public void OnClick() {
//
//                                    MainActivity.bp_unlock.purchase(getActivity(), PRODUCT_KEY);
//                                }
//                            })
//                            .OnNegativeClicked(new TTFancyGifDialogListener() {
//                                @Override
//                                public void OnClick() {
//
//                                }
//                            })
//
//                            .build();
                }

            }
        });


        // graph
        editgraphs(editgp1, "graphone2");
        editgraphs(editgp2, "graphtwo2");
//        expandtext3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (expandableLayout1.isExpanded()) {
//                    expandableLayout1.collapse();
//                    Drawable img = getContext().getResources().getDrawable(R.drawable.ic_baseline_expand_less_24);
//                    expandtext1.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
//                }
//
//                else {
//                    expandableLayout1.expand();
//                    Drawable img = getContext().getResources().getDrawable(R.drawable.ic_baseline_expand_more_24);
//                    expandtext1.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
//                }
//
//                getdata();
//
//                //graph
//            }
//        });


//        vtGraph1.getDescription().setEnabled(false);
//        vtgraph2.getDescription().setEnabled(false);
//
//        vtGraph1.getAxisRight().setStartAtZero(false);
//        vtgraph2.getAxisRight().setStartAtZero(false);
//        vtGraph1.getAxisLeft().setStartAtZero(false);
//        vtGraph1.getAxisRight().setStartAtZero(false);
//        vtgraph2.getAxisLeft().setStartAtZero(false);
//        vtgraph2.getAxisRight().setStartAtZero(false);
//
//        vtGraph1.setVisibleXRangeMaximum(10);
//        vtgraph2.setVisibleXRangeMaximum(10);
//        vtGraph1.setVisibleYRangeMaximum(50, YAxis.AxisDependency.LEFT);
//        vtgraph2.setVisibleYRangeMaximum(50, YAxis.AxisDependency.LEFT);


        prepareGraph(vtGraph1);
        prepareGraph(vtgraph2);
        textViewEdit_serial_monitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NewlineActivity.class);
                intent.putExtra("key", "2nd");
                startActivity(intent);
                Animatoo.animateSlideLeft(getContext());
            }
        });
        sendbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btSocket != null) {

                    String msg = messageinput.getText().toString().trim();
                    SharedPreferences sharedPreferences = getContext().getSharedPreferences("2nd", MODE_PRIVATE);
                    boolean ischecked = sharedPreferences.getBoolean("checkbox", false);

                    if (ischecked) {
                        senddata(msg);
                        messageinput.setText("");

                    } else {
                        senddataLN(msg);
                        messageinput.setText("");
                    }
                } else {
                    Toast.makeText(getContext(), "Connect bluetooth and try again", Toast.LENGTH_SHORT).show();
                }
            }
        });


        layoutexpand(expandtext1, expandableLayout1);
        layoutexpand(expandtext2, expandableLayout2);
        layoutexpand(expandtext3, expandableLayout3);

        expandableLayout1.setOnExpansionUpdateListener(new ExpandableLayout.OnExpansionUpdateListener() {
            @Override
            public void onExpansionUpdate(float expansionFraction, int state) {
                Log.d("ExpandableLayout1", "State: " + state);

            }
        });
        expandableLayout2.setOnExpansionUpdateListener(new ExpandableLayout.OnExpansionUpdateListener() {
            @Override
            public void onExpansionUpdate(float expansionFraction, int state) {
                Log.d("ExpandableLayout1", "State: " + state);

            }
        });
        expandableLayout3.setOnExpansionUpdateListener(new ExpandableLayout.OnExpansionUpdateListener() {
            @Override
            public void onExpansionUpdate(float expansionFraction, int state) {
                Log.d("ExpandableLayout1", "State: " + state);

            }
        });

        getdata();

        return root;
    }

    private void getdata() {
        getsliderdata("2btn7", seekBar1, SliderName);
        getsliderdata("2btn8", seekBar2, SliderName2);

        buttonfromsharepref(button1, "2btn1");
        buttonfromsharepref(button2, "2btn2");
        buttonfromsharepref(button3, "2btn3");
        buttonfromsharepref(button4, "2btn4");
        buttonfromsharepref(button5, "2btn5");
        buttonfromsharepref(button6, "2btn6");

        switchimplementaion(switch1, "2btn11");
        switchimplementaion(switch2, "2btn12");
        switchimplementaion(switch3, "2btn13");
    }


    private void switchimplementaion(SwitchCompat switchCompat, String btnkey) {
        SharedPreferences sharedPreferences11 = getContext().getSharedPreferences(btnkey, Context.MODE_PRIVATE);

        if (sharedPreferences11.contains("time") || sharedPreferences11.contains("data")) {
            String name = sharedPreferences11.getString("name", "switch");
            String datax = sharedPreferences11.getString("data", "no data saved");
            String timex = sharedPreferences11.getString("time", "no time set");
            boolean ischeckbox = sharedPreferences11.getBoolean("checkbox", false);
            int time = 1000;
            try {
                time = Integer.parseInt(timex);
            } catch (NumberFormatException ex) { // handle your exception

            }
            if (sharedPreferences11.contains("name")) {
                switchCompat.setText(name);

            }


            if (ischeckbox) {
                int finalTime = time;
                switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    private Handler mHandler = new Handler();

                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        if (isChecked) {

                            mAction.run();


                        } else {
                            Toast.makeText(getContext(), name + " is truns off", Toast.LENGTH_SHORT).show();
                            mHandler.removeCallbacks(mAction);
                        }


                    }


                    Runnable mAction = new Runnable() {
                        @SuppressLint("ResourceAsColor")
                        @Override
                        public void run() {
                            // String data = datax + "\n";
                            senddata(datax);
                            mHandler.postDelayed(this, finalTime);

                        }
                    };


                });

            } else {
                switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        if (isChecked) {
                            //  String data = datax + "\n";
                            senddata(datax);


                        } else {
                            Toast.makeText(getContext(), name + " is truns  off", Toast.LENGTH_SHORT).show();
                        }


                    }


                });

            }


        }

    }


    private void buttonfromsharepref(Button button, String checkboxKey) {

        SharedPreferences sharedPreferences11 = this.getActivity().getSharedPreferences(checkboxKey, Context.MODE_PRIVATE);
        if (sharedPreferences11.contains("data")) {
            boolean checkboxvaluebt1 = sharedPreferences11.getBoolean("checkbox", false);
            String name = sharedPreferences11.getString("name", "Button");
            String datax = sharedPreferences11.getString("data", "no data saved");
            String timex = sharedPreferences11.getString("time", "no time set");
            button.setText(name);
            int time = 1000;
            try {
                time = Integer.parseInt(timex);
            } catch (NumberFormatException ex) { // handle your exception

            }
            Log.d("data11", name + datax + timex + checkboxvaluebt1);


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
                                //   button.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rounded_button1));

                                //if (mHandler != null) return true;
//                            mHandler = new Handler();
//                            mHandler.postDelayed(mAction, time);
                                mAction.run();
                                break;
                            case MotionEvent.ACTION_UP:

                                //   button.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rounded_button));

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
                            //String data = datax + "\n";
                            senddata(datax);
                            mHandler.postDelayed(this, finalTime);


                        }
                    };

                });


            } else {

                button.setOnTouchListener(new View.OnTouchListener() {


                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                //  button.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rounded_button1));
                                // String data = datax + "\n";
                                senddata(datax);

                                break;
                            case MotionEvent.ACTION_UP:
                                //     button.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rounded_button));

                                break;
                        }
                        return false;
                    }
                });
            }
        }

    }

    private void getsliderdata(String name, SeekBar seekBar, TextView textView) {

        SharedPreferences sharedPreferencess = this.getActivity().getSharedPreferences(name, Context.MODE_PRIVATE);

        if ((sharedPreferencess.contains("min")) && (sharedPreferencess.contains("max"))) {
            int minslider1 = sharedPreferencess.getInt("min", 10);
            int maxslider1 = sharedPreferencess.getInt("max", 110);
            String sliderString = sharedPreferencess.getString("string", "null");
            String slidername = sharedPreferencess.getString("name", "null");

            Log.d("seek1", String.valueOf(minslider1 + "" + "" + maxslider1));

            int step = 10;
            int max = maxslider1;
            int min = minslider1;
            textView.setText(slidername);

// Ex :
// If you want values from 3 to 5 with a step of 0.1 (3, 3.1, 3.2, ..., 5)
// this means that you have 21 possible values in the seekbar.
// So the range of the seek bar will be [0 ; (5-3)/0.1 = 20].
            seekBar.setMax((max - min) / step);

            seekBar.setOnSeekBarChangeListener(
                    new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {
                        }

                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress,
                                                      boolean fromUser) {
                            // Ex :
                            // And finally when you want to retrieve the value in the range you
                            // wanted in the first place -> [3-5]
                            //
                            // if progress = 13 -> value = 3 + (13 * 0.1) = 4.3
                            if ((sharedPreferencess.contains("min")) && (sharedPreferencess.contains("max"))) {
                                int value = min + (progress * step);
                                String val = sliderString + (value);

                                Log.d("datasl", val);
                                senddata(val);
                            }

                            // Log.d("seek", String.valueOf(value));
                        }
                    }
            );


        }


    }

    //expandable layout implementation


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        Handler handler=new Handler();
//        handler = new Handler();
//        Runnable r = new Runnable() {
//            public void run() {
        recyclerViewAdapter = new RecyclerViewAdapter(rowsArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();
        recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());

//            }
//        };
//        handler.postDelayed(r, 0);
//        // rowsArrayList.add("");


    }

    private void layoutexpand(AppCompatTextView expandtext, ExpandableLayout expandableLayout) {

        expandtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expandableLayout.isExpanded()) {
                    expandableLayout.collapse();
                    Drawable img = getContext().getResources().getDrawable(R.drawable.ic_baseline_expand_less_24);
                    expandtext.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                } else {
                    expandableLayout.expand();
                    Drawable img = getContext().getResources().getDrawable(R.drawable.ic_baseline_expand_more_24);
                    expandtext.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                }
            }
        });

    }


    private void editgraphs(TextView editgph, String graphname) {

        editgph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "isProduct Purchased " + MainActivity.bp.isPurchased(PRODUCT_KEY));

                if (mySession.isUserPurchased()) {
                    Intent intent = new Intent(getContext(), GraphControlActivity.class);
                    intent.putExtra("graph", graphname);
                    startActivity(intent);
                    Animatoo.animateSlideLeft(getContext());
                } else {


                    Dialog dialog=new Dialog(getContext());
                    dialog.setContentView(R.layout.dialog_app_purchase);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    Button YesButton=dialog.findViewById(R.id.buttonExit);
                    Button NoButton=dialog.findViewById(R.id.buttonCancel);
                    YesButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MainActivity.bp_unlock.purchase(getActivity(), PRODUCT_KEY);
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

//                    Toast.makeText(getContext(), "ok", Toast.LENGTH_SHORT).show();
////                //    SomeDrawable drawable = new SomeDrawable(Color.parseColor("Start Color Code"),Color.parseColor("Center Color Code"),Color.parseColor("End Color Code"),1,Color.BLACK,00);
////                  //  yourLayout.setBackgroundDrawable(drawable);
//                    new TTFancyGifDialog.Builder((Activity) getActivity())
//                            .setTitle("This Features are premium!")
//                            .setMessage("Are you sure you want to Purchase? ")
//                            .setPositiveBtnText("YES")
//                            .setPositiveBtnBackground("#22b573")
//                            .setNegativeBtnText("NO")
//                            .setNegativeBtnBackground("#c1272d")
//                            .setGifResource(R.drawable.ic_arrow_down)      //pass your gif, png or jpg
//                            .isCancellable(true)
//                            .OnPositiveClicked(new TTFancyGifDialogListener() {
//                                @Override
//                                public void OnClick() {
//
//                                    MainActivity.bp_unlock.purchase(getActivity(), PRODUCT_KEY);
//                                }
//                            })
//                            .OnNegativeClicked(new TTFancyGifDialogListener() {
//                                @Override
//                                public void OnClick() {
//
//                                }
//                            })
//
//                            .build();
                }


            }
        });

    }


    private void senddata(String value) {
        String value1 = value + "\n";
        if (btSocket != null) {

            try {

                if (rowsArrayList.size() > 20) {
                    rowsArrayList.remove(0);
                    rowsArrayList.add(value);
                    recyclerView.setAdapter(recyclerViewAdapter);
                    recyclerViewAdapter.notifyDataSetChanged();
                    recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());
                } else {
                    rowsArrayList.add(value);
                    recyclerView.setAdapter(recyclerViewAdapter);
                    recyclerViewAdapter.notifyDataSetChanged();
                    recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());
                }

                btSocket.getOutputStream().write(value1.getBytes());

            } catch (IOException e) {
                Toast.makeText(getContext(), e.getMessage() + "Go to Devices Activity ", Toast.LENGTH_SHORT).show();
            }


        }
    }

    private void senddataLN(String value) {

        if (btSocket != null) {

            try {

                if (rowsArrayList2.size() > 20) {
                    rowsArrayList.remove(0);
                    rowsArrayList.add(value);
                    recyclerView.setAdapter(recyclerViewAdapter);
                    recyclerViewAdapter.notifyDataSetChanged();
                    recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());
                } else {
                    rowsArrayList.add(value);
                    recyclerView.setAdapter(recyclerViewAdapter);
                    recyclerViewAdapter.notifyDataSetChanged();
                    recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());
                }

                btSocket.getOutputStream().write(value.getBytes());

            } catch (IOException e) {
                Toast.makeText(getContext(), e.getMessage() + "Go to Devices Activity ", Toast.LENGTH_SHORT).show();
            }


        }
    }

    private void prepareGraph(LineChart graph) {
//        graph.getAxisLeft().setTextColor(Color.WHITE);
//        graph.getXAxis().setTextColor(Color.WHITE);
//        graph.getLegend().setTextColor(Color.WHITE);
//        graph.getDescription().setTextColor(Color.WHITE);
        graph.getDescription().setEnabled(false);
//        graph.getAxisLeft().setDrawGridLines(false);        // hide background grid
//        graph.getXAxis().setDrawGridLines(false);           // hide background grid
//        graph.getAxisRight().setDrawGridLines(false);       // hide background grid
//         graph.getAxisLeft().setDrawLabels(true);
//        graph.getAxisRight().setDrawLabels(true);
//        graph.getXAxis().setDrawLabels(true);
//        graph.setMaxVisibleValueCount(100);
//        graph.setTouchEnabled(false);
//        graph.setPinchZoom(false);
//         graph.getAxisLeft().setGranularity(20);
//         graph.getAxisLeft().setGranularityEnabled(true);
//        graph.setScaleYEnabled(true);
//        graph.setScaleY(1f);
//        graph.setScaleX(1f);
//        graph.moveViewToX(10);
    }

    @Override
    public void onResume() {
        getdata();
        recyclershow = true;

        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
//        Toast.makeText(getContext(), "Stopped", Toast.LENGTH_SHORT).show();

        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onPause() {
        recyclershow = false;
        rowsArrayList.clear();

        clearchartdata(vtGraph1);
        clearchartdata(vtgraph2);
        super.onPause();
        // Toast.makeText(getContext(), "pause called", Toast.LENGTH_SHORT).show();

    }

    private void clearchartdata(LineChart vtgraph) {
        vtgraph.invalidate();
        vtgraph.clear();
    }


    @Subscribe
    public void onEvent(Dataeventclass dataeventclass) {


        if (recyclershow) {
            String datas = dataeventclass.getData();
            Log.d("fragment2", dataeventclass.getData());


            if (rowsArrayList.size() > 20) {
                rowsArrayList.remove(0);
                rowsArrayList.add(datas);
                Log.d("size", String.valueOf(rowsArrayList.size()));

                recyclerView.setAdapter(recyclerViewAdapter);
                recyclerViewAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());
            } else {
                rowsArrayList.add(datas);
                recyclerView.setAdapter(recyclerViewAdapter);
                recyclerViewAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());
            }


            SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("graphone2", Context.MODE_PRIVATE);
            SharedPreferences sharedPreferences2 = this.getActivity().getSharedPreferences("graphtwo2", Context.MODE_PRIVATE);
            if ((sharedPreferences1.contains("startstring")) || (sharedPreferences2.contains("startstring"))) {

                startstringsp1 = sharedPreferences1.getString("startstring", "no data saved");
                labelgp1 = sharedPreferences1.getString("label", "no label");
                startstringsp2ndprgh = sharedPreferences2.getString("startstring", "no data saved");
                labelgp2 = sharedPreferences2.getString("label", "no label");
                Log.d("spd", startstringsp2ndprgh);


                String[] splitData = datas.split(startstringsp1);

                String[] splitData2 = datas.split(startstringsp2ndprgh);

                Log.d("splitData11", String.valueOf(splitData));


                if (splitData.length == 2 && !(Helper1.isNumeric(splitData[1]))) {
                    Log.d("splitDatacheck", splitData[1]);

                    String[] xDataArray = splitData[1].split(startstringsp2ndprgh);
                    // String yDataArray = splitData[1];

                    if (xDataArray.length == 2) {

                        if (Helper1.isNumeric(xDataArray[0])) {

                            float xFloat = Float.parseFloat(xDataArray[0]);
                            Log.d("xf1", String.valueOf(xFloat));
                            helper.addEntry(xFloat, vtGraph1, labelgp1, "#6aff6a");
                        }
                    }

                    if (Helper1.isNumeric(xDataArray[1])) {
                        float yFloat = Float.parseFloat(xDataArray[1]);
                        Log.d("xf2", String.valueOf(yFloat));
                        helper.addEntry(yFloat, vtgraph2, labelgp2, "#6aff6a");
                    }
                }


                if (splitData.length == 2 && (Helper1.isNumeric(splitData[1]))) {
                    if (Helper1.isNumeric(splitData[1])) {

                        float xFloat = Float.parseFloat(splitData[1]);
                        Log.d("xf1", String.valueOf(xFloat));
                        helper.addEntry(xFloat, vtGraph1, labelgp1, "#6aff6a");
                    }
                }
                if (splitData2.length == 2 && (Helper1.isNumeric(splitData2[1]))) {
                    if (Helper1.isNumeric(splitData2[1])) {

                        float xFloat = Float.parseFloat(splitData2[1]);
                        Log.d("xf1", String.valueOf(xFloat));
                        helper.addEntry(xFloat, vtgraph2, labelgp2, "#6aff6a");
                    }
                }

            }
        }


    }


}