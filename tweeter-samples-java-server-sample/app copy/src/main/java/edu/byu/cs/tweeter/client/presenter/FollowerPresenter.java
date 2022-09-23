package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.FollowerService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.PageObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.UserObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowerPresenter extends PagedPresenter<User> {

    public FollowerPresenter(PagedView<User> view, AuthToken authToken, User targetUser) {
        super(view, authToken, targetUser);
    }

    @Override
    protected void getItems(AuthToken authToken, User targetUser, int limit, User lastItem) {
        new FollowerService().getFollowers(authToken,targetUser,PAGE_SIZE,lastItem,this);
    }

    @Override
    protected String getDescription() {
        return "Followers";
    }

    
}
