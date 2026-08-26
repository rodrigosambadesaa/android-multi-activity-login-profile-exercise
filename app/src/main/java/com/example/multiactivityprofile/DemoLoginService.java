package com.example.multiactivityprofile;

public final class DemoLoginService {
    static final String DEMO_NICK = "neo";
    static final String DEMO_PASSWORD = "sesamo";

    private DemoLoginService() {}

    public static boolean authenticate(String nick, String password) {
        return DEMO_NICK.equals(nick) && DEMO_PASSWORD.equals(password);
    }

    public static boolean authenticateWithDelay(String nick, String password, long delayMillis)
            throws InterruptedException {
        if (delayMillis > 0) {
            Thread.sleep(delayMillis);
        }
        return authenticate(nick, password);
    }
}
