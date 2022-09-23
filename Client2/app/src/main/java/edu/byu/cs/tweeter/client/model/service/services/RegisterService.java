package edu.byu.cs.tweeter.client.model.service.services;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.Tasks.RegisterTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.BackgroundTaskHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.UserObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
public class RegisterService extends ServiceBase {
    public  void registerUser(String firstName, String lastName, String alias, String password,
                              String imageBytesBase64, UserObserver observer){
        RegisterTask registerTask = new RegisterTask(firstName, lastName,
                alias, password, imageBytesBase64, new RegisterHandler(observer));
        Execute(registerTask);
    }
    private class RegisterHandler extends BackgroundTaskHandler<UserObserver> {
        public  RegisterHandler(UserObserver observer){
            super(observer);
        }
        @Override
        protected String getFailedMessagePrefix() { return "Register failed:"; }
        @Override
        protected void handleSuccessMessage(UserObserver observer, Bundle data) {
            User registeredUser = (User) data.getSerializable(RegisterTask.USER_KEY);
            AuthToken authToken = (AuthToken)data.getSerializable(RegisterTask.AUTH_TOKEN_KEY);
            Cache.getInstance().setCurrUser(registeredUser);
            Cache.getInstance().setCurrUserAuthToken(authToken);
            observer.userSucceeded(authToken,registeredUser);
        }
    }
}