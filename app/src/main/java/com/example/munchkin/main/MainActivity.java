package com.example.munchkin.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.munchkin.R;
import com.example.munchkin.creatingservice.CreatingServiceActivity;
import com.example.munchkin.findingservice.FindingServiceActivity;


public class MainActivity extends AppCompatActivity implements  MainView{
    private MainPresenter presenter;
    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            getSupportFragmentManager().popBackStack();
        }
        //TODO check onBackPressed()
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        presenter = new MainPresenter(this);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.friendListFrame, new ButtonFragment());
        fragmentTransaction.commit();
        //TODO ?TCP/IP or UDP?
    }

    public void createService(View view) {
        Intent intent = new Intent(this, CreatingServiceActivity.class);
        startActivity(intent);
    }

    public void discoverServices(View view) {
        Intent intent = new Intent(this, FindingServiceActivity.class);
        startActivity(intent);
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    public void showFriendList(View view) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right);
        fragmentTransaction.replace(R.id.friendListFrame, new FriendsFragment());
        fragmentTransaction.commit();
    }
}


