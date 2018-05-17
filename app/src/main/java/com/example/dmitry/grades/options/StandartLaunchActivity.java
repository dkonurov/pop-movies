package com.example.dmitry.grades.options;

public class StandartLaunchActivity extends OptionsActivity {
    @Override
    protected String name() {
        return "standart";
    }

    @Override
    protected Class<?> repeatClass() {
        return StandartLaunchActivity.class;
    }
}
