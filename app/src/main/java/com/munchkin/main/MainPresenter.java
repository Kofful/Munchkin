package com.munchkin.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;

import com.munchkin.Friend;
import com.munchkin.R;
import com.munchkin.User;
import com.munchkin.creatinglobby.CreatingLobbyActivity;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import okhttp3.WebSocket;

public class MainPresenter {
    private MainActivity activity;
    private MainModel model;

    public MainPresenter(MainActivity activity, int userId) {
        this.activity = activity;
        model = new MainModel(userId);
    }

    public void findLobby() {
        try {
            model.findLobby();
        } catch (SocketException ex) {
            activity.showNoConnectionAlert();
        }
    }

    public void getFriends(User user) {
        try {
            model.getFriends(user);
        } catch (SocketException ex) {
            activity.showNoConnectionAlert();
        }
    }

    public ArrayList<Friend> getSubscribers() {
        try {
            return model.getSubscribers();
        } catch (SocketException ex) {
            activity.showNoConnectionAlert();
        }
        return new ArrayList<>();//return new list to not handle NullPointerException
    }

    public ArrayList<Friend> getStrangers(String query) {
        try {
            return model.getStrangers(query);
        } catch (SocketException ex) {
            activity.showNoConnectionAlert();
        }
        return new ArrayList<>();//return new list to not handle NullPointerException
    }

    public void addFriend(String name) {
        try {
            model.addFriend(name);
        } catch (SocketException ex) {
            activity.showNoConnectionAlert();
        }
    }

    public void acceptFriend(String name) {
        try {
            model.acceptFriend(name);
        } catch (SocketException ex) {
            activity.showNoConnectionAlert();
        }
    }

    public void denyFriend(String name) {
        try {
            model.denyFriend(name);
        } catch (SocketException ex) {
            activity.showNoConnectionAlert();
        }
    }

    public void removeFriend(String name) {
        try {
            model.removeFriend(name);
        } catch (SocketException ex) {
            activity.showNoConnectionAlert();
        }
    }

    public void goOffline() {
        MainModel.goOffline();
    }
}
