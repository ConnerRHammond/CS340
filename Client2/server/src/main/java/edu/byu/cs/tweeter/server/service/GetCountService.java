package edu.byu.cs.tweeter.server.service;


import java.util.Date;

import edu.byu.cs.tweeter.model.net.request.AuthenticatedRequest;
import edu.byu.cs.tweeter.model.net.response.CountResponse;
import edu.byu.cs.tweeter.server.dao.DynamoDAOFactory;
import edu.byu.cs.tweeter.server.daoInterfaces.AuthtokenDAO;
import edu.byu.cs.tweeter.server.daoInterfaces.DAOFactory;
import edu.byu.cs.tweeter.server.daoInterfaces.FollowsDAO;
import edu.byu.cs.tweeter.server.daoInterfaces.UsersDAO;


public class GetCountService extends BaseService{
    public CountResponse getFollowersCount(AuthenticatedRequest request, DAOFactory factory) {
        UsersDAO usersDAO = factory.getUserDAO();
        AuthtokenDAO authtokenDAO = factory.getAuthtokenDAO();
        double authtokenDate = authtokenDAO.getTokenTime(request.getAuthToken().getToken());
        if(checkDate(authtokenDate)){
            CountResponse response = new CountResponse(usersDAO.getFollowers(request.getUser().getAlias()));
            return response;
        }else{
            return new CountResponse("Failed to authenticate authtoken"+ authtokenDate);
        }
    }
    public CountResponse getFolloweesCount(AuthenticatedRequest request, DynamoDAOFactory factory) {
        factory = new DynamoDAOFactory();
        FollowsDAO followsDAO = factory.getFollowDAO();
        AuthtokenDAO authtokenDAO = factory.getAuthtokenDAO();
        double authtokenDate = authtokenDAO.getTokenTime(request.getAuthToken().getToken());
        if(checkDate(authtokenDate)){
            CountResponse response = followsDAO.getFollowerCount(request.getUser().getAlias());
            return response;
        }else{
            return new CountResponse("Failed to authenticate authtoken" + authtokenDate);
        }
    }
}
