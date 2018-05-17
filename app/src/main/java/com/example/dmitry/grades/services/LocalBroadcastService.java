package com.example.dmitry.grades.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

public class LocalBroadcastService extends Service {


    public static final String UPDATE = "UPDATE";

    public static Intent makeIntent(Context context) {
        return new Intent(context, LocalBroadcastService.class);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Intent base = new Intent(UPDATE);
                    base.putExtra(UPDATE, i);
                    LocalBroadcastManager.getInstance(getApplicationContext())
                            .sendBroadcast(base);
                }
                stopSelf();
            }
        });
        thread.start();
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
