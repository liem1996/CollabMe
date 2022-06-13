package com.example.collabme.objects;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collabme.R;
import com.example.collabme.model.ModelUsers;

import java.util.List;

/**
 *
 * the adapter for the chat activity - for the rycyle view for every item - recived and send messeges
 * send and received messages type
 * filling several felied including image and calling tha add offer function and upload image
 * putting time,name and sender for each message
 */


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private List<Messege> mMessages;
    private int[] mUsernameColors;

    public MessageAdapter(Context context, List<Messege> messages) {
        mMessages = messages;
        mUsernameColors = context.getResources().getIntArray(R.array.username_colors);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = -1;
        switch (viewType) {
            case Messege.TYPE_MESSAGE:
                layout = R.layout.item_chat_sentmessege;
                break;
            case Messege.TYPE_LOG:
                layout = R.layout.item_log;
                break;
            case Messege.TYPE_ACTION:
                layout = R.layout.item_action;
                break;
        }
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Messege message = mMessages.get(position);
        viewHolder.setMessage(message.getMessage());
        viewHolder.setUsername(message.getUsername());
        viewHolder.setDate(message.getDate());
        viewHolder.setTime(message.getTime());
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mMessages.get(position).getType();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mUsernameView;
        private TextView mMessageView;
        private TextView mDate, mTimeText;
        private CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            mUsernameView = itemView.findViewById(R.id.text_gchat_username_me);
            mMessageView = itemView.findViewById(R.id.text_gchat_message_me);
            mDate = itemView.findViewById(R.id.text_gchat_date_me);
            mTimeText = itemView.findViewById(R.id.text_gchat_timestamp_me);
            cardView = itemView.findViewById(R.id.card_gchat_message_me);
        }

        public void setUsername(String username) {
            if (null == mUsernameView) return;
            mUsernameView.setText(username);

            if(!username.equals(ModelUsers.instance3.getUser().getUsername())){
                cardView.setCardBackgroundColor(Color.rgb(130, 130, 130));
                mUsernameView.setTextColor(Color.rgb(130, 130, 130));
            } else {
                mUsernameView.setTextColor(Color.rgb(103, 58, 183));
                cardView.setCardBackgroundColor(Color.rgb(103, 58, 183));

            }

        }

        public void setMessage(String message) {
            if (null == mMessageView) return;
            mMessageView.setText(message);
        }

        public void setDate(String date) {
            if (null == mDate) return;
            mDate.setText(date);
        }

        public void setTime(String time) {
            if (null == mTimeText) return;
            mTimeText.setText(time);
        }


        private int getUsernameColor(String username) {
            int hash = 7;
            for (int i = 0, len = username.length(); i < len; i++) {
                hash = username.codePointAt(i) + (hash << 5) - hash;
            }
            int index = Math.abs(hash % mUsernameColors.length);
            return mUsernameColors[index];
        }
    }
}