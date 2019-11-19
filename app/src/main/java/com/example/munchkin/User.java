package com.example.munchkin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {
    @SerializedName("answer")
    @Expose
    private int answer;
    @SerializedName("userId")
    @Expose
    private int userId;
    @SerializedName("nickname")
    @Expose
    private String nickname;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("passwordHash")
    @Expose
    private String password;

    public User(String email, String password, String nickname) {
        setNickname(nickname);
        setEmail(email);
        setPassword(password);
        setUserId(0);
    }

    public User(String email, String password) {
        setEmail(email);
        setPassword(password);
        setUserId(0);
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String toJSON() {
        String sb = "";
        sb+="{";
        sb+="\"userId\":\"" + getUserId() +"\",";
        sb+="\"email\":\"" + getEmail() +"\",";
        sb+="\"nickname\":\"" + getNickname() +"\",";
        sb+="\"passwordHash\":\"" + getUserId() +"\"}";
        return sb;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }
}
