package com.munchkin.lobby;

public interface LobbyView {
    void addUser(String nickname);
    void removeUser(String nickname);
    void startGame();
}
