package edu.byu.cs.tweeter.server.daoInterfaces;

public interface AuthtokenDAO {
    double getTokenTime(String authtoken);
    String updateToken(String alias,String authtoken);
    String createToken(String alias,String authtoken);
    String removeToken(String authtoken);
    String getTokenAlias(String authtoken);
}
