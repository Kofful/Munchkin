package com.munchkin.main.friends;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.renderscript.Sampler;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.munchkin.Friend;
import com.munchkin.R;
import com.munchkin.main.MainActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class FriendListAdapter extends ArrayAdapter<Friend> {
    private Context context;
    private ArrayList<Friend> values;//all items including strangers and splitters
    private int actualFriends;//friends without strangers
    LayoutInflater inflater;

    public FriendListAdapter(Context context, ArrayList<Friend> values) {
        super(context, R.layout.fragment_friend_info, values);
        this.context = context;
        this.values = values;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public Friend getItem(int i) {
        return values.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    public int getItemViewType(int position) {
        if (values.get(position).getNickname() == null) {
            return 1;
        }
        return 0;
    }

    @Override
    public @NonNull View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (this.getItemViewType(position) == 0) {
            Friend friend = values.get(position);
            View rowView = inflater.inflate(R.layout.fragment_friend_info, parent, false);
            TextView nicknameTextView = rowView.findViewById(R.id.nickname);
            nicknameTextView.setText(friend.getNickname());
            ImageView onlineTextView = rowView.findViewById(R.id.friend_online);
            onlineTextView.setBackground(ContextCompat.getDrawable(context, friend.isOnline() ? R.drawable.green_circle : R.drawable.red_circle));
            return rowView;
        } else {
            View view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            view.getLayoutParams().height = 30;
            view.setBackgroundColor(Color.rgb(55, 55, 55));
            return view;
        }
    }

    public int getActualFriends() {
        return actualFriends;
    }

    public void setActualFriends(int actualFriends) {
        this.actualFriends = actualFriends;
    }
}
