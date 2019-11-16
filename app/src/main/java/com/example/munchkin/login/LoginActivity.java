package com.example.munchkin.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.munchkin.R;
import com.example.munchkin.main.MainActivity;
import com.example.munchkin.register.RegisterActivity;

public class LoginActivity extends AppCompatActivity implements LoginView{

    private LoginPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        presenter = new LoginPresenter(this);
    }
    @Override
    public void login(View view) {
        String email = ((EditText) view.findViewById(R.id.login_email)).getText().toString();
        String password = ((EditText) view.findViewById(R.id.login_password)).getText().toString();
        presenter.login(email, password);
    }

    public void register(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

}
