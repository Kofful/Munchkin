package com.example.munchkin.login;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.munchkin.R;
import com.example.munchkin.User;
import com.example.munchkin.main.MainActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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
            Log.i("DEBUGGING", ex.getMessage());
        }
    }
    public void login(String email, String password) {
        switch(model.login(email, password)) {
            case 0:
                try {
                    FileOutputStream outputStream = activity.getApplicationContext().openFileOutput("logindata", Context.MODE_PRIVATE);
                    ObjectOutputStream objectOutputStream =  new ObjectOutputStream(outputStream);
                    User data = model.getUser();
                    data.setPassword(password);
                    objectOutputStream.writeObject(data);
                    objectOutputStream.close();
                    outputStream.close();
                    Intent intent = new Intent(activity, MainActivity.class);
                    intent.putExtra("email", data.getEmail());
                    intent.putExtra("nickname", data.getNickname());
                    activity.startActivity(intent);
                } catch(Exception ex) {
                    Log.i("DEBUGGING", ex.getMessage());
                }
                break;
            case 211:
                activity.showMessage(activity.getResources().getString(R.string.incorrectEmail));
                break;
            case 212:
                activity.showMessage(activity.getResources().getString(R.string.incorrectPassword));
                break;
        }
    }
}
