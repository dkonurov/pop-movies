package com.example.dmitry.grades.options;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.dmitry.grades.LifeCycleActivity;
import com.example.dmitry.grades.MainActivity;
import com.example.dmitry.grades.R;

abstract public class OptionsActivity extends LifeCycleActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        ((TextView) findViewById(R.id.text)).setText(name());
        findViewById(R.id.main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OptionsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.repeat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OptionsActivity.this, repeatClass());
                intent.addFlags(getIntent().getFlags());
                startActivity(intent);
            }
        });
    }

    protected abstract String name();

    protected abstract Class<?> repeatClass();
}
