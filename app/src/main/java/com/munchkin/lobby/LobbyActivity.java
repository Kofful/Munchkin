package com.munchkin.lobby;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
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
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }

    public void exit(View view) {
        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);
        presenter = new LobbyPresenter(this);
        User user = (User)getIntent().getSerializableExtra("user");
        isCreator = getIntent().getBooleanExtra("iscreator", false);
        maxPlayers = getIntent().getIntExtra("maxplayers", 3);
        int avatarId = getIntent().getIntExtra("avatarid", 0);
        LinearLayout playerList = findViewById(R.id.linear_players);
        int current_players;
        playerList.addView(createLinearPlayer(user.getNickname(), avatarId));
        if(isCreator) {
            current_players = 1;
        }
        else {
            ArrayList<String> players = getIntent().getStringArrayListExtra("players");
            ArrayList<Integer> avatars = getIntent().getIntegerArrayListExtra("avatars");
            for(int i = 0; i < players.size(); i++) {
                playerList.addView(createLinearPlayer(players.get(i), avatars.get(i)));
            }
            current_players = players.size() + 1;
        }
        for(int i = current_players; i < maxPlayers; i++) {
            playerList.addView(createLinearPlayer(getString(R.string.waiting), 0));
        }
    }

    @Override
    protected void onDestroy() {
        Log.i("DEBUGGING", "onDestroy()");
        exit();
        super.onDestroy();
    }

//    private View createLinearPlayer(String nickname, int imageId) {
//        LayoutInflater factory = LayoutInflater.from(this);
//        LinearLayout linearLayout = (LinearLayout)factory.inflate(R.layout.fragment_lobby_user, null);
//        ImageView img = linearLayout.findViewById(R.id.player_avatar);
//        if(imageId == 0) {
//            img.setImageResource(R.drawable.three_dots);
//        } else {
//            img.setImageResource(getResources().getIdentifier(
//                    "monster" + imageId,
//                    "drawable",
//                    getPackageName()));
//        }
//        TextView text = linearLayout.findViewById(R.id.player_nickname);
//        text.setText(nickname);
//        return linearLayout;
//    }

    private View createLinearPlayer(String nickname, int imageId) {
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
        if(imageId == 0) {
            img.setImageResource(R.drawable.three_dots);
        } else {
            img.setImageResource(getResources().getIdentifier(
                    "monster" + imageId,
                    "drawable",
                    getPackageName()));
        }
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

    @Override
    public void addUser(String nickname, int avatar) {
        LinearLayout playerList = findViewById(R.id.linear_players);
        LinearLayout player;
        for(int i = 0; i < playerList.getChildCount(); i++) {
            player = (LinearLayout)playerList.getChildAt(i);
            ImageView imageView = (ImageView)player.getChildAt(0);
            TextView nicknameView = (TextView)player.getChildAt(1);
            String needed = getResources().getString(R.string.waiting);
            if(nicknameView.getText() == needed) {
                nicknameView.setText(nickname);
                imageView.setImageResource(getResources().getIdentifier(
                        "monster" + avatar,
                        "drawable",
                        getPackageName()));
                break;
            }
        }
    }

    @Override
    public void removeUser(String nickname) {
        LinearLayout playerList = findViewById(R.id.linear_players);
        LinearLayout player;
        for(int i = 0; i < playerList.getChildCount(); i++) {
            player = (LinearLayout)playerList.getChildAt(i);
            TextView nicknameView = (TextView)player.getChildAt(1);
            if(nicknameView.getText() != getResources().getString(R.string.waiting)) {
                playerList.removeViewAt(i);
                playerList.addView(createLinearPlayer(getResources().getString(R.string.waiting), 0));
            }
        }
    }

    @Override
    public void startGame() {

    }

    @Override
    public void exit() {
        if(isCreator) {
            presenter.closeLobby();
        } else {
            presenter.exit();
        }
    }


    @Override
    public void showNoConnectionAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.noConnection))
                .setCancelable(false)
                .setNegativeButton("ОК",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finishAffinity();
                                System.exit(0);
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
