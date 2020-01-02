package com.munchkin.creatinglobby;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.munchkin.R;
import com.munchkin.User;
import com.munchkin.main.MainModel;

public class CreatingLobbyActivity extends Activity implements CreatingLobbyView {
    private CreatingLobbyPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creating_lobby);
        presenter = new CreatingLobbyPresenter(this);
    }

    @Override
    public void showText(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void createLobby(View view) {
        TextView playersMax = findViewById(R.id.textView_playersMax);
        int players = Integer.parseInt(playersMax.getText().toString());
        boolean friendsOnly = ((Switch)findViewById(R.id.checkbox_forFriendsOnly)).isChecked();
        presenter.createLobby(players, friendsOnly);
    }

    public void incrementPlayersCount(View view) {
        TextView playersMax = findViewById(R.id.textView_playersMax);
        int players = Integer.parseInt(playersMax.getText().toString());
        //TODO change buttons' opacity
        if(players < 4) {
            playersMax.setText(String.valueOf(players + 1));
        }
    }

    public void decrementPlayersCount(View view) {
        TextView playersMax = findViewById(R.id.textView_playersMax);
        int players = Integer.parseInt(playersMax.getText().toString());
        if(players > 2) {
            playersMax.setText(String.valueOf(players - 1));
        }
    }

    @Override
    public void showNoConnectionAlert() {
        //TODO show alert
    }
}
