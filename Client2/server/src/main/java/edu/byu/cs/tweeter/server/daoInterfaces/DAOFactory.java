package edu.byu.cs.tweeter.server.daoInterfaces;

public interface DAOFactory {
    UsersDAO getUserDAO();
    StatusDAO getStoryDAO();
    StatusDAO getFeedDAO();
    FollowsDAO getFollowDAO();
    AuthtokenDAO getAuthtokenDAO();
}
