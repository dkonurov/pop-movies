package com.example.dmitry.grades.services;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.dmitry.grades.R;

public class ServiceActivity extends AppCompatActivity {

    private BindService.BaseBinder mBinder;

    private boolean mBind;

    private ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = (BindService.BaseBinder) service;
            mBind = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBind = false;
        }
    };

    private TextView mRecieveResult;

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (LocalBroadcastService.UPDATE.equals(intent.getAction())) {
                mRecieveResult.setText(String.valueOf(intent.getIntExtra(LocalBroadcastService.UPDATE, 0)));
            }
        }
    };

    private TextView mMBusResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        findViewById(R.id.startService).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = BaseService.makeIntent(ServiceActivity.this, "we can start service");
                startService(intent);
            }
        });

        findViewById(R.id.startIntentService).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = BaseIntentService.makeIntent(ServiceActivity.this, "we can start intent service");
                startService(intent);
            }
        });


        final TextView result = findViewById(R.id.resultBind);

        findViewById(R.id.bindService).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBind) {
                    int increment = mBinder.getIncrement();
                    mBinder.setIncrement(increment);
                    result.setText(String.valueOf(increment));
                }
            }
        });

        mRecieveResult = findViewById(R.id.resultRecieve);

        findViewById(R.id.recieveService).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(LocalBroadcastService.makeIntent(ServiceActivity.this));
            }
        });

        mMBusResult = findViewById(R.id.resultBus);

        findViewById(R.id.busService).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(EventBusService.makeIntent(ServiceActivity.this));
            }
        });
    }

    //    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBusEvent(EventBusService.MessageEvent message) {
        mMBusResult.setText(message.getMessage());
    }

    @Override
    protected void onStart() {
        super.onStart();
        //        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = BindService.makeIntent(this);
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, new IntentFilter(LocalBroadcastService.UPDATE));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(mServiceConnection);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //        EventBus.getDefault().unregister(this);
    }
}
