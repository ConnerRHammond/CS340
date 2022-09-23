package edu.byu.cs.tweeter.server.daoInterfaces;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.server.dao.StatusList;

public interface StoryDAO {
    String postStatus(Status status,String alias);
    String createStory(User user);
}
