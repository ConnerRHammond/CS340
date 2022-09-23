package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;


import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Follow;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.AuthenticatedRequest;
import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.request.FollowingRequest;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.response.IsFollowerResponse;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;
import edu.byu.cs.tweeter.model.net.response.Response;
import edu.byu.cs.tweeter.server.dao.DynamoDAOFactory;
import edu.byu.cs.tweeter.server.service.FollowTaskService;
import edu.byu.cs.tweeter.server.service.LoginService;


public class IsFollowingHandler implements RequestHandler<FollowingRequest, IsFollowerResponse> {

    @Override
    public IsFollowerResponse handleRequest(FollowingRequest followingRequest, Context context) {
        FollowTaskService service = new FollowTaskService();
        DynamoDAOFactory factory = new DynamoDAOFactory();
        IsFollowerResponse response = service.isFollower(followingRequest, factory);
        System.out.print(response.isFollower());
        System.out.print(response);
        return response;
    }

//    public static void main(String[] args) {
//        LoginService LoginService = new LoginService(new LoginRequest("@allen","a"));
//        LoginResponse respon= LoginService.login();
//        FollowingRequest followingRequest = new FollowingRequest(respon.getAuthToken(),"@allen",0,"@amy");
//        FollowTaskService service = new FollowTaskService();
//        IsFollowerResponse response = service.isFollower(followingRequest);
//        System.out.print(response.isFollower()+"\n\n");
//
//        AuthenticatedRequest request = new AuthenticatedRequest();
//        request.setAuthToken(new AuthToken(respon.getAuthToken().getToken()));
//        request.setUser( new User("amy","something","@amy","something"));
//        Response response1= service.UnFollow(request);
//        response = service.isFollower(followingRequest);
//        System.out.print(response.isFollower()+"\n\n");
//
//        request = new AuthenticatedRequest();
//        request.setAuthToken(new AuthToken(respon.getAuthToken().getToken()));
//        request.setUser( new User("amy","something","@amy","something"));
//        Response response2 = service.Follow(request);
//        response = service.isFollower(followingRequest);
//        System.out.print(response.isFollower()+"\n\n");
//    }
}
//FollowingRequest
//IsFollowerResponse