package com.example.munchkin.login;

import android.content.Intent;

import com.example.munchkin.User;
import com.example.munchkin.main.MainActivity;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class LoginPresenter {
    private LoginActivity activity;
    private LoginModel model;
    public LoginPresenter(LoginActivity activity) {
        this.activity = activity;
        model = new LoginModel();
        try {
            FileInputStream inputStream = activity.getApplicationContext().openFileInput("logindata");
            ObjectInputStream objectStream = new ObjectInputStream(inputStream);
            User data = (User)objectStream.readObject();
            if(model.login(data.getEmail(), data.getPassword()) == 0 ) {
                Intent intent = new Intent(activity, MainActivity.class);
                activity.startActivity(intent);
            }
        } catch(Exception ex) {

        }
    }
    public void login(String email, String password) {
        model.login(email, password);
    }
}
