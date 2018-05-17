package com.example.dmitry.grades.options;

public class SingleInstanceActivity extends OptionsActivity {
    @Override
    protected String name() {
        return "singleInstance";
    }

    @Override
    protected Class<?> repeatClass() {
        return SingleInstanceActivity.class;
    }
}
