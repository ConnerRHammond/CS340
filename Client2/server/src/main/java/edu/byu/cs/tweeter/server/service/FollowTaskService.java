package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.AuthenticatedRequest;
import edu.byu.cs.tweeter.model.net.request.FollowingRequest;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.response.IsFollowerResponse;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;
import edu.byu.cs.tweeter.model.net.response.Response;
import edu.byu.cs.tweeter.server.dao.DynamoAuthtokenDAO;
import edu.byu.cs.tweeter.server.dao.DynamoDAOFactory;
import edu.byu.cs.tweeter.server.daoInterfaces.AuthtokenDAO;
import edu.byu.cs.tweeter.server.daoInterfaces.DAOFactory;
import edu.byu.cs.tweeter.server.daoInterfaces.FollowsDAO;
import edu.byu.cs.tweeter.server.daoInterfaces.UsersDAO;

public class FollowTaskService extends BaseService{

    public Response Follow(AuthenticatedRequest request,DynamoDAOFactory factory) {
        AuthtokenDAO authtokenDAO = factory.getAuthtokenDAO();

        if(checkDate(authtokenDAO.getTokenTime(request.getAuthToken().getToken()))) {
            FollowsDAO followsDAO = factory.getFollowDAO();
            UsersDAO usersDAO = factory.getUserDAO();
            usersDAO.incrementFollowers(request.getUser().getAlias());
            String message = followsDAO.Follow(request.getUser().getAlias(), authtokenDAO.getTokenAlias(request.getAuthToken().getToken()));
            boolean success = message == "Succeded" ? true : false;
            return new Response(success, message);
        }
        else {
            return new Response(false,"failed to authenticate Authtoken");
        }
    }
    public Response UnFollow(AuthenticatedRequest request,DAOFactory factory ) {
        AuthtokenDAO authtokenDAO = factory.getAuthtokenDAO();
        if(checkDate(authtokenDAO.getTokenTime(request.getAuthToken().getToken()))) {
            FollowsDAO followsDAO = factory.getFollowDAO();
            UsersDAO usersDAO = factory.getUserDAO();
            usersDAO.incrementFollowers(request.getUser().getAlias());
            String message = followsDAO.unFollow(request.getUser().getAlias(), authtokenDAO.getTokenAlias(request.getAuthToken().getToken()));
            boolean success = message == "Succeded" ? true : false;
            return new Response(success, message);
        }
        else {
            return new Response(false,"failed to authenticate Authtoken");
        }

    }
    public IsFollowerResponse isFollower(FollowingRequest request,DynamoDAOFactory factory){
        factory = new DynamoDAOFactory();
        AuthtokenDAO authtokenDAO = factory.getAuthtokenDAO();
        if(checkDate(authtokenDAO.getTokenTime(request.getAuthToken().getToken()))) {
            FollowsDAO followsDAO = factory.getFollowDAO();
            boolean following = followsDAO.isFollower(request.getFollowerAlias(),request.getLastFolloweeAlias());
            return  new IsFollowerResponse(following);
        }
        else {
            return new IsFollowerResponse("Failed to authenticate authtoken");
        }
    }

}
