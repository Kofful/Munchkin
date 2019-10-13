package com.example.munchkin.findingservice;

import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.munchkin.R;

public class FindingServiceActivity extends AppCompatActivity implements  FindingServiceView{
    TextView textView;
    LinearLayout servicesLayout;
    IntentFilter intentFilter;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finding_service);
        servicesLayout = findViewById(R.id.servicesLayout);
        textView = findViewById(R.id.findView);

        intentFilter = new IntentFilter();
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
    }

    @Override
    public void addService() {

    }

    @Override
    public void showText(String text) {
        textView.setText(text);
    }
}
