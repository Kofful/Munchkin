package com.munchkin.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.munchkin.R;
import com.munchkin.register.RegisterActivity;

public class LoginActivity extends AppCompatActivity implements LoginView{

    private LoginPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        presenter = new LoginPresenter(this);
        super.onCreate(savedInstanceState);
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
