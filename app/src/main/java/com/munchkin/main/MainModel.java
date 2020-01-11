package com.munchkin.main;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import com.munchkin.Friend;
import com.munchkin.User;
import com.munchkin.lobby.LobbyActivity;
import com.munchkin.responses.LobbyAndUsers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class MainModel {
    static final int GET_FRIENDS = 1001;
    static final int GET_SUBSCRIBERS = 1002;
    static final int GET_STRANGERS = 1003;
    static final int SEND_FRIEND_REQUEST = 1004;
    static final int ACCEPT_FRIEND = 1005;
    static final int DENY_FRIEND = 1006;
    static final int REMOVE_FRIEND = 1007;
    static final int CREATE_LOBBY = 1008;
    static final int FIND_LOBBY = 1009;
    static final int GET_FRIEND_LOBBY = 1010;
    private static ObjectOutputStream oos;
    private static ObjectInputStream ois;
    private static Socket socket;

    public MainModel(int userId) {
        try {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        socket = new Socket("192.168.1.106", 8080);
                        oos = new ObjectOutputStream(socket.getOutputStream());
                        ois = new ObjectInputStream(socket.getInputStream());
                        oos.writeInt(userId);
                        oos.flush();
                    } catch (Exception ex) {
                        Log.i("DEBUGGING", ex.getClass().toString());
                    }
                }
            };
            thread.start();
            thread.join();
        } catch (Exception ex) {
            Log.i("DEBUGGING", ex.getClass().toString());
        }
    }


    public void getFriends(User user) throws SocketException {
        try {
            if (user.getFriends() != null) {
                user.getFriends().clear();
            }
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        oos.writeInt(GET_FRIENDS);
                        oos.flush();
                        int friendsCount = ois.readInt();
                        while (friendsCount > 0) {
                            Friend friend = new Friend();
                            friend.setNickname((String) ois.readObject());
                            friend.setOnline(ois.readBoolean());
                            user.addFriend(friend);
                            friendsCount--;
                        }
                        User.setStaticAnswer(1);
                    } catch (SocketException ex) {
                        User.setStaticAnswer(-1);//means internet problems
                    } catch (Exception ex) {
                        User.setStaticAnswer(0);//means some Exception
                        Log.i("DEBUGGING", ex.getClass().toString());
                    }
                }
            };
            thread.start();
            thread.join();
            if(User.getStaticAnswer() == -1 ) {
                throw new SocketException("MUNCHKIN: Socket Exception.");
            }
        } catch (SocketException ex) {
            throw ex;
        } catch (Exception ex) {
            Log.i("DEBUGGING", ex.getClass().toString());
        }
    }

    public ArrayList<Friend> getSubscribers() throws SocketException {
        ArrayList<Friend> result = new ArrayList<>();
        try {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        oos.writeInt(GET_SUBSCRIBERS);
                        oos.flush();
                        int friendsCount = ois.readInt();
                        while (friendsCount > 0) {
                            Friend friend = new Friend();
                            friend.setNickname((String) ois.readObject());
                            result.add(friend);
                            friendsCount--;
                        }
                        User.setStaticAnswer(1);
                    } catch (Exception ex) {
                        User.setStaticAnswer(-1);
                        Log.i("DEBUGGING", ex.getClass().toString());
                    }
                }
            };
            thread.start();
            thread.join();
            if(User.getStaticAnswer() == -1 ) {
                throw new SocketException("MUNCHKIN: Socket Exception.");
            }
        } catch (SocketException ex) {
            throw ex;
        } catch (Exception ex) {
            Log.i("DEBUGGING", ex.getClass().toString());
        }
        return result;
    }

    public ArrayList<Friend> getStrangers(String query) throws SocketException {
        ArrayList<Friend> result = new ArrayList<>();
        try {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        oos.writeInt(GET_STRANGERS);
                        oos.writeObject(query);
                        oos.flush();
                        int friendsCount = ois.readInt();
                        while (friendsCount > 0) {
                            Friend friend = new Friend();
                            friend.setNickname((String) ois.readObject());
                            friend.setOnline(ois.readBoolean());
                            result.add(friend);
                            friendsCount--;
                        }
                        User.setStaticAnswer(1);
                    } catch (Exception ex) {
                        User.setStaticAnswer(-1);
                        Log.i("DEBUGGING", ex.getClass().toString());
                    }
                }
            };
            thread.start();
            thread.join();
            if(User.getStaticAnswer() == -1 ) {
                throw new SocketException("MUNCHKIN: Socket Exception.");
            }
        } catch (Exception ex) {
            Log.i("DEBUGGING", ex.getClass().toString());
        }
        return result;
    }

    public void addFriend(String name) throws SocketException {
        try {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        oos.writeInt(SEND_FRIEND_REQUEST);
                        oos.writeObject(name);
                        oos.flush();
                        User.setStaticAnswer(1);
                    } catch (Exception ex) {
                        User.setStaticAnswer(-1);
                        Log.i("DEBUGGING", ex.getClass().toString());
                    }
                }
            };
            thread.start();
            thread.join();
            if(User.getStaticAnswer() == -1 ) {
                throw new SocketException("MUNCHKIN: Socket Exception.");
            }
        } catch(SocketException ex) {
            throw ex;
        } catch(Exception ex) {
            Log.i("DEBUGGING", ex.getClass().toString());
        }
    }

    public void acceptFriend(String name) throws SocketException {
        try {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        oos.writeInt(ACCEPT_FRIEND);
                        oos.writeObject(name);
                        oos.flush();
                        User.setStaticAnswer(1);
                    } catch (Exception ex) {
                        User.setStaticAnswer(-1);
                        Log.i("DEBUGGING", ex.getClass().toString());
                    }
                }
            };
            thread.start();thread.join();
            if(User.getStaticAnswer() == -1 ) {
                throw new SocketException("MUNCHKIN: Socket Exception.");
            }
        } catch(SocketException ex) {
            throw ex;
        } catch(Exception ex) {
            Log.i("DEBUGGING", ex.getClass().toString());
        }
    }

    public void denyFriend(String name) throws SocketException {
        try {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        oos.writeInt(DENY_FRIEND);
                        oos.writeObject(name);
                        oos.flush();
                        User.setStaticAnswer(1);
                    } catch (Exception ex) {
                        User.setStaticAnswer(-1);
                        Log.i("DEBUGGING", ex.getClass().toString());
                    }
                }
            };
            thread.start();thread.join();
            if(User.getStaticAnswer() == -1 ) {
                throw new SocketException("MUNCHKIN: Socket Exception.");
            }
        } catch(SocketException ex) {
            throw ex;
        } catch(Exception ex) {
            Log.i("DEBUGGING", ex.getClass().toString());
        }
    }


    public void removeFriend(String name) throws SocketException {
        try {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        oos.writeInt(REMOVE_FRIEND);
                        oos.writeObject(name);
                        oos.flush();
                        User.setStaticAnswer(1);
                    } catch (Exception ex) {
                        User.setStaticAnswer(-1);
                        Log.i("DEBUGGING", ex.getClass().toString());
                    }
                }
            };
            thread.start();thread.join();
            if(User.getStaticAnswer() == -1 ) {
                throw new SocketException("MUNCHKIN: Socket Exception.");
            }
        } catch(SocketException ex) {
            throw ex;
        } catch(Exception ex) {
            Log.i("DEBUGGING", ex.getClass().toString());
        }
    }

    public LobbyAndUsers findLobby(String nickname) throws SocketException {
        LobbyAndUsers result = new LobbyAndUsers();
        try {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        oos.writeInt(FIND_LOBBY);
                        oos.writeObject(nickname);
                        oos.flush();
                        result.setLobbyId(ois.readInt());
                        if(result.getLobbyId() != 0) {
                            int maxPlayers = ois.readInt();
                            result.setMaxPlayers(maxPlayers);
                            int count = ois.readInt();
                            for (int i = 0; i < count; i++) {
                                result.addUser((String) ois.readObject());
                            }
                        }
                    } catch (SocketException ex) {
                        result.setLobbyId(-1);
                        Log.i("DEBUGGING", ex.getClass().toString());
                    } catch (IOException ex) {
                        Log.i("DEBUGGING", ex.getClass().toString());
                    }
                    catch(ClassNotFoundException ex) {
                        Log.i("DEBUGGING", ex.getMessage());
                    }
                }
            };
            thread.start();
            thread.join();
            if(User.getStaticAnswer() == -1 ) {
                throw new SocketException("MUNCHKIN: Socket Exception.");
            }
        } catch(SocketException ex) {
            throw ex;
        } catch(Exception ex) {
            result.setLobbyId(0);
            Log.i("DEBUGGING", ex.getClass().toString());
        }
        return result;
    }

    public static void createLobby(int players, boolean friendsOnly) throws SocketException{
        try {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        oos.writeInt(CREATE_LOBBY);
                        oos.writeInt(players);
                        oos.writeBoolean(friendsOnly);
                        oos.flush();
                        int lobbyId = ois.readInt();
                        User.setStaticAnswer(lobbyId);
                    } catch (SocketException ex) {
                        User.setStaticAnswer(-1);
                        Log.i("DEBUGGING", ex.getClass().toString());
                    } catch (IOException ex) {
                        User.setStaticAnswer(0);
                        Log.i("DEBUGGING", ex.getClass().toString());
                    }
                }
            };
            thread.start();
            thread.join();
            if(User.getStaticAnswer() == -1 ) {
                throw new SocketException("MUNCHKIN: Socket Exception.");
            }
        } catch(SocketException ex) {
            throw ex;
        } catch(Exception ex) {
            Log.i("DEBUGGING", ex.getClass().toString());
        }
    }

    public static void goOffline() {
        try {
            oos.close();
            ois.close();
        } catch (Exception ex) {
            Log.i("DEBUGGING", ex.getMessage());
        }
    }
}
