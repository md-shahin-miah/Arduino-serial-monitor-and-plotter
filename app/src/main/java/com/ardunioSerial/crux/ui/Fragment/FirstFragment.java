package com.ardunioSerial.crux.ui.Fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import com.github.aakira.expandablelayout.ExpandableLayoutListener;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.github.mikephil.charting.charts.LineChart;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.ardunioSerial.crux.model.Dataeventclass;
import com.ardunioSerial.crux.ui.Activity.NewlineActivity;
import com.ardunioSerial.crux.utils.MySession;
import com.ardunioSerial.crux.ui.Activity.ButtonControlActivity;
import com.ardunioSerial.crux.ui.Activity.GraphControlActivity;
import com.ardunioSerial.crux.utils.Helper1;
import com.ardunioSerial.crux.R;
import com.ardunioSerial.crux.Adapters.RecyclerViewAdapter;

import net.cachapa.expandablelayout.ExpandableLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;

import static android.content.Context.MODE_PRIVATE;
import static com.ardunioSerial.crux.MainActivity.rowsArrayList;
import static com.ardunioSerial.crux.StaticValue.PCBWAY_AD_LINK;
import static com.ardunioSerial.crux.ui.Activity.DeviceListActivity.btSocket;

public class FirstFragment extends Fragment {
    TextView textViewEdit_serial_monitor;
    private boolean recyclershow = false;
    //   private String datas;
    private static final String TAG = "GraphActivity";
    Helper1 helper = new Helper1();
    private EditText messageinput;
    private ImageButton sendbutton;
    LineChart vtGraph1;
    Animation animinter;
    ExpandableLayout expandableLayout1, expandableLayout2;
   // RelativeLayout expandableLayout3;
    AppCompatTextView expandtext1, expandtext3;
    TextView editgp1, SliderName;
    TextView editablebuttonView;
    LinearLayout linearGraphlayout;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    Button button1, button2, button3;
    SeekBar seekBar1;
    SwitchCompat switch1, switch2;
     AdView mAdView;

     ImageView imageViewad;


    public static boolean isfirsttime=false;

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_first, container, false);

         Toolbar toolbar = root.findViewById(R.id.toolbar);
        textViewEdit_serial_monitor = root.findViewById(R.id.setings_serial);
        button1 = root.findViewById(R.id.button_1_id);
        button2 = root.findViewById(R.id.button_2_id);
        button3 = root.findViewById(R.id.button_3_id);

        imageViewad=root.findViewById(R.id.imageview_ad);


        editablebuttonView = root.findViewById(R.id.editable_button_textview);
        linearGraphlayout = root.findViewById(R.id.graph_view_layout_id);

        expandtext1 = root.findViewById(R.id.expanbale_gp_control1_id);
        expandtext3 = root.findViewById(R.id.expanbale_gp_control3_id);
        editgp1 = root.findViewById(R.id.editable_gp1_id);
        expandableLayout1 = root.findViewById(R.id.expandableLayout_Gp1_id);
        expandableLayout2 = root.findViewById(R.id.expandableLayout_Gp2_id);
        recyclerView = root.findViewById(R.id.recycler_id);
        messageinput = root.findViewById(R.id.send_message_edittext);
        sendbutton = root.findViewById(R.id.message_send_button);
        vtGraph1 = root.findViewById(R.id.linechart1_id);
        seekBar1 = root.findViewById(R.id.seekbar_1);

        switch1 = root.findViewById(R.id.switch_id_1);
        switch2 = root.findViewById(R.id.switch_id_2);
        SliderName = root.findViewById(R.id.slider_name_text_id);

        editbuttons();

        RelativeLayout adbnnr = root.findViewById(R.id.ad_1stfragment_relative);
//        mAdView = root.findViewById(R.id.adView_1stfragment);

//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);

        MySession mySession = new MySession(getContext());
        if (mySession.isUserPurchased()) {
            imageViewad.setVisibility(View.GONE);
        }


//
//        if (!isfirsttime){
//
//        }
//        taptarget(root,toolbar);
//
//


        imageViewad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse
                        (PCBWAY_AD_LINK));
                startActivity(browserIntent);
            }
        });


        getdata();


        vtGraph1.getDescription().setEnabled(false);
        //prepareGraph(vtGraph1);

        animinter = AnimationUtils.loadAnimation(getContext(), R.anim.translate);


        textViewEdit_serial_monitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NewlineActivity.class);
                intent.putExtra("key", "1st");
                startActivity(intent);
                Animatoo.animateSlideLeft(getContext());
            }
        });


        sendbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btSocket != null) {
                    Log.d("click", "yes");
                    String msg = messageinput.getText().toString().trim();
                    SharedPreferences sharedPreferences = getContext().getSharedPreferences("1st", MODE_PRIVATE);
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
        layoutexpand();

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
//        recyclerViewAdapter = new RecyclerViewAdapter(rowsArrayList);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.setAdapter(recyclerViewAdapter);
//        recyclerViewAdapter.notifyDataSetChanged();
//        recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());

        return root;
    }

//    private void taptarget(View root, Toolbar toolbar) {
//
//
//
//
//
//        // We load a drawable and create a location to show a tap target here
//        // We need the display to get the width and height at this point in time
//        final Display display =getActivity().getWindowManager().getDefaultDisplay();
//        // Load our little droid guy
//        final Drawable droid = ContextCompat.getDrawable(getContext(), R.drawable.ic_baseline_bluetooth_searching_24);
//        // Tell our droid buddy where we want him to appear
//        final Rect droidTarget = new Rect(0, 0, droid.getIntrinsicWidth() * 2, droid.getIntrinsicHeight() * 2);
//        // Using deprecated methods makes you look way cool
//        droidTarget.offset(display.getWidth() / 2, display.getHeight() / 2);
//
//        final SpannableString sassyDesc = new SpannableString("It allows you to go back, sometimes");
//        sassyDesc.setSpan(new StyleSpan(Typeface.ITALIC), sassyDesc.length() - "sometimes".length(), sassyDesc.length(), 0);
//
//        // We have a sequence of targets, so lets build it!
//      //  final TapTargetSequence sequence = new TapTargetSequence(getActivity())
//                .targets(
//                        // This tap target will target the back button, we just need to pass its containing toolbar
//                  //      TapTarget.forToolbarNavigationIcon(toolbar,root.findViewById(R.id.textVied_search_id), "This is the back button", sassyDesc).id(1)
////                        // Likewise, this tap target will target the search button
////                        TapTarget.forToolbarMenuItem(toolbar, R.id.search, "This is a search icon", "As you can see, it has gotten pretty dark around here...")
////                                .dimColor(android.R.color.black)
////                                .outerCircleColor(R.color.colorAccent)
////                                .targetCircleColor(android.R.color.black)
////                                .transparentTarget(true)
////                                .textColor(android.R.color.black)
////                                .id(2),
//                        // You can also target the overflow button in your toolbar
//                 //       TapTarget.forToolbarOverflow(toolbar, "This will show more options", "But they're not useful :(").id(3),
//                        // This tap target will target our droid buddy at the given target rect
////                        TapTarget.showFor(getActivity(), TapTarget.forView(root.findViewById(R.id.editable_gp1_id),"Oh look!", "You can point to any part of the screen. You also can't cancel this one!")
////                                .cancelable(false)
////                                .icon(droid)
////                                .id(1)
//
//         //SpannableString ss = new SpannableString("This is the sample app for TapTargetView");
//       // spannedDesc.setSpan(new UnderlineSpan(), spannedDesc.length() - "TapTargetView".length(), spannedDesc.length(), 0);
//     //   TapTargetView.forView(getActivity(), TapTarget.forView(root.findViewById(R.id.editable_gp1_id), "Hello, world!", "lol"),
//
/////                TapTarget.forView(root.findViewById(R.id.setings_serial), "Gonna","This will show more options\", \"But they're not useful :"),
/////                TapTarget.forView(root.findViewById(R.id.editable_button_textview), "Gonna","loli This will show more options")
/////
//
//              )
//                .listener(new TapTargetSequence.Listener() {
//                    // This listener will tell us when interesting(tm) events happen in regards
//                    // to the sequence
//                    @Override
//                    public void onSequenceFinish() {
//                        //((TextView) findViewById(R.id.educated)).setText("Congratulations! You're educated now!");
//                    }
//
//                    @Override
//                    public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {
//                        Log.d("TapTargetView", "Clicked on " + lastTarget.id());
//                    }
//
//                    @Override
//                    public void onSequenceCanceled(TapTarget lastTarget) {
//                        final AlertDialog dialog = new AlertDialog.Builder(getContext())
//                                .setTitle("Uh oh")
//                                .setMessage("You canceled the sequence")
//                                .setPositiveButton("Oops", null).show();
//                        TapTargetView.showFor(dialog,
//                                TapTarget.forView(dialog.getButton(DialogInterface.BUTTON_POSITIVE), "Uh oh!", "You canceled the sequence at step " + lastTarget.id())
//                                        .cancelable(false)
//                                        .tintTarget(false), new TapTargetView.Listener() {
//                                    @Override
//                                    public void onTargetClick(TapTargetView view) {
//                                        super.onTargetClick(view);
//                                        dialog.dismiss();
//                                    }
//                                });
//                    }
//                });
//
//        // You don't always need a sequence, and for that there's a single time tap target
//        final SpannableString spannedDesc = new SpannableString("There are so many instructions for using this graphs");
//        spannedDesc.setSpan(new UnderlineSpan(), spannedDesc.length() - "TapTargetView".length(), spannedDesc.length(), 0);
//        TapTargetView.showFor(getActivity(), TapTarget.forView(root.findViewById(R.id.editable_gp1_id), "Hello, Dear User!", spannedDesc)
//                .cancelable(false)
//                .drawShadow(true).titleTextDimen(R.dimen.title_text_size)
//                .tintTarget(false), new TapTargetView.Listener() {
//            @Override
//            public void onTargetClick(TapTargetView view) {
//                super.onTargetClick(view);
//                // .. which evidently starts the sequence we defined earlier
//                sequence.start();
//            }
//
//            @Override
//            public void onOuterCircleClick(TapTargetView view) {
//                super.onOuterCircleClick(view);
//                Toast.makeText(view.getContext(), "You clicked the outer circle!", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onTargetDismissed(TapTargetView view, boolean userInitiated) {
//                Log.d("TapTargetViewSample", "You dismissed me :(");
//            }
//        });
//    }


    private void getdata() {

        getsliderdata("1btn7", seekBar1, SliderName);
        buttonfromsharepref(button1, "1btn1", "checkbox", "name", "data", "time");
        buttonfromsharepref(button2, "1btn2", "checkbox", "name", "data", "time");
        buttonfromsharepref(button3, "1btn3", "checkbox", "name", "data", "time");

        switchimplementaion(switch1, "1btn11");
        switchimplementaion(switch2, "1btn12");


    }


    private void switchimplementaion(SwitchCompat switchCompat, String btnkey) {


        SharedPreferences sharedPreferences1 = getContext().getSharedPreferences(btnkey, MODE_PRIVATE);

        if (sharedPreferences1.contains("data") || sharedPreferences1.contains("time")) {
            String name = sharedPreferences1.getString("name", "no data saved");
            String datax = sharedPreferences1.getString("data", "no data saved");
            String timex = sharedPreferences1.getString("time", "no time");
            boolean ischeckbox = sharedPreferences1.getBoolean("checkbox", false);

            int time = 1000;
            try {
                time = Integer.parseInt(timex);
            } catch (NumberFormatException ex) { // handle your exception

            }
            Log.d("switch1st", name + datax + time + ischeckbox);
            if (sharedPreferences1.contains("name")) {
                switchCompat.setText(name);

            }

            if (ischeckbox) {
                int finalTime = time;
                switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    private Handler mHandler = new Handler();

                    //            volatile boolean shutdown = false;
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        if (isChecked) {

                            mAction.run();

                        } else {
                            Toast.makeText(getContext(), name + " truns is off", Toast.LENGTH_SHORT).show();
                            mHandler.removeCallbacks(mAction);
                            //         shutdown = true;
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
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        if (isChecked) {
                            //  String data = datax + "\n";
                            senddata(datax);

                        } else {
                            Toast.makeText(getContext(), name + " is truns  off", Toast.LENGTH_SHORT).show();
                            //         shutdown = true;
                        }


                    }

                });

            }
        }


    }

    private void layoutexpand() {

        expandtext1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (expandableLayout1.isExpanded()) {
                    expandableLayout1.collapse();
                    Drawable img = getContext().getResources().getDrawable(R.drawable.ic_baseline_expand_less_24);
                expandtext1.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                }

                else {
                    expandableLayout1.expand();
                    Drawable img = getContext().getResources().getDrawable(R.drawable.ic_baseline_expand_more_24);
                    expandtext1.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                }


////                expandableLayout1.toggle();
//            //    expandableLayout1.expand();
//                //  expandableLayout.toggle();
//// expand
//                expandableLayout1.expand();
//// collapse
//                expandableLayout1.collapse();
            }
        });

        expandtext3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expandableLayout2.isExpanded()) {
                    expandableLayout2.collapse();
                    Drawable img = getContext().getResources().getDrawable(R.drawable.ic_baseline_expand_less_24);
                    expandtext3.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                }

                else {
                    expandableLayout2.expand();
                    Drawable img = getContext().getResources().getDrawable(R.drawable.ic_baseline_expand_more_24);
                    expandtext3.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                }


                getdata();
            }
        });

    }


    private void buttonfromsharepref(Button button, String checkboxKey, String checkboxvalue, String btnname, String btndata, String btntime) {

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences(checkboxKey, MODE_PRIVATE);


        //     if (sharedPreferences1.contains("data")) {

        boolean checkboxvaluebt1 = sharedPreferences1.getBoolean(checkboxvalue, false);
        String name = sharedPreferences1.getString(btnname, "Button");
        String datax = sharedPreferences1.getString(btndata, "no data saved");
        String timex = sharedPreferences1.getString(btntime, "no time");
        button.setText(name);
        int time = 1000;
        try {
            time = Integer.parseInt(timex);
        } catch (NumberFormatException ex) { // handle your exception

        }

        Log.d("data", name + datax + timex + checkboxvaluebt1);


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

                          //  Toast.makeText(getContext(), "touch", Toast.LENGTH_SHORT).show();
                            mAction.run();
                            break;
                        case MotionEvent.ACTION_UP:
                           // Toast.makeText(getContext(), "touch re", Toast.LENGTH_SHORT).show();

                            mHandler.removeCallbacks(mAction);
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
                      //  Toast.makeText(getContext(), "touch", Toast.LENGTH_SHORT).show();
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
                            senddata(datax);

                            break;
                        case MotionEvent.ACTION_UP:
                            break;
                    }
                    return false;
                }
            });
        }


        // }


    }




    //slider

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
    //expandable layout implementation


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewAdapter = new RecyclerViewAdapter(rowsArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();
       recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());


    }

    private void editbuttons() {
        editgp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), GraphControlActivity.class);
                intent.putExtra("graph", "graphone");
                startActivity(intent);
                Animatoo.animateSlideLeft(getContext());
            }
        });


        editablebuttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), ButtonControlActivity.class);
                intent.putExtra("fromfragment", "fragment1");
                startActivity(intent);
                Animatoo.animateSlideLeft(getContext());
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

                btSocket.getOutputStream().write(value.getBytes());

            } catch (IOException e) {
                Toast.makeText(getContext(), e.getMessage() + "Go to Devices Activity ", Toast.LENGTH_SHORT).show();
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
        // graph.setMaxVisibleValueCount(500);
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
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        recyclershow = false;

        rowsArrayList.clear();
        clearchartdata(vtGraph1);

    }

    private void clearchartdata(LineChart vtgraph) {
        vtgraph.invalidate();
        vtgraph.clear();
    }

    @Override
    public void onStop() {
        super.onStop();
//        Toast.makeText(getContext(), "Stopped", Toast.LENGTH_SHORT).show();

        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume() {
        recyclershow = true;
        getdata();

        super.onResume();
    }


    @Subscribe
    public void onEvent(Dataeventclass dataeventclass) {


        if (recyclershow) {
            Log.d("fragment1", dataeventclass.getData());
            String datas = dataeventclass.getData();


            //      if (datas.equals("ON")||datas.equals("OFF")){
            if (rowsArrayList.size() > 20) {
                Log.d("fragment11", String.valueOf(rowsArrayList.size()));
                rowsArrayList.add(datas);
                Log.d("datas", datas);
                rowsArrayList.remove(0);
                Log.d("datas1", String.valueOf(rowsArrayList.size()));
                recyclerView.setAdapter(recyclerViewAdapter);
                recyclerViewAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());

            } else {
                rowsArrayList.add(datas);
                recyclerView.setAdapter(recyclerViewAdapter);
                recyclerViewAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());
            }

//             }
//             else {
//                 if (rowsArrayList.size() > 20) {
//                     Log.d("fragment11", String.valueOf(rowsArrayList.size()));
//
//                     Log.d("datas", datas);
//                     rowsArrayList.remove(0);
//                     Log.d("datas1", String.valueOf(rowsArrayList.size()));
//                     rowsArrayList.add( datas);
//                     recyclerView.setAdapter(recyclerViewAdapter);
//                     recyclerViewAdapter.notifyDataSetChanged();
//                     recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());
//
//                 } else {
//                     rowsArrayList.add(datas);
//                     recyclerView.setAdapter(recyclerViewAdapter);
//                     recyclerViewAdapter.notifyDataSetChanged();
//                     recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());
//                 }
//             }


            SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("graphone", MODE_PRIVATE);

            if (sharedPreferences1.contains("startstring")) {

                String startstringsp1 = sharedPreferences1.getString("startstring", "no data saved");
                String label1 = sharedPreferences1.getString("label", "no data saved");

                String[] splitData1 = datas.split(startstringsp1);

                if (splitData1.length == 1) {
                    Log.d("splitData", splitData1[0]);

                    String yDataArray = splitData1[0];
                    if (Helper1.isNumeric(yDataArray)) {
                        float yFloat = Float.parseFloat(yDataArray);
                        Log.d("xf", String.valueOf(yFloat));
                        helper.addEntry(yFloat, vtGraph1, label1, "#6aff6a");

                    }
                }
                if (splitData1.length == 2) {
                    Log.d("splitData", splitData1[1]);

                    String yDataArray = splitData1[1];
                    if (Helper1.isNumeric(yDataArray)) {
                        float yFloat = Float.parseFloat(yDataArray);
                        Log.d("xf", String.valueOf(yFloat));
                        helper.addEntry(yFloat, vtGraph1, label1, "#6aff6a");

                    }
                }

            }

        }

    }




}