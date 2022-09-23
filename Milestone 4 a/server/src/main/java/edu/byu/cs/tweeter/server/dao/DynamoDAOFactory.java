package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.server.daoInterfaces.AuthtokenDAO;
import edu.byu.cs.tweeter.server.daoInterfaces.DAOFactory;
import edu.byu.cs.tweeter.server.daoInterfaces.FollowsDAO;
import edu.byu.cs.tweeter.server.daoInterfaces.StatusDAO;
import edu.byu.cs.tweeter.server.daoInterfaces.UsersDAO;

public class DynamoDAOFactory implements DAOFactory {
    @Override
    public UsersDAO getUserDAO() {
        return new DynamoUsersDAO();
    }

    @Override
    public StatusDAO getStoryDAO() {
        return new DynamoStoryDAO();
    }

    @Override
    public StatusDAO getFeedDAO() {
        return new DynamoFeedDAO();
    }

    @Override
    public FollowsDAO getFollowDAO() {
        return new DynamoFollowDAO();
    }

    @Override
    public AuthtokenDAO getAuthtokenDAO() {
        return new DynamoAuthtokenDAO();
    }
}

