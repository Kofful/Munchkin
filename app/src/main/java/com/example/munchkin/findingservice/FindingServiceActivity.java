package com.example.munchkin.findingservice;

import android.app.Activity;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.munchkin.R;

public class FindingServiceActivity extends Activity implements  FindingServiceView{
    TextView textView;
    LinearLayout servicesLayout;
    IntentFilter intentFilter;
    FindingServicePresenter presenter;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        presenter.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finding_service);
        servicesLayout = findViewById(R.id.servicesLayout);
        textView = findViewById(R.id.findView);
        presenter = new FindingServicePresenter(this);

        intentFilter = new IntentFilter();
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
    }

    @Override
    public void addService(String value) {
        Button btn = new Button(this);
        btn.setText(value);
        btn.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        servicesLayout.addView(btn);
    }

    @Override
    public void showText(String text) {
        textView.setText(text);
    }
}
