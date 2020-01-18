package com.munchkin.settings;

import com.munchkin.main.MainModel;

import java.net.SocketException;

public class SettingsPresenter {
    SettingsActivity activity;
    public SettingsPresenter(SettingsActivity activity) {
        this.activity = activity;
    }

    public void changeAvatar(int avatarId) {
        try {
            MainModel.changeAvatar(avatarId);
        } catch(SocketException ex) {
            activity.showNoConnectionAlert();
        }
    }

    public int getAvatar() {
        try {
            return MainModel.getAvatar();
        } catch(SocketException ex) {
            activity.showNoConnectionAlert();
            return -1;
        }
    }
}
