package com.munchkin.main.friends;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.munchkin.Friend;
import com.munchkin.R;
import com.munchkin.User;
import com.munchkin.main.MainActivity;

import java.util.ArrayList;

public class FriendsFragment extends Fragment {
    FriendListAdapter adapter;
    ArrayList<Friend> friends;// friend list at start of searching
    boolean friendsAreShown;// showing friends(true) or subscribers(false)


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        friendsAreShown = true;
        View view = inflater.inflate(R.layout.fragment_friendlist, container, false);
        EditText editText = view.findViewById(R.id.edit_findFriend);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String name = charSequence.toString();
                adapter.clear();
                for (Friend friend : friends) {
                    if (friend.getNickname().indexOf(name) == 0) {
                        adapter.add(friend);
                    }
                }
                adapter.setActualFriends(adapter.getCount());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        editText.setOnEditorActionListener(
                new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH
                                || actionId == EditorInfo.IME_ACTION_DONE
                                || actionId == EditorInfo.IME_ACTION_NEXT) {
                            String text = v.getText().toString().replaceAll("\\s", "");
                            if (text.length() != 0 && friendsAreShown) {
                                ArrayList<Friend> strangers = ((MainActivity) getActivity())
                                        .getStrangersList(
                                                ((User) getActivity().getIntent().getSerializableExtra("user")).getUserId(),
                                                text
                                        );
                                if (strangers.size() > 0)
                                    adapter.add(new Friend());
                                adapter.addAll(strangers);
                            }
                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                            return true;
                        }
                        return false;
                    }
                });
        ListView lv_friends = view.findViewById(R.id.lv_friends);
        this.friends = new ArrayList<>();
        Thread t = new Thread() {
            @Override
            public void run() {
                friends = ((User) getActivity().getIntent().getSerializableExtra("user")).getFriends();
                try {
                    if (friends != null) {
                        Thread.sleep(350);
                        new Handler(getActivity().getMainLooper()).post(() -> {
                            adapter.addAll(friends);
                            adapter.setActualFriends(adapter.getCount());
                        });
                    }
                } catch (Exception ex) {
                    Log.i("DEBUGGING", ex.getMessage());
                }
            }
        };
        t.start();
        ArrayList<Friend> temp = friends;
        adapter = new FriendListAdapter(getActivity().getApplicationContext(), temp);
        lv_friends.setAdapter(adapter);
        lv_friends.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PopupMenu popupMenu = new PopupMenu(getContext(), view);
                if (!friendsAreShown) {
                    popupMenu.inflate(R.menu.popup_subscribers);
                } else if (i < adapter.getActualFriends()) {
                    popupMenu.inflate(R.menu.popup_friends);
                } else if (i > adapter.getActualFriends()) {
                    popupMenu.inflate(R.menu.popup_strangers);
                } else return;
                popupMenu
                        .setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                String nickname = ((TextView) view.findViewById(R.id.nickname)).getText().toString();
                                switch (item.getItemId()) {
                                    case R.id.removeFriend:
                                        adapter.remove(adapter.getItem(i));
                                        removeFriend(nickname);
                                        return true;
                                    case R.id.addFriend:
                                        addFriend(nickname);
                                        return true;
                                    case R.id.acceptFriend:
                                        adapter.remove(adapter.getItem(i));
                                        acceptFriend(nickname);
                                        return true;
                                    case R.id.denyFriend:
                                        adapter.remove(adapter.getItem(i));
                                        denyFriend(nickname);

                                    default:
                                        return false;
                                }
                            }
                        });
                popupMenu.show();
            }
        });
        return view;
    }

    public void showFriends(ArrayList<Friend> friends) {
        friendsAreShown = true;
        this.friends = new ArrayList<>();
        this.friends.addAll(friends);
        adapter.clear();
        adapter.addAll(this.friends);
        adapter.setActualFriends(adapter.getCount());
    }


    public void showSubscribers(ArrayList<Friend> subscribers) {
        TextView textView = getActivity().findViewById(R.id.edit_findFriend);
        textView.setText("");
        friendsAreShown = false;
        this.friends.clear();
        this.friends.addAll(subscribers);
        adapter.clear();
        adapter.addAll(this.friends);
        adapter.setActualFriends(adapter.getCount());
    }

    public void addFriend(String name) {
        ((MainActivity) getActivity()).addFriend(name);
    }

    public void acceptFriend(String name) {
        ((MainActivity) getActivity()).acceptFriend(name);
    }

    public void denyFriend(String name) {
        ((MainActivity) getActivity()).denyFriend(name);
    }

    public void removeFriend(String name) {
        ((MainActivity) getActivity()).removeFriend(name);
    }

}
