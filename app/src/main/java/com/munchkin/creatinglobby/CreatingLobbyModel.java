package com.munchkin.creatinglobby;

import com.munchkin.main.MainModel;

import java.net.SocketException;

public class CreatingLobbyModel {

    public void createLobby(int players, boolean friendsOnly) throws SocketException {
        MainModel.createLobby(players, friendsOnly);
    }
}

