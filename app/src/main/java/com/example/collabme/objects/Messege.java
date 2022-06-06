/**
 *
 * the message object  -object for the send and received messages
 * get and set for from and set the message destination and the user is which write it
 *
 */

package com.example.collabme.objects;


import java.text.DateFormat;

public class Messege {

    public static final int TYPE_MESSAGE = 0;
    public static final int TYPE_LOG = 1;
    public static final int TYPE_ACTION = 2;

    private int mType;
    private String mMessage;
    private String mUsername;
    private String mDate;
    private String mTime;

    private Messege() {}

    public int getType() {
        return mType;
    };

    public String getMessage() {
        return mMessage;
    };

    public String getUsername() {
        return mUsername;
    };

    public String getDate() {
        return mDate;
    }

    public String getTime() {
        return mTime;
    }

    public static class Builder {
        private final int mType;
        private String mUsername;
        private String mMessage;
        private String mTime;
        private String mDate;

        public Builder(int type) {
            mType = type;
        }

        public Builder username(String username) {
            mUsername = username;
            return this;
        }

        public Builder message(String message) {
            mMessage = message;
            return this;
        }

        public Builder date(String date) {
            mDate = date;
            return this;
        }
        public Builder time(String time) {
            mTime = time;
            return this;
        }


        public Messege build() {
            Messege message = new Messege();
            message.mType = mType;
            message.mUsername = mUsername;
            message.mMessage = mMessage;
            message.mDate = mDate;
            message.mTime = mTime;
            return message;
        }
    }
}