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
 * Background task that creates a new user account and logs in the new user (i.e., starts a session).
 */
public class RegisterTask  extends AuthenticationTask {
    private static final String LOG_TAG = "RegisterTask";

    public static final String SUCCESS_KEY = "success";
    public static final String USER_KEY = "user";
    public static final String AUTH_TOKEN_KEY = "auth-token";
    public static final String MESSAGE_KEY = "message";
    public static final String EXCEPTION_KEY = "exception";

    /**
     * The user's first name.
     */
    private String firstName;
    /**
     * The user's last name.
     */
    private String lastName;
    /**
     * The user's username (or "alias" or "handle"). E.g., "@susan".
     */
    private String username;
    /**
     * The user's password.
     */
    private String password;
    /**
     * The base-64 encoded bytes of the user's profile image.
     */
    private String image;
    /**
     * Message handler that will receive task results.
     */
    private Handler messageHandler;

    public RegisterTask(String firstName, String lastName, String username, String password,
                        String image, Handler messageHandler) {
        super(username, password, messageHandler);
        this.firstName = firstName;
        this.lastName = lastName;
        this.image = image;
    }

    @Override
    protected boolean runTask() throws IOException {
        this.user = getFakeData().getFirstUser();
        this.authToken = getFakeData().getAuthToken();
        BackgroundTaskUtils.loadImage(user);
        return true;
    }
}