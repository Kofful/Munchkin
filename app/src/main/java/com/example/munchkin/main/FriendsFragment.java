package com.example.munchkin.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;

import com.example.munchkin.R;
import com.example.munchkin.User;

import java.util.ArrayList;

public class FriendsFragment extends Fragment {
    ListView lv_friends;
    ArrayList<User> friends;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        EditText editText = getActivity().findViewById(R.id.edit_findFriend);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String name = charSequence.toString();
                //TODO request to server
                ArrayList<User> newFriends = new ArrayList<>();
                for(User friend: friends) {
                    if(friend.getNickname().indexOf(name) == 0) {
                        newFriends.add(friend);
                    }
                }
                friends = newFriends;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        lv_friends = getActivity().findViewById(R.id.lv_friends);
        User user = (User)getActivity().getIntent().getSerializableExtra("user");
        friends = user.getFriends();
        ArrayAdapter<User> adapter = new ArrayAdapter<>(getActivity(),R.layout.fragment_friend_info, friends);
        lv_friends.setAdapter(adapter);

        return inflater.inflate(R.layout.fragment_friendlist, container, false);
    }
}
