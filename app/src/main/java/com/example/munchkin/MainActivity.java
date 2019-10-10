package com.example.munchkin;

import android.content.Intent;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.os.Build;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public class MainActivity extends AppCompatActivity {
    public String name = "Someone";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void createService(View view) {
        Intent intent = new Intent(this, CreatingServiceActivity.class);
        intent.putExtra("name", name);
        startActivity(intent);
    }

    public void discoverServices(View view) {
        Intent intent = new Intent(this, FindingServiceActivity.class);
        intent.putExtra("name", name);
        startActivity(intent);
    }

    public void changeName(View view) {
        name = ((EditText)findViewById(R.id.editName)).getText().toString();
    }
}

