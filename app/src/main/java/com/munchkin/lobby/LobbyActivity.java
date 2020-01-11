package com.munchkin.lobby;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.munchkin.R;
import com.munchkin.User;

import java.util.ArrayList;

public class LobbyActivity extends AppCompatActivity implements LobbyView{
    boolean isCreator;
    int maxPlayers;
    //boolean friendsOnly;
    LobbyPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);
        presenter = new LobbyPresenter(this);
        User user = (User)getIntent().getSerializableExtra("user");
        isCreator = getIntent().getBooleanExtra("iscreator", false);
        maxPlayers = getIntent().getIntExtra("maxplayers", 3);
        LinearLayout playerList = findViewById(R.id.linear_players);
        int current_players;
        playerList.addView(createLinearPlayer(user.getNickname()));
        if(isCreator) {
            current_players = 1;
            //TODO set creator and guest image
        }
        else {
            ArrayList<String> players = getIntent().getStringArrayListExtra("players");
            for(int i = 0; i < players.size(); i++) {
                playerList.addView(createLinearPlayer(players.get(i)));
            }
            current_players = players.size() + 1;
        }
        for(int i = current_players; i < maxPlayers; i++) {
            playerList.addView(createLinearPlayer(getString(R.string.waiting)));
        }
        //friendsOnly = getIntent().getBooleanExtra("friendsonly", false);
    }
    private View createLinearPlayer(String nickname) {
        LinearLayout.LayoutParams lp_linear = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        lp_linear.weight = 1;
        LinearLayout.LayoutParams lp_image = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
        LinearLayout.LayoutParams lp_text = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
        lp_image.weight = 5;
        lp_text.weight = 2;
        LinearLayout linear = new LinearLayout(this);
        linear.setOrientation(LinearLayout.VERTICAL);
        linear.setLayoutParams(lp_linear);
        ImageView img = new ImageView(this);
        img.setImageResource(R.drawable.three_dots);
        img.setLayoutParams(lp_image);
        linear.addView(img);
        TextView text = new TextView(this);
        text.setText(nickname);
        text.setGravity(Gravity.CENTER);
        text.setLayoutParams(lp_text);
        text.setTextSize(20);
        linear.addView(text);
        return linear;
    }
}
