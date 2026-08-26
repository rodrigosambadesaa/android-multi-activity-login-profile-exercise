package com.example.multiactivityprofile;

public final class ProfileValidator {
    private ProfileValidator() {}

    public static boolean isValid(String nick, String firstName, String lastName, String gender) {
        return !isBlank(nick)
                && !isBlank(firstName)
                && !isBlank(lastName)
                && (UserProfile.GENDER_MALE.equals(gender) || UserProfile.GENDER_FEMALE.equals(gender));
    }

    static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
