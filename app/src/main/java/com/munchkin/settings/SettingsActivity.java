package com.munchkin.settings;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DataSetObserver;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.munchkin.R;
import com.munchkin.main.ButtonFragment;
import com.munchkin.main.MainActivity;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity implements SettingsView {
    SettingsPresenter presenter;
    boolean firstSelect = true; //made to select language at first time

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }

    public void back(View view) {
        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        presenter = new SettingsPresenter(this);
        int avatarId = presenter.getAvatar();
        Spinner spinner = findViewById(R.id.spin_languages);
        switch(getResources().getConfiguration().locale.getLanguage()) {
            case "ru": {
                spinner.post(new Runnable() {
                    @Override
                    public void run() {
                        spinner.setSelection(1, false);
                    }
                });
                break;
            }

            case "en-us": {
                spinner.post(new Runnable() {
                    @Override
                    public void run() {
                        spinner.setSelection(0, false);
                    }
                });
                break;
            }
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                changeLanguage(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ImageButton avatar = findViewById(R.id.btn_avatar);
        avatar.setBackgroundResource(getResources().getIdentifier("monster" + avatarId, "drawable", getPackageName()));
    }

    @Override
    public void showAvatars(View view) {
        Intent intent = new Intent(getApplicationContext(), AvatarActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivityForResult(intent, 5);
    }

    @Override
    public void changeLanguage(int i) {
        if(!firstSelect) {
            Locale newLocale = new Locale("en-us");
            switch (i) {
                case 0: {
                    newLocale = new Locale("en-us");
                    SharedPreferences sharedPref = getSharedPreferences("language", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("str", "en-us");
                    editor.apply();
                    break;
                }
                case 1: {
                    newLocale = new Locale("ru");
                    SharedPreferences sharedPref = getSharedPreferences("language", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("str", "ru");
                    editor.apply();
                    break;
                }
            }
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = newLocale;
            res.updateConfiguration(conf, dm);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getResources().getString(R.string.restart_to_apply_changes))
                    .setCancelable(false)
                    .setNegativeButton("ОК",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    System.exit(0);
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
        }
        firstSelect = false;
    }


    @Override
    public void showNoConnectionAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.noConnection))
                .setCancelable(false)
                .setNegativeButton("ОК",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finishAffinity();
                                System.exit(0);
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }


    @Override
    public void onActivityResult(int resourceCode, int resultCode, Intent data) {
        if (data != null) {
            int avatarId = data.getIntExtra("avatarid", 1);
            presenter.changeAvatar(avatarId);
            ImageButton avatar = findViewById(R.id.btn_avatar);
            avatar.setBackgroundResource(getResources().getIdentifier("monster" + avatarId, "drawable", getPackageName()));
        }

    }
}
