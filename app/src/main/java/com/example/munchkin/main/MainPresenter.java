package com.example.munchkin.main;

import android.content.Context;
import android.util.Log;

import com.example.munchkin.R;
import com.example.munchkin.User;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import okio.HashingSink;

public class MainPresenter {
    private MainActivity activity;
    private MainModel model;
    public MainPresenter(MainActivity activity) {
        this.activity = activity;
        model = new MainModel();
    }
}
