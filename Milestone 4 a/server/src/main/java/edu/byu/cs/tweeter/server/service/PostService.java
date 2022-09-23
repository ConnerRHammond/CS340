package edu.byu.cs.tweeter.server.service;

import java.util.List;

import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.response.Response;
import edu.byu.cs.tweeter.server.dao.DynamoDAOFactory;
import edu.byu.cs.tweeter.server.dao.DynamoFeedDAO;
import edu.byu.cs.tweeter.server.daoInterfaces.AuthtokenDAO;
import edu.byu.cs.tweeter.server.daoInterfaces.DAOFactory;
import edu.byu.cs.tweeter.server.daoInterfaces.FollowsDAO;
import edu.byu.cs.tweeter.server.daoInterfaces.StatusDAO;
import edu.byu.cs.tweeter.util.Pair;
import jdk.nashorn.internal.runtime.regexp.JoniRegExp;

public class PostService extends BaseService {
    public Response doPost(PostStatusRequest input, DAOFactory factory){
        factory = new DynamoDAOFactory();
        AuthtokenDAO authtokenDAO = factory.getAuthtokenDAO();
        StatusDAO storyDAO = factory.getStoryDAO();
        StatusDAO feedDAO = factory.getFeedDAO();
        FollowsDAO followsDAO = factory.getFollowDAO();
        if( checkDate(authtokenDAO.getTokenTime(input.getAuthToken().getToken()))){
            String alias = authtokenDAO.getTokenAlias(input.getAuthToken().getToken());
            Pair<List<String>, Boolean> pair= followsDAO.getFollowing(alias,0,null);
            for (String name : pair.getFirst()){
                feedDAO.postStatus(input.getStatus(),name);
            }
            storyDAO.postStatus(input.getStatus(),alias);
            return new Response(true,"Post Successful");
        }
        else {
            return new Response(false,"Failed to authenticate authotoken");
        }

    }
}
