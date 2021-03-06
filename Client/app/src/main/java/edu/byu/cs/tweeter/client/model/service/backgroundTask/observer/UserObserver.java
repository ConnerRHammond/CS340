package edu.byu.cs.tweeter.client.model.service.backgroundTask.observer;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public interface UserObserver extends ServiceObserver{
    void userSucceeded(AuthToken authToken, User user);
}
