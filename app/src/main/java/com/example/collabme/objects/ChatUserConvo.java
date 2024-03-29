package com.example.collabme.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class ChatUserConvo {

    @SerializedName("Username")
    @Expose
    private String usernameConnect;
    @SerializedName("theUserNameYouText")
    @Expose
    private String userNameYouWrite;
    @SerializedName("TheChatTextUsername")
    @Expose
    private String TheChat;
    @SerializedName("theOrder")
    @Expose
    private int theorder;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("time")
    @Expose
    private String time;

    /**
     *
     * @return
     * The usernameConnect
     */
    public String getUsernameConnect() {
        return usernameConnect;
    }

    public void setUsernameConnect(String usernameConnect) {
        this.usernameConnect = usernameConnect;
    }

    /**
     *
     * @return
     * The userNameYouWrite
     */
    public String getUserNameYouWrite() {
        return userNameYouWrite;
    }

    public void setUserNameYouWrite(String userNameYouWrite) {
        this.userNameYouWrite = userNameYouWrite;
    }

    /**
     *
     * @return
     * The TheChat
     */
    public String getTheChat() {
        return TheChat;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTheChat(String theChat) {
        TheChat = theChat;
    }

    public int getTheorder() {
        return theorder;
    }

    public void setTheorder(int theorder) {
        this.theorder = theorder;
    }

    public HashMap<String, Object> toJson() {
        HashMap<String, Object> json = new HashMap<String, Object>();
        json.put("TheChatTextUsername",TheChat);
        json.put("Username",usernameConnect);
        json.put("theUserNameYouText",userNameYouWrite);
        json.put("theOrder",theorder);
        json.put("date",date);
        json.put("time",time);
        return json;
    }


}
