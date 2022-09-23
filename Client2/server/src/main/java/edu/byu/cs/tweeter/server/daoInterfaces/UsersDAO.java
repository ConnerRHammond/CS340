package edu.byu.cs.tweeter.server.daoInterfaces;

import edu.byu.cs.tweeter.model.domain.User;

public interface UsersDAO {
    int getFollowers(String alias);
    void incrementFollowers(String alias);
    void decrementFollowers(String alias);
    User getUser(String Alias,String password );
    String createUser(String alias,String firstName,String lastName, String url,String password);
}
