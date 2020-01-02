package com.munchkin.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.widget.Toast;

import com.munchkin.Friend;
import com.munchkin.R;
import com.munchkin.User;
import com.munchkin.creatinglobby.CreatingLobbyActivity;
import com.munchkin.findinglobby.FindingServiceActivity;
import com.munchkin.main.friends.FriendsFragment;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements MainView {
    private MainPresenter presenter;
    private FriendsFragment friendsFragment;

    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            getSupportFragmentManager().popBackStack();
        }
        if (findViewById(R.id.fragment_friendlist) != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(0, R.anim.exit_to_right);
            fragmentTransaction.replace(R.id.friendListFrame, new ButtonFragment());
            fragmentTransaction.commit();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        User user = (User) getIntent().getSerializableExtra("user");
        presenter = new MainPresenter(this, user.getUserId());
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.friendListFrame, new ButtonFragment());
        fragmentTransaction.commit();
    }

    @Override
    public void createLobby(View view) {
        Intent intent = new Intent(this, CreatingLobbyActivity.class);
        intent.putExtra("user", getIntent().getSerializableExtra("user"));
        startActivity(intent);
    }

    @Override
    public void findLobby(View view) {
        presenter.findLobby();
    }

    public void playWithFriends(View view) {
        //TODO create activity and find friends' lobbies
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }


    @Override
    public void showFriendList(View view) {
        presenter.getFriends((User) getIntent().getSerializableExtra("user"));
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.hide_immediately);
        friendsFragment = new FriendsFragment();
        fragmentTransaction.add(R.id.friendListFrame, friendsFragment);
        fragmentTransaction.show(friendsFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void getFriendList(View view) {
        View btn_getFriends = findViewById(R.id.btn_getFriends);
        btn_getFriends.setBackgroundColor(Color.rgb(85, 85, 85));
        View btn_getSubscribers = findViewById(R.id.btn_getSubscribers);
        btn_getSubscribers.setBackgroundColor(Color.rgb(165, 165, 165));
        presenter.getFriends((User) getIntent().getSerializableExtra("user"));
        friendsFragment.showFriends(((User) getIntent().getSerializableExtra("user")).getFriends());
    }

    @Override
    public void getSubscriberList(View view) {
        View btn_getFriends = findViewById(R.id.btn_getFriends);
        btn_getFriends.setBackgroundColor(Color.rgb(165, 165, 165));
        View btn_getSubscribers = findViewById(R.id.btn_getSubscribers);
        btn_getSubscribers.setBackgroundColor(Color.rgb(85, 85, 85));

        friendsFragment.showSubscribers(
                presenter.getSubscribers()
        );
    }

    @Override
    public ArrayList<Friend> getStrangersList(int userId, String query) {
        return presenter.getStrangers(query);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        Log.i("DEBUGGING", "context");
        menu.add(0, 1, 0, "Remove");
    }

    @Override
    public void addFriend(String name) {
        presenter.addFriend(name);
    }

    @Override
    public void acceptFriend(String name) {
        presenter.acceptFriend(name);
    }

    @Override
    public void denyFriend(String name) {
        presenter.denyFriend(name);
    }

    @Override
    public void removeFriend(String name) {
        presenter.removeFriend(name);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.goOffline();
    }
}


