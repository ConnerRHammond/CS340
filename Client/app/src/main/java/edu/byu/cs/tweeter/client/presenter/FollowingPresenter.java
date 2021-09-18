package edu.byu.cs.tweeter.client.presenter;

import android.util.Log;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.FollowingService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowingPresenter implements FollowingService.GetFollowingObserver,UserService.GetUserObserver  {

    private static final int PAGE_SIZE = 10;
    private View view;
    private User targetUser;
    private AuthToken authToken;

    private User lastFollowee;
    private boolean hasMorePages = true;
    private boolean isLoading = false;

    @Override
    public void getUserSucceeded(User user) {
        view.navigateToUser(user);
    }

    @Override
    public void getUserFailed(String message) {
        view.displayErrorMessage(message);
    }

    @Override
    public void getUserThrewException(Exception ex) {
        view.displayErrorMessage(ex.getMessage());
    }


    public  interface  View{
        void setLoading(boolean value);
        void addItems(List<User> followees);
        void navigateToUser(User user);

        void displayErrorMessage(String Message);
        void displayInfoMessage(String message);
    }


    public FollowingPresenter(View view, AuthToken authToken, User targetUser){
        this.view = view;
        this.targetUser = targetUser;
        this.authToken = authToken;
    }
    public void loadMoreItems(){
        if(!isLoading && hasMorePages){
            isLoading = true;
            view.setLoading(true);
            new FollowingService().getFollowing(authToken,targetUser,PAGE_SIZE,lastFollowee,this);
        }
    }
    public void goToUser(String alias){
        view.displayInfoMessage("Getting user's profile...");
        new UserService().getUser(authToken,alias,this);
    }

    @Override
    public void getFollowingSucceeded(List<User> users, Boolean hasMorePages, User user) {
        this.lastFollowee = (users.size() > 0) ? users.get(users.size() - 1) : null;
        this.hasMorePages = hasMorePages;
        view.setLoading(false);
        view.addItems(users);
        this.isLoading = false;
    }

    @Override
    public void getFollowingFailed(String message) {
        String errorMessage = "Failed to retrieve followees: " + message;
        view.setLoading(false);
        view.displayErrorMessage(errorMessage);
        this.isLoading = false;
    }

    @Override
    public void getFollowingThrewException(Exception ex) {
        String errorMessage = "Failed to retrieve followees because of exception: " + ex.getMessage();
        view.setLoading(false);
        view.displayErrorMessage(errorMessage);
        this.isLoading = false;
    }
}
