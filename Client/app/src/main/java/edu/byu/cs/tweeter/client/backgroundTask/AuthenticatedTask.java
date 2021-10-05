package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Handler;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public abstract class AuthenticatedTask extends BackgroundTask  {
    private static final String LOG_Tag ="AuthenticatedTask";
    protected AuthToken authToken;

    public AuthenticatedTask(AuthToken authToken,Handler messageHandler){
        super(messageHandler);
        this.authToken = authToken;
    }
}
