package edu.byu.cs.tweeter.client.presenter;

import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.backgroundTask.GetUserTask;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.FollowingService;
import edu.byu.cs.tweeter.client.model.service.StoryService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.view.main.story.StoryFragment;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StoryPresenter implements StoryService.StoryObserver, UserService.GetUserObserver {


    private static final int PAGE_SIZE = 10;
    private StoryPresenter.View view;
    private User targetUser;
    private AuthToken authToken;

    private Status lastStatus;
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
    @Override
    public void StorySucceeded(List<Status> statuses, Boolean hasMorePages, Status lastStatus) {
        this.lastStatus = lastStatus;
        this.hasMorePages = hasMorePages;
        view.setLoading(false);
        view.addItems(statuses);
        this.isLoading = false;
    }

    @Override
    public void StoryFailed(String message) {
        String errorMessage = "Failed to retrieve followees: " + message;
        view.setLoading(false);
        view.displayErrorMessage(errorMessage);
        this.isLoading = false;
    }

    @Override
    public void StoryThrewException(Exception ex) {
        String errorMessage = "Failed to retrieve followees because of exception: " + ex.getMessage();
        view.setLoading(false);
        view.displayErrorMessage(errorMessage);
        this.isLoading = false;
    }

    public  interface  View{
        void setLoading(boolean value);
        void addItems(List<Status> Story);
        void navigateToUser(User user);
        void navigateToUrl(String url);
        void displayErrorMessage(String Message);
        void displayInfoMessage(String message);
    }
    public StoryPresenter(StoryPresenter.View view, User targetUser){
        this.view = view;
        this.targetUser = targetUser;
        this.authToken = Cache.getInstance().getCurrUserAuthToken();
    }
    public void clickedUser(String clickable){
        if (clickable.contains("http")) {
            view.navigateToUrl(clickable);
        } else {
            new UserService().getUser(authToken,clickable,this);
            view.displayInfoMessage("Getting user's profile...");
        }
    }

    public void  loadMoreStories(){
        if(!isLoading && hasMorePages){
            isLoading = true;
            view.setLoading(true);
            new StoryService().getStory(authToken,targetUser,PAGE_SIZE,lastStatus,this);
        }
    }


}
