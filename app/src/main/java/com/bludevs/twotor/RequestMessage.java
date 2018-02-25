package com.bludevs.twotor;


public class RequestMessage {

    protected String imgURL, name, topic, desc, date, subj, ID, key;

    protected Boolean resolved = false;

    public RequestMessage(){

    }

    public RequestMessage(String imgURL, String name, String topic, String desc, String subj,
                          String date, String ID) {
        this.imgURL = imgURL;
        this.name = name;
        this.topic = topic;
        this.desc = desc;
        this.subj = subj;
        this.date = date;
        this.ID = ID;
    }

    public void msgStatus(Boolean b){
        this.resolved = b;
    }
    public void setKey(String k){
        key = k;
    }
}
