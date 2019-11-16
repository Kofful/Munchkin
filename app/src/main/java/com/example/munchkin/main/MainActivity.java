package com.example.munchkin.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.munchkin.R;
import com.example.munchkin.creatingservice.CreatingServiceActivity;
import com.example.munchkin.findingservice.FindingServiceActivity;


public class MainActivity extends AppCompatActivity implements  MainView{
    public String name = "Someone";
    private MainPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        presenter = new MainPresenter(this);
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

    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}


