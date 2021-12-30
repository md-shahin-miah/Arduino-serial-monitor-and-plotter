package com.ardunioSerial.crux.ui.Activity;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import com.ardunioSerial.crux.StaticValue;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.ardunioSerial.crux.utils.MyApp;
import com.ardunioSerial.crux.utils.MySession;
import com.ardunioSerial.crux.R;

import br.tiagohm.codeview.CodeView;
import br.tiagohm.codeview.Language;
import br.tiagohm.codeview.Theme;
import io.github.kbiakov.codeview.highlight.ColorTheme;
import io.github.kbiakov.codeview.highlight.ColorThemeData;
import ru.katso.livebutton.LiveButton;


public class GraphControlActivity extends AppCompatActivity implements CodeView.OnHighlightListener {

    EditText label, startstring, endstring;
    TextView sharetextbutton;
    LiveButton savebutton;
    ImageView imageview_Ads_jlcpcb;

    private AdView mAdView;


  //  private InterstitialAd mInterstitialAd;

    LinearLayout linearLayoutendstrg;
    TextView textViewOurApps, textViewInstruction;
    MySession mySession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_control);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle("Data Receive Settings");
        imageview_Ads_jlcpcb=findViewById(R.id.imageview_Ads_jlcpcb);

        savebutton = findViewById(R.id.save_button_gpc_id);
        sharetextbutton = findViewById(R.id.shareText_button);

        textViewInstruction = findViewById(R.id.textviewinstructiondetails);

        textViewOurApps = findViewById(R.id.text_ourad);

        textViewOurApps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (!URLUtil.isValidUrl("https://play.google.com/store/apps/developer?id=CRUX")) {
                        Toast.makeText(GraphControlActivity.this, " This is not a valid link", Toast.LENGTH_LONG).show();
                    } else {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("https://play.google.com/store/apps/developer?id=CRUX"));
                        startActivity(intent);
                    }
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(GraphControlActivity.this, " You don't have any browser to open web page", Toast.LENGTH_LONG).show();
                }
                Animatoo.animateSlideLeft(GraphControlActivity.this);
            }
        });

        label = findViewById(R.id.et_label_id);
        startstring = findViewById(R.id.et_start_string_id);

        CodeView codeView = findViewById(R.id.code_view);


        mAdView = findViewById(R.id.adView_graph_control);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mySession = new MySession(this);
        if (mySession.isUserPurchased()) {
            mAdView.setVisibility(View.GONE);
        }


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


//        mInterstitialAd = new InterstitialAd(this);
//        mInterstitialAd.setAdUnitId(getString(R.string.admob_interstitial_one));
        AdRequest adRequestInter = new AdRequest.Builder().build();
//        mInterstitialAd.setAdListener(new AdListener() {
//
//            @Override
//            public void onAdLoaded() {
//                //   Log.d(TAG, "onAdFailedToLoad: " + "loaded");
//
//                super.onAdLoaded();
//            }
//        });
//        mInterstitialAd.loadAd(adRequestInter);

        Intent intent = getIntent();
        String getintentvalue = intent.getStringExtra("graph");

//Interface


        if (getintentvalue.equals("graphone")) {
            codeView.setVisibility(View.VISIBLE);
            textViewInstruction.setVisibility(View.VISIBLE);
            sharetextbutton.setVisibility(View.VISIBLE);

            textViewInstruction.setText(getString(R.string.fragment1_instruction));
            //     codeView.setCode(getString(R.string.fragment1_code));

            codeView.setOnHighlightListener(GraphControlActivity.this)
                    .setOnHighlightListener(GraphControlActivity.this)
                    .setTheme(Theme.AGATE)
                    .setCode(getString(R.string.fragment1_code))
                    .setLanguage(Language.ARDUINO)
                    .setWrapLine(true)
                    .setFontSize(14)
                    .setZoomEnabled(true)
                    .setShowLineNumber(true)
                    .setStartLineNumber(1)
                    .apply();

//            ColorThemeData myTheme = ColorTheme.MONOKAI.theme()
//                    .withBgContent(android.R.color.holo_blue_dark)
//                    .withNoteColor(android.R.color.holo_red_dark);
//            codeView.getOptions().setTheme(myTheme);
            Loaddatagp1("graphone", label, startstring);

            sharetextbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ShareCompat.IntentBuilder.from(GraphControlActivity.this)
                            .setType("text/plain")
                            .setChooserTitle("Chooser title")
                            .setText(getString(R.string.fragment1_code))
                            .startChooser();
                }
            });

            savebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mySession.isUserPurchased()) {
                        String lN = label.getText().toString().trim();
                        String sS = startstring.getText().toString();


                        if (sS.equals("")) {
                            Toast.makeText(GraphControlActivity.this, "a blank space  saved", Toast.LENGTH_SHORT).show();
                        } else {
                            SharedPreferences sharedPreferences = getSharedPreferences("graphone", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("label", lN);
                            editor.putString("startstring", sS);
                            editor.commit();
                            Toast.makeText(GraphControlActivity.this, "saved data", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        String lN = label.getText().toString().trim();
                        String sS = startstring.getText().toString();


                        if (sS.equals("")) {
                            Toast.makeText(GraphControlActivity.this, "a blank space  saved", Toast.LENGTH_SHORT).show();
                        } else {
                            SharedPreferences sharedPreferences = getSharedPreferences("graphone", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("label", lN);
                            editor.putString("startstring", sS);
                            editor.commit();
                            Toast.makeText(GraphControlActivity.this, "saved data", Toast.LENGTH_SHORT).show();
                        }
                     //   mInterstitialAd.show();
                    }


                }


            });


        }

        if (getintentvalue.equals("graphone2")) {

            codeView.setVisibility(View.VISIBLE);
            textViewInstruction.setVisibility(View.VISIBLE);
            sharetextbutton.setVisibility(View.VISIBLE);

            textViewInstruction.setText(getString(R.string.fragment2_instruction));
            codeView.setCode(getString(R.string.fragment2_code));

//            ColorThemeData myTheme = ColorTheme.MONOKAI.theme()
//                    .withBgContent(android.R.color.holo_blue_dark)
//                    .withNoteColor(android.R.color.holo_red_dark);
//            codeView.getOptions().setTheme(myTheme);
            codeView.setOnHighlightListener(GraphControlActivity.this)
                    .setOnHighlightListener(GraphControlActivity.this)
                    .setTheme(Theme.AGATE)
                    .setCode(getString(R.string.fragment2_code))
                    .setLanguage(Language.ARDUINO)
                    .setWrapLine(true)
                    .setFontSize(14)
                    .setZoomEnabled(true)
                    .setShowLineNumber(true)
                    .setStartLineNumber(1)
                    .apply();

            Loaddatagp1("graphone2", label, startstring);

            sharetextbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ShareCompat.IntentBuilder.from(GraphControlActivity.this)
                            .setType("text/plain")
                            .setChooserTitle("Chooser title")
                            .setText(getString(R.string.fragment2_code))
                            .startChooser();
                }
            });
            savebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String lN = label.getText().toString().trim();
                    String sS = startstring.getText().toString();
                    //   String eS = endstring.getText().toString();


                    if (lN.equals("") || sS.equals("")) {
                        Toast.makeText(GraphControlActivity.this, " a blank space want to save", Toast.LENGTH_SHORT).show();
                    } else {
                        SharedPreferences sharedPreferences = getSharedPreferences("graphone2", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("label", lN);
                        editor.putString("startstring", sS);
                        //  editor.putString("endstring", eS);
                        editor.commit();
                        Toast.makeText(GraphControlActivity.this, "saved data", Toast.LENGTH_SHORT).show();
                    }


                }


            });


        }
        if (getintentvalue.equals("graphtwo2")) {
            codeView.setVisibility(View.VISIBLE);
            textViewInstruction.setVisibility(View.VISIBLE);
            sharetextbutton.setVisibility(View.VISIBLE);
            textViewInstruction.setText(getString(R.string.fragment2_instruction));
            //     codeView.setCode(getString(R.string.fragment2_code));

//            ColorThemeData myTheme = ColorTheme.MONOKAI.theme()
//                    .withBgContent(android.R.color.holo_blue_dark)
//                    .withNoteColor(android.R.color.holo_red_dark);
//            codeView.getOptions().setTheme(myTheme);

            codeView.setOnHighlightListener(GraphControlActivity.this)
                    .setOnHighlightListener(GraphControlActivity.this)
                    .setTheme(Theme.AGATE)
                    .setCode(getString(R.string.fragment2_code))
                    .setLanguage(Language.ARDUINO)
                    .setWrapLine(true)
                    .setFontSize(14)
                    .setZoomEnabled(true)
                    .setShowLineNumber(true)
                    .setStartLineNumber(1)
                    .apply();

            sharetextbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ShareCompat.IntentBuilder.from(GraphControlActivity.this)
                            .setType("text/plain")
                            .setChooserTitle("Chooser title")
                            .setText(getString(R.string.fragment2_code))
                            .startChooser();
                }
            });

            Loaddatagp1("graphtwo2", label, startstring);
            savebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String lN = label.getText().toString().trim();
                    String sS = startstring.getText().toString();


                    if (lN.equals("") || sS.equals("")) {
                        Toast.makeText(GraphControlActivity.this, " a blank space want to save", Toast.LENGTH_SHORT).show();
                    } else {
                        SharedPreferences sharedPreferences = getSharedPreferences("graphtwo2", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("label", lN);
                        editor.putString("startstring", sS);
                        editor.commit();
                        Toast.makeText(GraphControlActivity.this, "saved data", Toast.LENGTH_SHORT).show();
                    }


                }


            });


        }

        if (getintentvalue.equals("graphone3")) {
            codeView.setVisibility(View.VISIBLE);
            textViewInstruction.setVisibility(View.VISIBLE);
            sharetextbutton.setVisibility(View.VISIBLE);
            textViewInstruction.setText(getString(R.string.fragment3_instruction));
            // codeView.setCode(getString(R.string.fragment3_code));


            codeView.setOnHighlightListener(GraphControlActivity.this)
                    .setOnHighlightListener(GraphControlActivity.this)
                    .setTheme(Theme.AGATE)
                    .setCode(getString(R.string.fragment3_code))
                    .setLanguage(Language.ARDUINO)
                    .setWrapLine(true)
                    .setFontSize(14)
                    .setZoomEnabled(true)
                    .setShowLineNumber(true)
                    .setStartLineNumber(1)
                    .apply();

//            ColorThemeData myTheme = ColorTheme.MONOKAI.theme()
//                    .withBgContent(android.R.color.holo_blue_dark)
//                    .withNoteColor(android.R.color.holo_red_dark);
//            codeView.getOptions().setTheme(myTheme);

            Loaddatagp1("graphone3", label, startstring);
            sharetextbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ShareCompat.IntentBuilder.from(GraphControlActivity.this)
                            .setType("text/plain")
                            .setChooserTitle("Chooser title")
                            .setText(getString(R.string.fragment3_code))
                            .startChooser();
                }
            });

            savebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String lN = label.getText().toString().trim();
                    String sS = startstring.getText().toString();


                    if (lN.equals("") || sS.equals("")) {
                        Toast.makeText(GraphControlActivity.this, " a blank space want to save", Toast.LENGTH_SHORT).show();
                    } else {
                        SharedPreferences sharedPreferences = getSharedPreferences("graphone3", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("label", lN);
                        editor.putString("startstring", sS);
                        //  editor.putString("endstring", eS);
                        editor.commit();
                        Toast.makeText(GraphControlActivity.this, "saved data", Toast.LENGTH_SHORT).show();
                    }


                }


            });


        }
        if (getintentvalue.equals("graphtwo3")) {
            codeView.setVisibility(View.VISIBLE);
            textViewInstruction.setVisibility(View.VISIBLE);
            sharetextbutton.setVisibility(View.VISIBLE);
            textViewInstruction.setText(getString(R.string.fragment3_instruction));
            //   codeView.setCode(getString(R.string.fragment3_code));
            codeView.setOnHighlightListener(GraphControlActivity.this)
                    .setOnHighlightListener(GraphControlActivity.this)
                    .setTheme(Theme.AGATE)
                    .setCode(getString(R.string.fragment3_code))
                    .setLanguage(Language.ARDUINO)
                    .setWrapLine(true)
                    .setFontSize(14)
                    .setZoomEnabled(true)
                    .setShowLineNumber(true)
                    .setStartLineNumber(1)
                    .apply();
//            ColorThemeData myTheme = ColorTheme.MONOKAI.theme()
//                    .withBgContent(android.R.color.holo_blue_dark)
//                    .withNoteColor(android.R.color.holo_red_dark);
//            codeView.getOptions().setTheme(myTheme);
            Loaddatagp1("graphtwo3", label, startstring);
            sharetextbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ShareCompat.IntentBuilder.from(GraphControlActivity.this)
                            .setType("text/plain")
                            .setChooserTitle("Chooser title")
                            .setText(getString(R.string.fragment3_code))
                            .startChooser();
                }
            });

            savebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String lN = label.getText().toString().trim();
                    String sS = startstring.getText().toString();


                    if (lN.equals("") || sS.equals("")) {
                        Toast.makeText(GraphControlActivity.this, " a blank space want to save", Toast.LENGTH_SHORT).show();
                    } else {
                        SharedPreferences sharedPreferences = getSharedPreferences("graphtwo3", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("label", lN);
                        editor.putString("startstring", sS);
                        editor.commit();
                        Toast.makeText(GraphControlActivity.this, "saved data", Toast.LENGTH_SHORT).show();
                    }


                }


            });


        }
        if (getintentvalue.equals("graphthree3")) {
            codeView.setVisibility(View.VISIBLE);
            textViewInstruction.setVisibility(View.VISIBLE);
            sharetextbutton.setVisibility(View.VISIBLE);
            textViewInstruction.setText(getString(R.string.fragment3_instruction));
            //   codeView.setCode(getString(R.string.fragment3_code));

            codeView.setOnHighlightListener(GraphControlActivity.this)
                    .setOnHighlightListener(GraphControlActivity.this)
                    .setTheme(Theme.AGATE)
                    .setCode(getString(R.string.fragment3_code))
                    .setLanguage(Language.ARDUINO)
                    .setWrapLine(true)
                    .setFontSize(14)
                    .setZoomEnabled(true)
                    .setShowLineNumber(true)
                    .setStartLineNumber(1)
                    .apply();

//            ColorThemeData myTheme = ColorTheme.MONOKAI.theme()
//                    .withBgContent(android.R.color.holo_blue_dark)
//                    .withNoteColor(android.R.color.holo_red_dark);
//            codeView.getOptions().setTheme(myTheme);

            Loaddatagp1("graphthree3", label, startstring);

            sharetextbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ShareCompat.IntentBuilder.from(GraphControlActivity.this)
                            .setType("text/plain")
                            .setChooserTitle("Chooser title")
                            .setText(getString(R.string.fragment3_code))
                            .startChooser();
                }
            });

            savebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String lN = label.getText().toString().trim();
                    String sS = startstring.getText().toString();


                    if (lN.equals("") || sS.equals("")) {
                        Toast.makeText(GraphControlActivity.this, " a blank space want to save", Toast.LENGTH_SHORT).show();
                    } else {
                        SharedPreferences sharedPreferences = getSharedPreferences("graphthree3", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("label", lN);
                        editor.putString("startstring", sS);
                        editor.commit();
                        Toast.makeText(GraphControlActivity.this, "saved data", Toast.LENGTH_SHORT).show();
                    }


                }


            });


        }

    }


    private void Loaddatagp1(String graphname, EditText label, EditText startstring) {

        SharedPreferences sharedPreferences = getSharedPreferences(graphname, Context.MODE_PRIVATE);

        if (sharedPreferences.contains("label") || sharedPreferences.contains("startstring")) {

            String labelsp = sharedPreferences.getString("label", "no label saved");
            String startstringsp = sharedPreferences.getString("startstring", "no data saved");

            label.setText(labelsp);
            startstring.setText(startstringsp);


        }


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        if (!(mySession.isUserPurchased())) {
//            if (MyApp.count == 1 || MyApp.count % 3 == 0
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
//        }

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

    @Override
    public void onStartCodeHighlight() {

    }

    @Override
    public void onFinishCodeHighlight() {

    }

    @Override
    public void onLanguageDetected(Language language, int i) {

    }

    @Override
    public void onFontSizeChanged(int i) {

    }

    @Override
    public void onLineClicked(int i, String s) {

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