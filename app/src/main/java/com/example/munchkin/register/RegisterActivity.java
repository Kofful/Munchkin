package com.example.munchkin.register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.munchkin.R;
import com.example.munchkin.login.LoginActivity;

public class RegisterActivity extends AppCompatActivity implements RegisterView{

    private RegisterPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        presenter = new RegisterPresenter(this);
    }

    public void login(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void register(View view) {
        View parentView = (View)view.getParent();
        String email = ((EditText) parentView.findViewById(R.id.register_email)).getText().toString();
        String password = ((EditText) parentView.findViewById(R.id.register_password)).getText().toString();
        String nickname = ((EditText) parentView.findViewById(R.id.register_nickname)).getText().toString();
        presenter.register(email, password, nickname);
    }

    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
