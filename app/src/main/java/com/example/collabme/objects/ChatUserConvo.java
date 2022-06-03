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

    public void setTheChat(String theChat) {
        TheChat = theChat;
    }

    public HashMap<String, Object> toJson() {
        HashMap<String, Object> json = new HashMap<String, Object>();
        json.put("TheChatTextUsername",TheChat);
        json.put("Username",usernameConnect);
        json.put("theUserNameYouText",userNameYouWrite);
        return json;
    }
}
