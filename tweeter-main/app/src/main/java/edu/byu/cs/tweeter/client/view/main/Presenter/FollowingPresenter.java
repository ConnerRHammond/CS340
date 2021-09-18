package edu.byu.cs.tweeter.client.view.main.Presenter;

import java.util.List;

import javax.swing.text.View;

import edu.byu.cs.tweeter.client.view.main.model.service.FollowService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowingPresenter implements  {
    private User lastFollowee;
    private View  view;
    private AuthToken authtoken;
    private  User user;
    private boolean hasMorePages;
    private boolean isLoading = false;

    private static final int PAGE_SIZE = 10;
    public  interface  view {

        void addItems(List<User> followees);
        void setLoading(boolean value);
        void navigateToUser(User user);
        void displayErrorMessage(String message);
        void displayInfoMessage(String message);

    }
    public void loadMoreItems(){
        if(isLoading && hasMorePages){
            isLoading = true;
            view.setLoading(true);
            new FollowService().getFollowing(authtoken,user,PAGE_SIZE,lastFollowee, this);
        }
    }
    public void goToUser(String alias){

    }
    public  FollowingPresenter(View view, AuthToken authToken, User targetUser){
        this.view = view;
        this.authtoken = authToken;
        this.user = targetUser;
    }
}
