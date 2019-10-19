package com.example.munchkin.creatingservice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.example.munchkin.R;

public class CreatingServiceActivity extends AppCompatActivity implements CreatingServiceView{
    private TextView textView;
    private CreatingServicePresenter presenter;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creating_service);
        textView = findViewById(R.id.infoView);
        presenter = new CreatingServicePresenter(this);
    }

    @Override
    public void showText(String text) {
        textView.setText(text);
    }




}
