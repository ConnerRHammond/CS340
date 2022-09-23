package edu.byu.cs.tweeter.model.net.response;

import java.io.Serializable;

public class IsFollowerResponse  extends Response implements Serializable {
   private boolean follower;
    public IsFollowerResponse(){

    }
    public  IsFollowerResponse(boolean follower){
        super(true,"Succeded");
        this.follower = follower;
    }
    public  IsFollowerResponse(String message){
        super(false,message);
    }

    public boolean isFollower() {
        return follower;
    }

    public void setFollower(boolean follower) {
        follower = follower;
    }
}
