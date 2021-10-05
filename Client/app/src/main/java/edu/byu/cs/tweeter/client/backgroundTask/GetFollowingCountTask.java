package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

/**
 * Background task that queries how many other users a specified user is following.
 */
public class GetFollowingCountTask extends GetCountTask {

    private static final String LOG_TAG = "GetFollowersCountTask";
    public GetFollowingCountTask(AuthToken authToken, User targetUser,Handler messageHandler){
        super(authToken,targetUser,messageHandler);
    }
    @Override
    protected boolean runTask() throws IOException {
        this.count = 20;
        return true;
    }
}