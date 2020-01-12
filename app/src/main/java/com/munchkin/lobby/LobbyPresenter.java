package com.munchkin.lobby;

import com.munchkin.main.MainModel;

public class LobbyPresenter {
    LobbyActivity activity;
    public LobbyPresenter(LobbyActivity activity) {
        this.activity = activity;
        MainModel.handleLobbyConnections(this);
    }
    public void addUser(String nickname) {
        activity.addUser(nickname);
    }

    public void removeUser(String nickname) {
        activity.removeUser(nickname);
    }

    public void startGame() {
        activity.startGame();
    }

}
