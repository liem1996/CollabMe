package com.example.collabme.objects;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collabme.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 *
 * the adapter for the chat activity - for the rycyle view for every item - recived and send messeges
 * send and received messages type
 * filling several felied including image and calling tha add offer function and upload image
 * putting time,name and sender for each message
 */


public class MessageAdapter extends RecyclerView.Adapter {

    private static final int TYPE_MESSAGE_SENT = 0;
    private static final int TYPE_MESSAGE_RECEIVED = 1;
    private static final int TYPE_IMAGE_SENT = 2;
    private static final int TYPE_IMAGE_RECEIVED = 3;

    private LayoutInflater inflater;
    private List<JSONObject> messages = new ArrayList<>();
    private List<Messege> ourMessages = new ArrayList<>();

    String currentUser;
    String currentTime;
    String toUser;
    String currentMessage;

    public MessageAdapter (LayoutInflater inflater) {
        this.inflater = inflater;
    }



    private class SentMessageHolder extends RecyclerView.ViewHolder {

        TextView messageTxt;
        TextView timeText;
        TextView date;


        public SentMessageHolder(@NonNull View itemView) {
            super(itemView);
            timeText = (TextView) itemView.findViewById(R.id.text_gchat_timestamp_me);
            messageTxt = itemView.findViewById(R.id.text_gchat_message_me);
            date = itemView.findViewById(R.id.text_gchat_date_me);
        }
    }

    private class SentImageHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public SentImageHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
        }
    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {

        TextView nameTxt, messageTxt, timeText;

        public ReceivedMessageHolder(@NonNull View itemView) {
            super(itemView);

            nameTxt = itemView.findViewById(R.id.text_gchat_user_other);
            messageTxt = itemView.findViewById(R.id.text_gchat_message_other);
            timeText = (TextView) itemView.findViewById(R.id.text_gchat_timestamp_me);

        }
    }

    private class ReceivedImageHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView nameTxt, timeText;

        public ReceivedImageHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView2);
            nameTxt = itemView.findViewById(R.id.nameTxt2);

        }
    }

    @Override
    public int getItemViewType(int position) {

        JSONObject message = messages.get(position);

        try {
            if (message.getBoolean("isSent")) {

                if (message.has("message"))
                    return TYPE_MESSAGE_SENT;
                else
                    return TYPE_IMAGE_SENT;

            } else {

                if (message.has("message"))
                    return TYPE_MESSAGE_RECEIVED;
                else
                    return TYPE_IMAGE_RECEIVED;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return -1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;

        switch (viewType) {
            case TYPE_MESSAGE_SENT:
                view = inflater.inflate(R.layout.item_chat_sentmessege, parent, false);
                return new SentMessageHolder(view);
            case TYPE_MESSAGE_RECEIVED:

                view = inflater.inflate(R.layout.item_chat_recivemessege, parent, false);
                return new ReceivedMessageHolder(view);

            case TYPE_IMAGE_SENT:

                view = inflater.inflate(R.layout.item_chat_sendimage, parent, false);
                return new SentImageHolder(view);

            case TYPE_IMAGE_RECEIVED:

                view = inflater.inflate(R.layout.item_chat_recivemessege, parent, false);
                return new ReceivedImageHolder(view);

        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        JSONObject message = messages.get(position);


        try {
            currentUser = message.getString("name");
            currentTime = message.getString("currentTime");
            toUser = message.getString("toUser");
            currentMessage = message.getString("message");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Messege ourMessage = new Messege(currentUser,toUser,currentMessage,currentTime);
        ourMessages.add(ourMessage);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);

        DateFormat date = new SimpleDateFormat("HH:mm a");
        date.setTimeZone(TimeZone.getTimeZone("GMT+3:00"));
        String localTime = date.format(c);

        try {
            if (message.getBoolean("isSent")) {
                if (message.has("message")) {


                    SentMessageHolder messageHolder = (SentMessageHolder) holder;

                    messageHolder.messageTxt.setText(message.getString("message"));
                    messageHolder.date.setText(formattedDate);
                    messageHolder.timeText.setText(message.getString("currentTime"));


                } else {

                    SentImageHolder imageHolder = (SentImageHolder) holder;
                    Bitmap bitmap = getBitmapFromString(message.getString("image"));
                    imageHolder.imageView.setImageBitmap(bitmap);


                }

            } else {

                if (message.has("message")) {

                    ReceivedMessageHolder messageHolder = (ReceivedMessageHolder) holder;
                    messageHolder.nameTxt.setText(message.getString("name"));
                    messageHolder.messageTxt.setText(message.getString("message"));



                } else {

                    ReceivedImageHolder imageHolder = (ReceivedImageHolder) holder;
                    imageHolder.nameTxt.setText(message.getString("name"));
                    Bitmap bitmap = getBitmapFromString(message.getString("image"));
                    imageHolder.imageView.setImageBitmap(bitmap);

                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private Bitmap getBitmapFromString(String image) {

        byte[] bytes = Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void addItem (JSONObject jsonObject) {
        messages.add(jsonObject);
        notifyDataSetChanged();
    }

}