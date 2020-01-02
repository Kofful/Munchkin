package com.munchkin.register;

import android.content.Context;
import android.content.Intent;

import com.munchkin.R;
import com.munchkin.User;
import com.munchkin.main.MainActivity;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class RegisterPresenter {
    private RegisterActivity activity;
    private RegisterModel model;
    public RegisterPresenter(RegisterActivity activity) {
        this.activity = activity;
        model = new RegisterModel();
    }
    public void register(String email, String password, String nickname) {
        switch(model.register(email, password, nickname)) {
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
                    intent.putExtra("userId", data.getUserId());
                    intent.putExtra("nickname", data.getNickname());
                    activity.startActivity(intent);
                } catch(Exception ex) {

                }
                break;
            case 101:
                activity.showMessage(activity.getResources().getString(R.string.wrongNickNameLength));
                break;
            case 102:
                activity.showMessage(activity.getResources().getString(R.string.wrongEmailAddress));
                break;
            case 103:
                activity.showMessage(activity.getResources().getString(R.string.wrongPasswordLength));
                break;
            case 111:
                activity.showMessage(activity.getResources().getString(R.string.thisNickNameExists));
                break;
            case 112:
                activity.showMessage(activity.getResources().getString(R.string.thisEmailExists));
                break;
        }
    }
}
