package edu.byu.cs.tweeter.model.net.response;

import java.io.Serializable;

public class CountResponse extends Response implements Serializable {
    private int count;
    public CountResponse(String message) {
        super(false,message);
    }
    public CountResponse(int count) {
        super(true,null);
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
