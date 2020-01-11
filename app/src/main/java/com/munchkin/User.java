package com.munchkin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    /**
     * used in deny, accept, remove queries
     */
    private static int staticAnswer;
    /**
     *
     * used to login and register
     */
    @SerializedName("answer")
    @Expose
    private int answer = 0;
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
    private ArrayList<Friend> friends;

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

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public ArrayList<Friend> getFriends() {
        return friends;
    }

    public void addFriend(Friend friend) {
        if (friends == null)
            friends = new ArrayList<>();
        friends.add(friend);
    }

    public static int getStaticAnswer() {
        return staticAnswer;
    }

    public static void setStaticAnswer(int answer) {
        staticAnswer = answer;
    }
}
