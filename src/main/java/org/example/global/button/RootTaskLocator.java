package org.example.global.button;

import org.example.event.RootTaskAppender;

public class RootTaskLocator {
    private static RootTaskAppender rootTaskAppender;

    public static void set(RootTaskAppender appender) {
        rootTaskAppender = appender;
    }

    public static void updateBaseY() {
        rootTaskAppender.updateBaseY();
    }
}
