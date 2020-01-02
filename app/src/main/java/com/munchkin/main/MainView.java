package com.munchkin.main;

import android.view.View;

import com.munchkin.Friend;
import com.munchkin.User;

import java.util.ArrayList;

public interface MainView {
    void createLobby(View view);
    void findLobby(View view);
    void showFriendList(View view);
    void getFriendList(View view);
    void getSubscriberList(View view);
    void showMessage(String msg);
    void addFriend(String name);
    void acceptFriend(String name);
    void denyFriend(String name);
    void removeFriend(String name);
    ArrayList<Friend> getStrangersList(int userId, String query);
    void showNoConnectionAlert();
}
