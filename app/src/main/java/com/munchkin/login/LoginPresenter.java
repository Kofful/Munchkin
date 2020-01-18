package com.munchkin.login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;

import com.munchkin.R;
import com.munchkin.User;
import com.munchkin.main.MainActivity;

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
            int answer = model.login(data.getEmail(), data.getPassword());
            if(answer == 0 ) {//success
                Intent intent = new Intent(activity, MainActivity.class);
                intent.putExtra("user", data);
                activity.startActivity(intent);
            } else if(answer == -1) {//no connection
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setTitle(activity.getResources().getString(R.string.noConnection))
                            .setCancelable(false)
                            .setNegativeButton("ОК",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            activity.finish();
                                        }
                                    });
                    AlertDialog alert = builder.create();
                    alert.show();
            }
        } catch(Exception ex) {
            Log.i("DEBUGGING", ex.getClass().toString());
            activity.setContentView(R.layout.activity_login);
        }
        activity.setContentView(R.layout.activity_login);
    }
    public void login(String email, String password) {
        if(email.replace(" ", "").equals("")) {
            activity.showMessage(activity.getResources().getString(R.string.incorrectEmail));
            return;
        } else if(password.replace(" ", "").equals("")){
            activity.showMessage(activity.getResources().getString(R.string.incorrectPassword));
            return;
        }
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
                    intent.putExtra("user", data);
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
