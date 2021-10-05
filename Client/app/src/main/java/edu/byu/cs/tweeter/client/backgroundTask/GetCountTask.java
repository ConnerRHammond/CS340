package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Bundle;
import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class GetCountTask extends AuthenticatedTask{

    public  static final String LOG_TAG ="GetCountTask";
    public static final String COUNT_KEY = "count";

    protected User targetUser;
    protected  int count;

    public GetCountTask(AuthToken authToken, User targetUser, Handler messageHandler){
        super(authToken,  messageHandler);

    }
    @Override
    protected void loadSuccessBundle(Bundle msgBundle){
        msgBundle.putInt(COUNT_KEY,count);
    }
}
