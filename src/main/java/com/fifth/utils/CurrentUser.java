package com.fifth.utils;

public class CurrentUser {
    private static ThreadLocal<String> currentUserId = new ThreadLocal<String>();
    private static ThreadLocal<String> currentUserName = new ThreadLocal<String>();

    public static String getCurrentUserId() {
        return currentUserId.get();
    }

    public static void setCurrentUserId(String userId) {
        currentUserId.set(userId);
    }

    public static String getCurrentUserName() {
        return currentUserName.get();
    }

    public static void setCurrentUserName(String userName) {
        currentUserName.set(userName);
    }
}
