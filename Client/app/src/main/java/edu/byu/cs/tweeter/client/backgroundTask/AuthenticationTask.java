package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Bundle;
import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class AuthenticationTask extends BackgroundTask{

    private static  final String LOG_TAG = "AuthenticateTask";
    public static final String USER_KEY = "user";
    public static final String AUTH_TOKEN_KEY = "auth-token";

    private String username;
    private String password;
    protected User user;
    protected AuthToken authToken;


    protected AuthenticationTask(String username, String password,Handler messageqHandler) {
        super(messageqHandler);

        this.username = username;
        this.password = password;
    }

    @Override
    public void loadSuccessBundle(Bundle msgBundle){
        msgBundle.putSerializable(USER_KEY,this.user);
        msgBundle.putSerializable(AUTH_TOKEN_KEY,this.authToken);

    }
    @Override
    protected boolean runTask() throws IOException {
        return false;
    }
}
