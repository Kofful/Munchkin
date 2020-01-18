package com.munchkin.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.munchkin.R;
import com.munchkin.register.RegisterActivity;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity implements LoginView{

    private LoginPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        presenter = new LoginPresenter(this);
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPref = getSharedPreferences("language", Context.MODE_PRIVATE);
        String str = sharedPref.getString("str", "ru");
        Locale locale = new Locale(str);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = locale;
        res.updateConfiguration(conf, dm);
    }
    @Override
    public void login(View view) {
        View parentView = (View)view.getParent();
        String email = ((EditText) parentView.findViewById(R.id.login_email)).getText().toString();
        String password = ((EditText) parentView.findViewById(R.id.login_password)).getText().toString();
        presenter.login(email, password);
    }

    public void register(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

}
