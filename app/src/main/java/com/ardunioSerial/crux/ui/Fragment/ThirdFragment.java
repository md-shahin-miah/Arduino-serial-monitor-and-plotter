package com.ardunioSerial.crux.ui.Fragment;


import android.annotation.SuppressLint;
import android.app.Dialog;
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
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

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

import net.cachapa.expandablelayout.ExpandableLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.ardunioSerial.crux.ui.Activity.DeviceListActivity.btSocket;
import static com.ardunioSerial.crux.utils.Appconfig.PRODUCT_KEY;


public class ThirdFragment extends Fragment {

    private ArrayList<String> rowsArrayList3 = new ArrayList<>();

    TextView textViewEdit_serial_monitor;

    // String datas;
    private boolean recyclershow = false;
    private MySession mySession;
    TextView editbuttons;
    private static final String TAG = "Thirdfragment";
    Helper1 helper = new Helper1();
    private EditText messageinput;
    private ImageButton sendbutton;
    LineChart vtGraph1, vtgraph2, vtgraph3;
    ExpandableLayout expandableLayout1, expandableLayout2, expandableLayout3,expandableLayout4;
    AppCompatTextView expandtext1, expandtext2, expandtext3, expandtext4;
    TextView editgp1, editgp2, editgp3, SliderName, SliderName2, Slidername3, Slidername4;
    LinearLayout linearGraphlayout;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    Button button1, button2, button3, button4, button5, button6;
    SeekBar seekBar1, seekBar2, seekBar3, seekBar4;
    SwitchCompat switch1, switch2, switch3, switch4, switch5, switch6;
    ArrayList<String> rowsArrayList1 = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_third, container, false);

        //find views
        initialization(root);

        mySession = new MySession(getContext());


        //all buttons settings button
        editbuttons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (mySession.isUserPurchased()) {
                    Intent intent = new Intent(getContext(), ButtonControlActivity.class);
                    intent.putExtra("fromfragment", "fragment3");
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

                }
            }
        });

        //  data send button
        sendbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btSocket != null) {
                    Log.d("click", "yes");
                    String msg = messageinput.getText().toString().trim();
                    senddata(msg);
                    messageinput.setText("");
                } else {
                    Toast.makeText(getContext(), "Connect bluetooth and try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
        textViewEdit_serial_monitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NewlineActivity.class);
                intent.putExtra("key", "3rd");
                startActivity(intent);
                Animatoo.animateSlideLeft(getContext());
            }
        });
        sendbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btSocket != null) {

                    String msg = messageinput.getText().toString().trim();
                    SharedPreferences sharedPreferences = getContext().getSharedPreferences("3rd", MODE_PRIVATE);
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


        //onclickexpand layout implement
        //buttonexpandlistener();

        //eXpand graph layout
        layoutexpand(expandtext1, expandableLayout1);
        layoutexpand(expandtext2, expandableLayout2);
        layoutexpand(expandtext3, expandableLayout3);
        layoutexpand(expandtext4, expandableLayout4);
        //graph data load
        editgraphs(editgp1, "graphone3");
        editgraphs(editgp2, "graphtwo3");
        editgraphs(editgp3, "graphthree3");


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

//        expandableLayout1.setListener(new ExpandableLayoutListener() {
//
//            @Override
//            public void onAnimationStart() {
//
//            }
//
//            @Override
//            public void onAnimationEnd() {
//
//            }
//
//            @Override
//            public void onPreOpen() {
//
//            }
//
//            @Override
//            public void onPreClose() {
//
//            }
//
//            @Override
//            public void onOpened() {
//                Drawable img = getContext().getResources().getDrawable(R.drawable.ic_baseline_expand_less_24);
//                expandtext1.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
//            }
//
//            @Override
//            public void onClosed() {
//                Drawable img = getContext().getResources().getDrawable(R.drawable.ic_baseline_expand_more_24);
//                expandtext1.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
//            }
//        });
//        expandableLayout2.setListener(new ExpandableLayoutListener() {
//
//            @Override
//            public void onAnimationStart() {
//
//            }
//
//            @Override
//            public void onAnimationEnd() {
//
//            }
//
//            @Override
//            public void onPreOpen() {
//
//            }
//
//            @Override
//            public void onPreClose() {
//
//            }
//
//            @Override
//            public void onOpened() {
//                Drawable img = getContext().getResources().getDrawable(R.drawable.ic_baseline_expand_less_24);
//                expandtext2.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
//            }
//
//            @Override
//            public void onClosed() {
//                Drawable img = getContext().getResources().getDrawable(R.drawable.ic_baseline_expand_more_24);
//                expandtext2.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
//            }
//        });
//        expandableLayout3.setListener(new ExpandableLayoutListener() {
//
//            @Override
//            public void onAnimationStart() {
//
//            }
//
//            @Override
//            public void onAnimationEnd() {
//
//            }
//
//            @Override
//            public void onPreOpen() {
//
//            }
//
//            @Override
//            public void onPreClose() {
//
//            }
//
//            @Override
//            public void onOpened() {
//                Drawable img = getContext().getResources().getDrawable(R.drawable.ic_baseline_expand_less_24);
//                expandtext3.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
//            }
//
//            @Override
//            public void onClosed() {
//                Drawable img = getContext().getResources().getDrawable(R.drawable.ic_baseline_expand_more_24);
//                expandtext3.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
//            }
//        });
        //prepare graph
//        prepareGraph(vtGraph1);
//        prepareGraph(vtgraph2);
//        prepareGraph(vtgraph3);
        vtGraph1.getDescription().setEnabled(false);
        vtgraph2.getDescription().setEnabled(false);
        vtgraph3.getDescription().setEnabled(false);


        getdata();

        return root;
    }

    private void initialization(View root) {
        editbuttons = root.findViewById(R.id.editable_button_textview_3rd);

        textViewEdit_serial_monitor = root.findViewById(R.id.setings_serial_3);

        button1 = root.findViewById(R.id.button_1_id_3rd);
        button2 = root.findViewById(R.id.button_2_id_3rd);
        button3 = root.findViewById(R.id.button_3_id_3rd);
        button4 = root.findViewById(R.id.button_4_id_3rd);
        button5 = root.findViewById(R.id.button_5_id_3rd);
        button6 = root.findViewById(R.id.button_6_id_3rd);

        linearGraphlayout = root.findViewById(R.id.graph_view_layout_id_3rd);

        expandtext1 = root.findViewById(R.id.expanbale_gp_control1_id_3rd);
        expandtext2 = root.findViewById(R.id.expanbale_gp_control2_id_3rd);
        expandtext3 = root.findViewById(R.id.expanbale_gp_control3_id_3rd);
        expandtext4 = root.findViewById(R.id.expanbale_gp_control4_id_3rd);

        editgp1 = root.findViewById(R.id.editable_gp1_ida_3rd);
        editgp2 = root.findViewById(R.id.editable_gp2_id_3rd);
        editgp3 = root.findViewById(R.id.editable_gp3_id_3rd);

        expandableLayout1 = root.findViewById(R.id.expandableLayout_Gp1_id_3rd);
        expandableLayout2 = root.findViewById(R.id.expandableLayout_Gp2_id_3rd);
        expandableLayout3 = root.findViewById(R.id.expandableLayout_Gp3_id_3rd);
        expandableLayout4 = root.findViewById(R.id.expandableLayout_Gp4_id_3rd);

        recyclerView = root.findViewById(R.id.recycler_id_3rd);
        messageinput = root.findViewById(R.id.send_message_edittext_3rd);
        sendbutton = root.findViewById(R.id.message_send_button_3rd);

        //switches
        switch1 = root.findViewById(R.id.switch_id_1_3rd);
        switch2 = root.findViewById(R.id.switch_id_2_3rd);
        switch3 = root.findViewById(R.id.switch_id_3_3rd);
        switch4 = root.findViewById(R.id.switch_id_4_3rd);
        switch5 = root.findViewById(R.id.switch_id_5_3rd);
        switch6 = root.findViewById(R.id.switch_id_6_3rd);


        vtGraph1 = root.findViewById(R.id.linechart1_id_3rd);
        vtgraph2 = root.findViewById(R.id.linechart2_id_3rd);
        vtgraph3 = root.findViewById(R.id.linechart3_id_3rd);

        seekBar1 = root.findViewById(R.id.seekbar_1_3rd);
        seekBar2 = root.findViewById(R.id.seekbar_2_3rd);
        seekBar3 = root.findViewById(R.id.seekbar_3_3rd);
        seekBar4 = root.findViewById(R.id.seekbar_4_3rd);

        SliderName = root.findViewById(R.id.slider_name1_text_id_3rd);
        SliderName2 = root.findViewById(R.id.slider_name2_text_id_3rd);
        Slidername3 = root.findViewById(R.id.slider_name3_text_id_3rd);
        Slidername4 = root.findViewById(R.id.slider_name4_text_id_3rd);


    }


//    private void buttonexpandlistener() {
//        expandtext4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                expandableLayout4.setVisibility(expandableLayout4.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
//                if (expandableLayout4.getVisibility() == View.VISIBLE) {
//                    Drawable img = getContext().getResources().getDrawable(R.drawable.ic_baseline_expand_less_24);
//                    expandtext4.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
//                } else {
//                    Drawable img = getContext().getResources().getDrawable(R.drawable.ic_baseline_expand_more_24);
//                    expandtext4.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
//                }
//                getdata();
//            }
//        });
//    }

    private void getdata() {
        //loaddata by refreshing
        getsliderdata("3btn7", seekBar1, SliderName);
        getsliderdata("3btn8", seekBar2, SliderName2);
        getsliderdata("3btn9", seekBar3, Slidername3);
        getsliderdata("3btn10", seekBar4, Slidername4);
        buttonfromsharepref(button1, "3btn1", "checkbox", "name", "data", "time");
        buttonfromsharepref(button2, "3btn2", "checkbox", "name", "data", "time");
        buttonfromsharepref(button3, "3btn3", "checkbox", "name", "data", "time");
        buttonfromsharepref(button4, "3btn4", "checkbox", "name", "data", "time");
        buttonfromsharepref(button5, "3btn5", "checkbox", "name", "data", "time");
        buttonfromsharepref(button6, "3btn6", "checkbox", "name", "data", "time");

        switchimplementaion(switch1, "3btn11");
        switchimplementaion(switch2, "3btn12");
        switchimplementaion(switch3, "3btn13");
        switchimplementaion(switch4, "3btn14");
        switchimplementaion(switch5, "3btn15");
        switchimplementaion(switch6, "3btn16");
    }

    //switch implement
    private void switchimplementaion(SwitchCompat switchCompat, String btnkey) {
        SharedPreferences sharedPreferences11 = getContext().getSharedPreferences(btnkey, MODE_PRIVATE);

        if (sharedPreferences11.contains("time") || sharedPreferences11.contains("data")) {
            String name = sharedPreferences11.getString("name", "switch");
            String datax = sharedPreferences11.getString("data", "no data saved");
            String timex = sharedPreferences11.getString("time", "no time set");
            boolean ischecked = sharedPreferences11.getBoolean("checkbox", false);
            int time = 1000;
            try {
                time = Integer.parseInt(timex);
            } catch (NumberFormatException ex) { // handle your exception

            }

            if (sharedPreferences11.contains("name")) {
                switchCompat.setText(name);

            }
            if (ischecked) {
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
                            //  String data = datax + "\n";
                            senddata(datax);
                            mHandler.postDelayed(this, finalTime);

                        }
                    };


                });

            } else {
                switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    private Handler mHandler = new Handler();

                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        if (isChecked) {
                            //String data = datax + "\n";
                            senddata(datax);

                        } else {
                            Toast.makeText(getContext(), name + " is truns off", Toast.LENGTH_SHORT).show();
                        }


                    }


                });

            }

        }

    }

    private void buttonfromsharepref(Button button, String checkboxKey, String checkboxvalue, String btnname, String btndata, String btntime) {

        SharedPreferences sharedPreferences11 = this.getActivity().getSharedPreferences(checkboxKey, MODE_PRIVATE);

        if (sharedPreferences11.contains("data")) {
            boolean checkboxvaluebt1 = sharedPreferences11.getBoolean(checkboxvalue, false);
            String name = sharedPreferences11.getString(btnname, "Button");
            String datax = sharedPreferences11.getString(btndata, "no data saved");
            String timex = sharedPreferences11.getString(btntime, "no time set");
            button.setText(name);
            int time = 1000;
            try {
                time = Integer.parseInt(timex);
            } catch (NumberFormatException ex) { // handle your exception

            }
            Log.d("data11", name + datax + time + checkboxvaluebt1);


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
                               // button.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rounded_button1));

                                //if (mHandler != null) return true;
//                            mHandler = new Handler();
//                            mHandler.postDelayed(mAction, time);
                                mAction.run();
                                break;
                            case MotionEvent.ACTION_UP:
                           //     button.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rounded_button));

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
                            // String data = datax + "\n";
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
                           //     button.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rounded_button1));
                                //String data = datax + "\n";
                                senddata(datax);

                                break;
                            case MotionEvent.ACTION_UP:
                             //   button.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rounded_button));

                                break;
                        }
                        return false;
                    }
                });
            }
        }

    }

    private void getsliderdata(String name, SeekBar seekBar, TextView textView) {

        SharedPreferences sharedPreferencess = this.getActivity().getSharedPreferences(name, MODE_PRIVATE);

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

                            int value = min + (progress * step);
                            String val = sliderString + (value);

                            Log.d("datasl", val);
                            senddata(val);


                            // Log.d("seek", String.valueOf(value));
                        }
                    }
            );


        }


    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        // rowsArrayList.add("");
//        Handler handler=new Handler();
//        handler = new Handler();
//        Runnable r = new Runnable() {
//            public void run() {
//
        recyclerViewAdapter = new RecyclerViewAdapter(rowsArrayList3);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();
        recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());

    }

    //        recyclerView.setAdapter(recyclerViewAdapter);
//        recyclerView.setAdapter(recyclerViewAdapter);
//
//    }
    //  };
//        handler.postDelayed(r, 0);
//        // rowsArrayList.add("");
//
//
//    }





    private void layoutexpand(AppCompatTextView expandtext, ExpandableLayout expandableLayout) {

        expandtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expandableLayout.isExpanded()) {
                    expandableLayout.collapse();
                    Drawable img = getContext().getResources().getDrawable(R.drawable.ic_baseline_expand_less_24);
                    expandtext.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                }

                else {
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

    }

    private void senddata(String value) {
        String value1 = value + "\n";
        if (btSocket != null) {
            try {

                if (rowsArrayList3.size() > 20) {
                    rowsArrayList3.remove(0);
                    rowsArrayList3.add(value);
                    recyclerView.setAdapter(recyclerViewAdapter);
                    recyclerViewAdapter.notifyDataSetChanged();
                    recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());
                } else {
                    rowsArrayList3.add(value);
                    recyclerView.setAdapter(recyclerViewAdapter);
                    recyclerViewAdapter.notifyDataSetChanged();
                    recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());
                }

                btSocket.getOutputStream().write(value1.getBytes());
            } catch (IOException e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void senddataLN(String value) {

        if (btSocket != null) {
            try {

                if (rowsArrayList3.size() > 20) {
                    rowsArrayList3.remove(0);
                    rowsArrayList3.add(value);
                    recyclerView.setAdapter(recyclerViewAdapter);
                    recyclerViewAdapter.notifyDataSetChanged();
                    recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());
                } else {
                    rowsArrayList3.add(value);
                    recyclerView.setAdapter(recyclerViewAdapter);
                    recyclerViewAdapter.notifyDataSetChanged();
                    recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());
                }

                btSocket.getOutputStream().write(value.getBytes());
            } catch (IOException e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void prepareGraph(LineChart graph) {
        graph.getAxisLeft().setTextColor(Color.WHITE);
        graph.getXAxis().setTextColor(Color.WHITE);
        graph.getLegend().setTextColor(Color.WHITE);
        graph.getDescription().setTextColor(Color.WHITE);
        graph.getDescription().setEnabled(false);
        graph.getAxisLeft().setDrawGridLines(false);        // hide background grid
        graph.getXAxis().setDrawGridLines(false);           // hide background grid
        graph.getAxisRight().setDrawGridLines(false);       // hide background grid
        // graph.getAxisLeft().setDrawLabels(false);
        graph.getAxisRight().setDrawLabels(false);
        graph.getXAxis().setDrawLabels(false);
//         graph.setMaxVisibleValueCount(500);
        graph.setTouchEnabled(false);
        graph.setPinchZoom(false);
        // graph.getAxisLeft().setGranularity(20);
        // graph.getAxisLeft().setGranularityEnabled(true);
        graph.setScaleYEnabled(true);
        graph.setScaleY(1f);
        graph.setScaleX(1f);
        graph.moveViewToX(10);
    }

    @Override
    public void onResume() {
        recyclershow = true;
        getdata();

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
        //   Toast.makeText(getContext(), "Stopped", Toast.LENGTH_SHORT).show();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        recyclershow = false;
        rowsArrayList3.clear();
        clearchartdata(vtGraph1);
        clearchartdata(vtgraph2);
        clearchartdata(vtgraph3);
        // Toast.makeText(getContext(), "Stopped", Toast.LENGTH_SHORT).show();

    }
    private void clearchartdata(LineChart vtgraph) {
        vtgraph.invalidate();
        vtgraph.clear();
    }
    @Subscribe
    public void onEvent(Dataeventclass dataeventclass) {

        Log.d("fragment3", dataeventclass.getData());

        if (recyclershow) {
            String datas = dataeventclass.getData();

            if (rowsArrayList3.size() > 20) {
                rowsArrayList3.remove(0);
                rowsArrayList3.add(datas);
                recyclerView.setAdapter(recyclerViewAdapter);
                recyclerViewAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());
            } else {
                rowsArrayList3.add(datas);
                recyclerView.setAdapter(recyclerViewAdapter);
                recyclerViewAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());
            }


            SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("graphone3", MODE_PRIVATE);
            SharedPreferences sharedPreferences2 = this.getActivity().getSharedPreferences("graphtwo3", MODE_PRIVATE);
            SharedPreferences sharedPreferences3 = this.getActivity().getSharedPreferences("graphthree3", MODE_PRIVATE);
            if ((sharedPreferences1.contains("startstring")) || (sharedPreferences2.contains("startstring")) || (sharedPreferences3.contains("startstring"))) {

                String startstringsp1 = sharedPreferences1.getString("startstring", "no data saved");
                String labelgrp1 = sharedPreferences1.getString("label", "No label");
                String startstringsp2ndprgh = sharedPreferences2.getString("startstring", "no data saved");
                String labelgrp2 = sharedPreferences2.getString("label", "No label");
                String startstringsp3rdprgh = sharedPreferences3.getString("startstring", "no data saved");
                String labelgrp3 = sharedPreferences3.getString("label", "No label");

                Log.d("spre", startstringsp2ndprgh);

                String[] splitData = datas.split(startstringsp2ndprgh);

                String[] splitData1 = datas.split(startstringsp1);
                String[] splitData2 = datas.split(startstringsp2ndprgh);
                String[] splitData3 = datas.split(startstringsp3rdprgh);

                String[] splitData1isfortwo12 = datas.split(startstringsp1);
                String[] splitData1isfortwo13 = datas.split(startstringsp1);
                String[] splitData1isfortwo23 = datas.split(startstringsp2ndprgh);


                //  String[] splitData1 = data.split(startstringsp1);


                if ((splitData.length == 2)) {
                    Log.d("splitData", splitData[0]);

                    String[] xDataArray = splitData[0].split(startstringsp1);
                    String[] yDataArray = splitData[1].split(startstringsp3rdprgh);

//                Log.d("ydata0/1",yDataArray[0]+yDataArray[1]);

                    if (xDataArray.length == 2) {
                        if (Helper1.isNumeric(xDataArray[1])) {
                            float xFloat = Float.parseFloat(xDataArray[1]);
                            Log.d("xf", String.valueOf(xFloat));
                            helper.addEntry(xFloat, vtGraph1, labelgrp1, "#6aff6a");
                        }
                    }


                    if (yDataArray.length == 2) {
                        String yArray = yDataArray[0];
                        String zArray = yDataArray[1];

                        if (Helper1.isNumeric(yArray)) {
                            float yFloat = Float.parseFloat(yArray);
                            Log.d("xf", String.valueOf(yFloat));
                            helper.addEntry(yFloat, vtgraph2, labelgrp2, "#6aff6a");
                        }
                        if (Helper1.isNumeric(zArray)) {
                            float zFloat = Float.parseFloat(zArray);
                            Log.d("xf", String.valueOf(zFloat));
                            helper.addEntry(zFloat, vtgraph3, labelgrp3, "#6aff6a");
                        }
                    }
                }


                // //implementation of  any one graph from 3
                if (splitData1.length == 2 && Helper1.isNumeric(splitData1[1])) {
                    if (Helper1.isNumeric(splitData1[1])) {

                        float xFloat = Float.parseFloat(splitData1[1]);
                        Log.d("xf1", String.valueOf(xFloat));
                        helper.addEntry(xFloat, vtGraph1, labelgrp1, "#6aff6a");
                    }
                }
                if (splitData2.length == 2 && Helper1.isNumeric(splitData2[1])) {
                    if (Helper1.isNumeric(splitData2[1])) {

                        float xFloat = Float.parseFloat(splitData2[1]);
                        Log.d("xf1", String.valueOf(xFloat));
                        helper.addEntry(xFloat, vtgraph2, labelgrp2, "#6aff6a");
                    }
                }
                if (splitData3.length == 2 && Helper1.isNumeric(splitData3[1])) {
                    if (Helper1.isNumeric(splitData3[1])) {

                        float xFloat = Float.parseFloat(splitData3[1]);
                        Log.d("xf1", String.valueOf(xFloat));
                        helper.addEntry(xFloat, vtgraph3, labelgrp3, "#6aff6a");
                    }
                }


                //  implementation of  any two graph from 3
                if (splitData1isfortwo12.length == 2 && !(Helper1.isNumeric(splitData1isfortwo12[1]))) {
                    Log.d("splitDatacheck", splitData1isfortwo12[1]);

                    String[] xDataArray = splitData1isfortwo12[1].split(startstringsp2ndprgh);
                    // String yDataArray = splitData[1];

                    if (xDataArray.length == 2) {

                        if (Helper1.isNumeric(xDataArray[0])) {

                            float xFloat = Float.parseFloat(xDataArray[0]);
                            Log.d("xf1", String.valueOf(xFloat));
                            helper.addEntry(xFloat, vtGraph1, labelgrp1, "#6aff6a");
                        }
                    }

                    if (Helper1.isNumeric(xDataArray[1])) {
                        float yFloat = Float.parseFloat(xDataArray[1]);
                        Log.d("xf2", String.valueOf(yFloat));
                        helper.addEntry(yFloat, vtgraph2, labelgrp2, "#6aff6a");
                    }
                }
                if (splitData1isfortwo13.length == 2 && !(Helper1.isNumeric(splitData1isfortwo13[1]))) {
                    Log.d("splitDatacheck", splitData1isfortwo13[1]);

                    String[] xDataArray = splitData1isfortwo13[1].split(startstringsp2ndprgh);
                    // String yDataArray = splitData[1];

                    if (xDataArray.length == 2) {

                        if (Helper1.isNumeric(xDataArray[0])) {

                            float xFloat = Float.parseFloat(xDataArray[0]);
                            Log.d("xf1", String.valueOf(xFloat));
                            helper.addEntry(xFloat, vtGraph1, labelgrp1, "#6aff6a");
                        }
                    }

                    if (Helper1.isNumeric(xDataArray[1])) {
                        float yFloat = Float.parseFloat(xDataArray[1]);
                        Log.d("xf2", String.valueOf(yFloat));
                        helper.addEntry(yFloat, vtgraph3, labelgrp3, "#6aff6a");
                    }
                }
                if (splitData1isfortwo23.length == 2 && !(Helper1.isNumeric(splitData1isfortwo23[1]))) {
                    Log.d("splitDatacheck", splitData1isfortwo23[1]);

                    String[] xDataArray = splitData1isfortwo23[1].split(startstringsp2ndprgh);
                    // String yDataArray = splitData[1];

                    if (xDataArray.length == 2) {

                        if (Helper1.isNumeric(xDataArray[0])) {

                            float xFloat = Float.parseFloat(xDataArray[0]);
                            Log.d("xf1", String.valueOf(xFloat));
                            helper.addEntry(xFloat, vtgraph2, labelgrp2, "#6aff6a");
                        }
                    }

                    if (Helper1.isNumeric(xDataArray[1])) {
                        float yFloat = Float.parseFloat(xDataArray[1]);
                        Log.d("xf2", String.valueOf(yFloat));
                        helper.addEntry(yFloat, vtgraph3, labelgrp3, "#6aff6a");
                    }
                }


            }

        }


    }


}