package com.example.myapplication;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.services.MyService;
import com.example.myapplication.services.MyService2;

public class MainActivity extends AppCompatActivity {
    private Intent intent;
    private MyService2.InfoBinder infoBinder;
    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            infoBinder = (MyService2.InfoBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button startButton = findViewById(R.id.start);
        Button stopButton = findViewById(R.id.stop);
        Button startBindingButton = findViewById(R.id.bindingStart);
        Button stopBindingButton = findViewById(R.id.bindingStop);
        Button getCountButton = findViewById(R.id.getCount);
        startButton.setOnClickListener(v -> {
            intent = new Intent(MainActivity.this, MyService.class);
            MainActivity.this.startService(intent);
        });
        stopButton.setOnClickListener(v -> MainActivity.this.stopService(intent));
        startBindingButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MyService2.class);
            bindService(intent,serviceConnection,BIND_AUTO_CREATE);
        });
        stopBindingButton.setOnClickListener(v -> unbindService(serviceConnection));
        getCountButton.setOnClickListener(v -> Toast.makeText(MainActivity.this, "当前 service 的 count：" + infoBinder.getCount(), Toast.LENGTH_LONG).show());
    }
}