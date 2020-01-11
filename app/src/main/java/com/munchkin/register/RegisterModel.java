package com.munchkin.register;

import android.util.Log;

import com.munchkin.NetworkService;
import com.munchkin.User;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class RegisterModel {
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int register(String email, String password, String nickname) {
        try {
            if (nickname.length() < 3 || nickname.length() > 20) {
                return 101;
            }
            if (!email.matches(".+@.+")) {
                return 102;
            }
            if (password.length() < 6) {
                return 103;
            }
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            String strHash = String.format("%064x", new BigInteger(1, hash));
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        setUser(NetworkService.getInstance()
                                .getJSONApi()
                                .register(new User(email, strHash, nickname))
                                .execute().body());
                    } catch (Exception ex) {
                        Log.i("DEBUGGING", ex.getMessage());
                    }
                }
            };
            thread.start();
            thread.join();
            return user.getAnswer();
        } catch (Exception ex) {
            Log.i("DEBUGGING", ex.getMessage());
        }

        return 0;
    }
}
