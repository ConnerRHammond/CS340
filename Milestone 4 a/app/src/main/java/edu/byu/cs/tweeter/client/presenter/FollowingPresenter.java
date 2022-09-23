package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.services.FollowingService;
import edu.byu.cs.tweeter.client.model.service.services.UserService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.PageObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.UserObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowingPresenter extends PagedPresenter<User> {


    public FollowingPresenter(PagedView<User> view, AuthToken authToken, User targetUser) {
        super(view, authToken, targetUser);
    }

    @Override
    protected void getItems(AuthToken authToken, User targetUser, int limit, User lastItem) {
        new FollowingService().getFollowing(authToken,targetUser,PAGE_SIZE,lastItem,this);
    }

    @Override
    protected String getDescription() {
        return "Following";
    }



}
