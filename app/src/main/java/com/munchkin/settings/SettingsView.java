package com.munchkin.settings;

import android.view.View;

public interface SettingsView {
    void showAvatars(View view);
    void changeLanguage(int index);
    void showNoConnectionAlert();
}
