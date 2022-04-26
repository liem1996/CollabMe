package com.example.collabme.actionsOnOffers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.collabme.R;

import java.util.List;

public class SpinnerAdapter extends BaseAdapter {

    private Context context;
    private List<String> list;

    public SpinnerAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        int newSize = 0;
        for(int i = 0 ; i<list.size() ; i++){
            if(list.get(i)!=null){
                newSize++;
            }
        }
        return newSize;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rootView = LayoutInflater.from(context)
                .inflate(R.layout.my_spinner_style, viewGroup, false);

        TextView txtName = rootView.findViewById(R.id.nameSpinner_item);
        //ImageView image = rootView.findViewById(R.id.imageSpinner_item);
        if(list.get(i)!=null)
            txtName.setText(list.get(i));
       // image.setImageResource(R.drawable.com_facebook_auth_dialog_background);

        return rootView;
    }
}
