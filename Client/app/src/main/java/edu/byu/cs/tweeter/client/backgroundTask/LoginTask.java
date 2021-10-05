package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.util.FakeData;
import edu.byu.cs.tweeter.util.Pair;

/**
 * Background task that logs in a user (i.e., starts a session).
 */
public class LoginTask extends AuthenticationTask{

    private static final String LOG_TAG = "LoginTask";

    public static final String SUCCESS_KEY = "success";
    public static final String USER_KEY = "user";
    public static final String AUTH_TOKEN_KEY = "auth-token";
    public static final String MESSAGE_KEY = "message";
    public static final String EXCEPTION_KEY = "exception";

    /**
     * The user's username (or "alias" or "handle"). E.g., "@susan".
     */
    private String username;
    /**
     * The user's password.
     */
    private String password;
    /**
     * Message handler that will receive task results.
     */
    private Handler messageHandler;

    public LoginTask(String username, String password, Handler messageHandler) {
        super(username,password,messageHandler);
    }


    @Override
    protected boolean runTask() throws IOException {
        this.user = getFakeData().getFirstUser();
        this.authToken = getFakeData().getAuthToken();
        BackgroundTaskUtils.loadImage(user);
        return true;
    }





}
