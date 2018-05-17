package com.example.dmitry.grades.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class BindService extends Service {

    BaseBinder mBaseBinder = new BaseBinder();

    static Intent makeIntent(Context context) {
        return new Intent(context, BindService.class);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBaseBinder;
    }

    public static class BaseBinder extends Binder {

        private int mIncrement;

        public int getIncrement() {
            return ++mIncrement;
        }

        public void setIncrement(int increment) {
            mIncrement = increment;
        }
    }
}
