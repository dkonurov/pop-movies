package com.example.dmitry.grades;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class LifeCycleActivity extends AppCompatActivity {

    private static final String LIFE_CYCLE = "LifeCycle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LIFE_CYCLE,getMethod("onCreate"));
        super.onCreate(savedInstanceState);
    }

    private String getMethod(String name) {
        return getClass().getSimpleName() + " : " + name;
    }

    @Override
    protected void onStart() {
        Log.d(LIFE_CYCLE,getMethod("onStart"));
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d(LIFE_CYCLE,getMethod("onResume"));
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(LIFE_CYCLE,getMethod("onResume"));
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(LIFE_CYCLE,getMethod("onStop"));
        super.onStop();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d(LIFE_CYCLE,getMethod("onRestoreInstanceState"));
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(LIFE_CYCLE,getMethod("onSaveStateInstance"));
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.d(LIFE_CYCLE,getMethod("onNewIntent"));
        super.onNewIntent(intent);
    }

    @Override
    protected void onRestart() {
        Log.d(LIFE_CYCLE,getMethod("onRestart"));
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        Log.d(LIFE_CYCLE,getMethod("onDestroy"));
        super.onDestroy();
    }
}
