package com.example.multiactivityprofile;

import android.content.Intent;
import android.os.Build;

import androidx.annotation.Nullable;

public final class NavigationContract {
    public static final String EXTRA_USER = "com.example.multiactivityprofile.extra.USER";
    public static final String EXTRA_MODE = "com.example.multiactivityprofile.extra.MODE";
    public static final String MODE_REGISTER = "register";
    public static final String MODE_EDIT = "edit";

    private NavigationContract() {}

    @Nullable
    @SuppressWarnings("deprecation")
    public static UserProfile readUser(@Nullable Intent intent) {
        if (intent == null) {
            return null;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return intent.getParcelableExtra(EXTRA_USER, UserProfile.class);
        }
        return intent.getParcelableExtra(EXTRA_USER);
    }
}
