package com.bludevs.twotor;

/**
 * Created by Nightwing on 12/21/2017.
 */

public class RequestMessage {

    protected String imgURL, name,topic,desc,date,subj;

    protected Boolean resolved = false;

    public RequestMessage(){

    }

    public RequestMessage(String imgURL, String name, String topic, String desc, String subj, String date) {
        this.imgURL = imgURL;
        this.name = name;
        this.topic = topic;
        this.desc = desc;
        this.subj = subj;
        this.date = date;
    }

    public void msgStatus(Boolean b){
        this.resolved = b;
    }
}
