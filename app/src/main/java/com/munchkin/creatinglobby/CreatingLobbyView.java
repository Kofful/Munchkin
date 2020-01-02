package com.munchkin.creatinglobby;

import android.view.View;

public interface CreatingLobbyView {
    void showText(String text);
    void createLobby(View view);
    void showNoConnectionAlert();
}
