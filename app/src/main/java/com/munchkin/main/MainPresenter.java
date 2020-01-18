package com.munchkin.main;

import com.munchkin.Friend;
import com.munchkin.User;
import com.munchkin.responses.LobbyAndUsers;

import java.net.SocketException;
import java.util.ArrayList;
public class MainPresenter {
    private MainActivity activity;
    private MainModel model;

    public MainPresenter(MainActivity activity, int userId) {
        this.activity = activity;
        model = new MainModel(userId);
    }

    public LobbyAndUsers findLobby(String nickname) {
        LobbyAndUsers result;
        try {
            result = model.findLobby(nickname);
        } catch (SocketException ex) {
            result = new LobbyAndUsers();
            result.setLobbyId(-1);
            activity.showNoConnectionAlert();
        }
        return result;
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


    public int getAvatar() {
        int avatarId = 0;
        try {
            avatarId = MainModel.getAvatar();
        } catch(SocketException ex) {
            activity.showNoConnectionAlert();
        }
        return avatarId;
    }

    public void goOffline() {
        MainModel.goOffline();
    }
}
