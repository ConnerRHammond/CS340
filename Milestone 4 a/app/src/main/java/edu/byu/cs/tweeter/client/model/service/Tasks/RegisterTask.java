package edu.byu.cs.tweeter.client.model.service.Tasks;

import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;
import edu.byu.cs.tweeter.util.Pair;

/**
 * Background task that creates a new user account and logs in the new user (i.e., starts a session).
 */
public class RegisterTask extends AuthenticationTask {

    /**
     * The user's first name.
     */
    private final String firstName;
    
    /**
     * The user's last name.
     */
    private final String lastName;

    /**
     * The base-64 encoded bytes of the user's profile image.
     */
    private final String image;

    private RegisterRequest request;
    public RegisterTask(String firstName, String lastName, String username, String password,
                        String image, Handler messageHandler) {
        super(messageHandler, username, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.image = image;
        request = new RegisterRequest(username,password,firstName,lastName,image);
    }

    @Override
    protected Pair<User, AuthToken> runAuthenticationTask() throws IOException, TweeterRemoteException {
        ServerFacade fascade = new ServerFacade();
        LoginResponse response = fascade.Register(request,"/register");
//        User registeredUser = getFakeData().getFirstUser();
//        AuthToken authToken = getFakeData().getAuthToken();
        return new Pair<>(response.getUser(), response.getAuthToken());
    }
}
