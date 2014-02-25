package com.example;

public class LoggerImpl implements Logger {

    @Override
    public void log(String text) {
        System.out.println(text);
    }

    @Override
    public void saveLogs() {
        // do nothing
    }
}
