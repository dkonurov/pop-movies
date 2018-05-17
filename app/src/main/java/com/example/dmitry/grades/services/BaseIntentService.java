package com.example.dmitry.grades.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import static com.example.dmitry.grades.services.BaseService.EXTRA_STRING_NAME;

public class BaseIntentService extends IntentService {
    public BaseIntentService() {
        super("intent service");
    }

    public static Intent makeIntent(Context context, String text) {
        Intent intent = new Intent(context, BaseIntentService.class);
        intent.putExtra(EXTRA_STRING_NAME, text);
        return intent;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String text = intent.getStringExtra(EXTRA_STRING_NAME);
            if (!TextUtils.isEmpty(text)) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d("Intent Service", text);
            }
        }
    }
}
