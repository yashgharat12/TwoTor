package com.bludevs.twotor;

/**
 * Created by syre on 2/27/18.
 */

public class ForumMessage {
    protected String imgURL, name, ID, msg, key;

    public ForumMessage(){};

    public ForumMessage(String url, String name, String ID, String msg){
        this.imgURL = url;
        this.name = name;
        this.ID = ID;
        this.msg = msg;
    }

    public void setKey(String k){
        key = k;
    }
}
