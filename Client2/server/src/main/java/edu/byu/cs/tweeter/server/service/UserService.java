package edu.byu.cs.tweeter.server.service;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.request.FollowingRequest;
import edu.byu.cs.tweeter.model.net.response.PagedResponse;
import edu.byu.cs.tweeter.model.net.response.UsersResponse;
import edu.byu.cs.tweeter.server.dao.DynamoDAOFactory;
import edu.byu.cs.tweeter.server.daoInterfaces.AuthtokenDAO;
import edu.byu.cs.tweeter.server.daoInterfaces.DAOFactory;
import edu.byu.cs.tweeter.server.daoInterfaces.FollowsDAO;
import edu.byu.cs.tweeter.server.daoInterfaces.UsersDAO;
import edu.byu.cs.tweeter.util.Pair;

/**
 * Contains the business logic for getting the users a user is following.
 */
public class UserService extends BaseService{

    public UsersResponse getFollowers(FollowingRequest request, DAOFactory factory) {
        factory = new DynamoDAOFactory();
        UsersDAO usersDAO = factory.getUserDAO();
        FollowsDAO followsDAO = factory.getFollowDAO();
        AuthtokenDAO authtokenDAO = factory.getAuthtokenDAO();
        UsersResponse response;
        if(checkDate(authtokenDAO.getTokenTime(request.getAuthToken().getToken()))) {
            ArrayList<String> aliases = new ArrayList<>();
            List<User> users = new ArrayList<>();
            Pair<List<String>, Boolean> pair;
            if (request.getLastFolloweeAlias() != null) {
                ArrayList<String> queryAlias = new ArrayList<>();
                queryAlias.add(request.getFollowerAlias());
                queryAlias.add(request.getLastFolloweeAlias());
                System.out.print(queryAlias);
                pair = followsDAO.getFollowing(request.getFollowerAlias(), 10, queryAlias);
                System.out.print(pair.getFirst());
            } else {
                //System.out.print("null last alias");
                pair = followsDAO.getFollowing(request.getFollowerAlias(), 10, null);
            }
            for (String alias : pair.getFirst()) {
                users.add(usersDAO.getUser(alias, null));
            }
            response = new UsersResponse(users, pair.getSecond());
        }
        else{
            response = new UsersResponse(null,false);
        }
        return response;
    }

    public UsersResponse getFollowing(FollowingRequest request, DAOFactory factory) {
        factory = new DynamoDAOFactory();
        UsersDAO usersDAO = factory.getUserDAO();
        FollowsDAO followsDAO = factory.getFollowDAO();
        AuthtokenDAO authtokenDAO = factory.getAuthtokenDAO();
        ArrayList <String> aliases = new ArrayList<>();
        List<User> users = new ArrayList<>();
        Pair<List<String>, Boolean> pair;
        UsersResponse response;
        if(checkDate(authtokenDAO.getTokenTime(request.getAuthToken().getToken()))) {
            if (request.getLastFolloweeAlias() != null) {
                ArrayList<String> queryAlias = new ArrayList<>();
                queryAlias.add(request.getFollowerAlias());
                queryAlias.add(request.getLastFolloweeAlias());
                System.out.print(queryAlias);
                pair = followsDAO.getFollowers(request.getFollowerAlias(),10 ,queryAlias);
                System.out.print(pair.getFirst());
            } else {
                //System.out.print("null last alias");
                pair = followsDAO.getFollowers(request.getFollowerAlias(),10, null);
            }
            for (String alias : pair.getFirst()) {
                users.add(usersDAO.getUser(alias, null));
            }
            response = new UsersResponse(users, pair.getSecond());
        }else{
            response = new UsersResponse(null,false);
        }
        return response;
    }

    /**
     * Returns an instance of {@link FollowDAO}. Allows mocking of the FollowDAO class
     * for testing purposes. All usages of FollowDAO should get their FollowDAO
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
//    FollowDAO getFollowingDAO() {
//        return new FollowDAO();
//    }
}
