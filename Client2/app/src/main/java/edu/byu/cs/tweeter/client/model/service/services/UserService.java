package edu.byu.cs.tweeter.client.model.service.services;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.Tasks.GetUserTask;
import edu.byu.cs.tweeter.client.model.service.Tasks.LoginTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.BackgroundTaskHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.UserObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.GetUserRequest;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;

public class UserService extends ServiceBase{

    public void login(String alias,String password, UserObserver observer){
        LoginRequest request = new LoginRequest(alias,password);
        LoginTask loginTask = new LoginTask(request, new LoginHandler(observer));
        Execute(loginTask);
    }
    public void getUser(AuthToken authToken,String alias,UserObserver observer){
            GetUserRequest request = new GetUserRequest(alias,authToken);
            GetUserTask getUserTask = new GetUserTask(request, new GetUserHandler(observer));
            Execute(getUserTask);
    }
    private class LoginHandler extends BackgroundTaskHandler <UserObserver> {
        public  LoginHandler(UserObserver observer){
            super(observer);
        }
        @Override
        protected String getFailedMessagePrefix() {
            return "Login failed";
        }
        @Override
        protected void handleSuccessMessage(UserObserver observer, Bundle data) {
                User loggedInUser = (User) data.getSerializable(LoginTask.USER_KEY);
                AuthToken authToken = (AuthToken) data.getSerializable(LoginTask.AUTH_TOKEN_KEY);
                Cache.getInstance().setCurrUser(loggedInUser);
                Cache.getInstance().setCurrUserAuthToken(authToken);
                observer.userSucceeded(authToken,loggedInUser);
        }
    }
    private class GetUserHandler extends BackgroundTaskHandler<UserObserver> {
        public GetUserHandler(UserObserver observer){
            super(observer);
        }
        @Override
        protected String getFailedMessagePrefix() {
            return "Failed to retrieve user:";
        }
        @Override
        protected void handleSuccessMessage(UserObserver observer, Bundle data) {
                User user = (User) data.getSerializable(GetUserTask.USER_KEY);
                observer.userSucceeded(null,user);
        }
    }

}
