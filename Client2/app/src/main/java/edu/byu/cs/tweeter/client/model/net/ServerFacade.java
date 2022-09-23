package edu.byu.cs.tweeter.client.model.net;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.Tasks.GetFollowersCountTask;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.AuthTokenRequest;
import edu.byu.cs.tweeter.model.net.request.AuthenticatedRequest;
import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.request.FollowingRequest;
import edu.byu.cs.tweeter.model.net.request.GetUserRequest;
import edu.byu.cs.tweeter.model.net.request.ListRequest;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.request.StatusRequest;
import edu.byu.cs.tweeter.model.net.response.CountResponse;

import edu.byu.cs.tweeter.model.net.response.GetUserResponse;
import edu.byu.cs.tweeter.model.net.response.IsFollowerResponse;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;
import edu.byu.cs.tweeter.model.net.response.PagedResponse;
import edu.byu.cs.tweeter.model.net.response.Response;
import edu.byu.cs.tweeter.model.net.response.StatusResponse;
import edu.byu.cs.tweeter.model.net.response.UsersResponse;

/**
 * Acts as a Facade to the Tweeter server. All network requests to the server should go through
 * this class.
 */
public class ServerFacade {

    // TODO: Set this to the invoke URL of your API. Find it by going to your API in AWS, clicking
    //  on stages in the right-side menu, and clicking on the stage you deployed your API to.
    private static final String SERVER_URL = "https://tiy5mwxua5.execute-api.us-west-1.amazonaws.com/Tweeter";

    private final ClientCommunicator clientCommunicator = new ClientCommunicator(SERVER_URL);

    /**
     * Performs a login and if successful, returns the logged in user and an auth token.
     *
     * @param request contains all information needed to perform a login.
     * @return the login response.
     */
    public IsFollowerResponse isFollower(FollowingRequest request, String urlPath) throws IOException, TweeterRemoteException {
        IsFollowerResponse response = clientCommunicator.doPost(urlPath, request, null, IsFollowerResponse.class);
        if(response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }
    public  Response Post(PostStatusRequest request, String urlPath) throws IOException, TweeterRemoteException {
        Response response = clientCommunicator.doPost(urlPath, request, null, Response.class);

        if(response.isSuccess()) {
            return response;
        } else {
            System.out.print(response.getMessage());
            throw new RuntimeException(response.getMessage());
        }
    }
    public LoginResponse login(LoginRequest request, String urlPath) throws IOException, TweeterRemoteException {
        LoginResponse response = clientCommunicator.doPost(urlPath, request, null, LoginResponse.class);

        if(response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }

    public LoginResponse Register(RegisterRequest request, String urlPath) throws IOException, TweeterRemoteException {
        LoginResponse response = clientCommunicator.doPost(urlPath, request, null, LoginResponse.class);

        if(response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }
    public Response Follow(AuthenticatedRequest request,String urlPath)throws IOException, TweeterRemoteException{
        Response response = clientCommunicator.doPost(urlPath,request,null,Response.class);
        if(response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }
    public CountResponse GetFollowersCount(AuthenticatedRequest request,String urlPath) throws IOException, TweeterRemoteException {
        CountResponse response = clientCommunicator.doPost(urlPath,request,null,CountResponse.class);
        if(response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }
    public CountResponse GetFollowingCount(AuthenticatedRequest request,String urlPath) throws IOException, TweeterRemoteException {
        CountResponse response = clientCommunicator.doPost(urlPath,request,null,CountResponse.class);
        if(response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }
    public GetUserResponse GetUser(GetUserRequest request,String urlPath) throws IOException, TweeterRemoteException {
        GetUserResponse response = clientCommunicator.doPost(urlPath,request,null,GetUserResponse.class);
        if(response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }
    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request.
     *
     * @param request contains information about the user whose followees are to be returned and any
     *                other information required to satisfy the request.
     * @return the followees.
     */
    public UsersResponse GetUsers(FollowingRequest request, String urlPath)
            throws IOException, TweeterRemoteException {

        UsersResponse response = clientCommunicator.doPost(urlPath, request, null, UsersResponse.class);

        if(response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }
    public StatusResponse GetStatus(StatusRequest request, String urlPath) throws IOException, TweeterRemoteException {
        StatusResponse response = clientCommunicator.doPost(urlPath, request, null, StatusResponse.class);
        if(response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }
    public Response LogOut(AuthTokenRequest request,String urlPath) throws IOException, TweeterRemoteException {
        Response response = clientCommunicator.doPost(urlPath, request, null, Response.class);
        if(response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }

    public <T extends Response> T handleResponse(T response){
        if(response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }


}