package com.munchkin.settings;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.munchkin.R;

import java.util.ArrayList;

public class AvatarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar);
    }

    public void changeAvatar(View view) {
        int newAvatarId = Integer.parseInt(view.getTag().toString());
        Intent intent = new Intent();
        intent.putExtra("avatarid", newAvatarId);
        setResult(RESULT_OK, intent);
        finish();
        overridePendingTransition(0, 0);
    }
}
