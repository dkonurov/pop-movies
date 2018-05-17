package com.example.dmitry.grades.options;

public class SingleTopActivity extends OptionsActivity {
    @Override
    protected String name() {
        return "single top";
    }

    @Override
    protected Class<?> repeatClass() {
        return SingleTopActivity.class;
    }
}
