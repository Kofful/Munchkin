package com.munchkin.creatinglobby;

import android.util.Log;

import com.munchkin.User;
import com.munchkin.main.MainModel;

import java.net.SocketException;

public class CreatingLobbyPresenter {
    private CreatingLobbyActivity activity;
    private CreatingLobbyModel model;

    public CreatingLobbyPresenter(CreatingLobbyActivity activity) {
        this.activity = activity;
        model = new CreatingLobbyModel();
    }

    public void createLobby(int players, boolean friendsOnly) {
        try {
            model.createLobby(players, friendsOnly);
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
}
