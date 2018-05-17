package com.example.dmitry.grades.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class EventBusService extends Service {


    public static final String UPDATE_BUS = "UPDATE_BUS";

    public static Intent makeIntent(Context context) {
        return new Intent(context, EventBusService.class);
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
                    //                    EventBus.getDefault().post(new MessageEvent(i));
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

    public static class MessageEvent {
        private String mMessage;

        public MessageEvent(int message) {
            mMessage = String.valueOf(message);
        }

        public String getMessage() {
            return mMessage;
        }

        public void setMessage(String message) {
            mMessage = message;
        }
    }
}
