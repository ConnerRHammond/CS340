package edu.byu.cs.tweeter.server.daoInterfaces;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.server.dao.StatusList;

public interface StatusDAO {
    String postStatus(Status status, String alias);
    String createStatuses(User user);
    StatusList getStatuses(String alias);
}
