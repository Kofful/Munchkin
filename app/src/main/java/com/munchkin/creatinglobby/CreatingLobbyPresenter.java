package com.munchkin.creatinglobby;

import com.munchkin.User;

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
        } catch (
                SocketException ex) {
            activity.showNoConnectionAlert();
        }
        //TODO go to lobby
    }
}
