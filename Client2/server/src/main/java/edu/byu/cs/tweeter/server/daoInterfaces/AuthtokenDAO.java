package edu.byu.cs.tweeter.server.daoInterfaces;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.util.Pair;

public interface AuthtokenDAO {
    double getTokenTime(String authtoken);
    String updateToken(String alias,String authtoken);
    String createToken(String alias,String authtoken);
    String removeToken(String authtoken);
    String getTokenAlias(String authtoken);
    Pair<String, Double>  getAliasToken(String authtoken);
}
