package com.example;

public class Server {

    private Logger logger;
    private Preferences preferences;


    public Server(Logger logger, Preferences preferences) {
        this.logger = logger;
        this.preferences = preferences;
    }

    public void doSomething() {
        if (preferences.isLoggingEnabled()) {
            logger.log("did something");
            logger.saveLogs();
        }
    }
}
