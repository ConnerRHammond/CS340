package edu.byu.cs.tweeter.model.net.request;

import java.io.Serializable;
import java.util.List;

public class FeedSQSMessage implements Serializable {
    public List<String> aliases;
    public String post;
    public String date;
    public FeedSQSMessage(){}

    public FeedSQSMessage(List<String> aliases, String post,String date) {
        this.aliases = aliases;
        this.post = post;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }
}
