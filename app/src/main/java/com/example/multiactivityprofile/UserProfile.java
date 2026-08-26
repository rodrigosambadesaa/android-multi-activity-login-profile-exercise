package com.example.multiactivityprofile;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Objects;

public final class UserProfile implements Parcelable {
    public static final String GENDER_MALE = "h";
    public static final String GENDER_FEMALE = "m";

    private final String nick;
    private final String firstName;
    private final String lastName;
    private final String gender;

    public UserProfile(String nick, String firstName, String lastName, String gender) {
        this.nick = Objects.requireNonNull(nick);
        this.firstName = Objects.requireNonNull(firstName);
        this.lastName = Objects.requireNonNull(lastName);
        this.gender = Objects.requireNonNull(gender);
    }

    private UserProfile(Parcel source) {
        nick = Objects.requireNonNull(source.readString());
        firstName = Objects.requireNonNull(source.readString());
        lastName = Objects.requireNonNull(source.readString());
        gender = Objects.requireNonNull(source.readString());
    }

    public String getNick() { return nick; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getGender() { return gender; }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(nick);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(gender);
    }

    public static final Creator<UserProfile> CREATOR = new Creator<>() {
        @Override
        public UserProfile createFromParcel(Parcel source) { return new UserProfile(source); }

        @Override
        public UserProfile[] newArray(int size) { return new UserProfile[size]; }
    };
}
