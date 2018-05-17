package com.example.dmitry.grades.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Toast;

public class BaseService extends Service {


    public static final String EXTRA_STRING_NAME = "EXTRA_STRING_NAME";

    public static Intent makeIntent(Context context, String text) {
        Intent intent = new Intent(context, BaseService.class);
        intent.putExtra(EXTRA_STRING_NAME, text);
        return intent;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final String text = intent.getStringExtra(EXTRA_STRING_NAME);
        if (!TextUtils.isEmpty(text)) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                        }
                    });
                    stopSelf();
                }
            });
            thread.start();
        } else {
            stopSelf();
        }
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
