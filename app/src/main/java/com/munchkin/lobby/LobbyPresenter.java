package com.munchkin.lobby;

import android.util.Log;

import com.munchkin.main.MainModel;

import java.net.SocketException;

public class LobbyPresenter {
    LobbyActivity activity;
    public LobbyPresenter(LobbyActivity activity) {
        this.activity = activity;
        MainModel.handleLobbyConnections(this);
    }
    public void addUser(String nickname, int avatar) {
        activity.runOnUiThread(() -> activity.addUser(nickname, avatar));
    }

    public void removeUser(String nickname) {
        activity.runOnUiThread(() -> activity.removeUser(nickname));
    }

    public void startGame() {
        activity.runOnUiThread(() -> activity.startGame());
    }

    public void exit() {
        try {
            MainModel.leaveLobby();
        } catch (SocketException ex) {
            activity.showNoConnectionAlert();
        }
    }

    public void closeLobby() {
        try {
            MainModel.closeLobby();
        } catch(SocketException ex) {
            activity.showNoConnectionAlert();
        }
    }

}
