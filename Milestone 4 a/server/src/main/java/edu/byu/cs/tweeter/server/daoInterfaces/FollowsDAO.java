package edu.byu.cs.tweeter.server.daoInterfaces;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import edu.byu.cs.tweeter.model.net.response.CountResponse;
import edu.byu.cs.tweeter.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface FollowsDAO {
    boolean isFollower(String follower,String followee);
    String Follow(String alias,String follower);
    String unFollow(String follower, String followee);
    CountResponse getFollowerCount(String Alias);
    CountResponse getFollowingCount(String Alias);
    Pair<List<String>, Boolean> getFollowers(String Alias, ArrayList key);
    Pair<List<String>, Boolean> getFollowing(String Alias, int max,ArrayList key);
}
