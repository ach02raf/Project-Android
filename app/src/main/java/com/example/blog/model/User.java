package com.example.blog.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    private String name;
    private String email ;
    private String phone ;
    private String password ;
    private String Role ;

    public User() {
    }

    protected User(Parcel in) {
        name = in.readString();
        email = in.readString();
        phone = in.readString();
        password = in.readString();
        Role = in.readString();
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(email);
        parcel.writeString(phone);
        parcel.writeString(name);
        parcel.writeString(password);
        parcel.writeString(Role);
    }
}
