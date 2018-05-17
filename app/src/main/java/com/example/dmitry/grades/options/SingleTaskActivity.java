package com.example.dmitry.grades.options;

public class SingleTaskActivity extends OptionsActivity {
    @Override
    protected String name() {
        return "single task";
    }

    @Override
    protected Class<?> repeatClass() {
        return SingleTaskActivity.class;
    }
}
