package com.munchkin.lobby;

public interface LobbyView {
    void addUser(String nickname, int avatar);
    void removeUser(String nickname);
    void startGame();
    void showNoConnectionAlert();
    void exit();
}
