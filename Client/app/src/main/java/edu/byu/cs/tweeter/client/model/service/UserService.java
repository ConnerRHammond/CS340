package edu.byu.cs.tweeter.client.model.service;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.backgroundTask.GetUserTask;
import edu.byu.cs.tweeter.client.backgroundTask.LoginTask;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class UserService {


    public interface  LoginObserver{
        void loginSucceeded(AuthToken authToken,User user);
        void loginFailed(String message);
        void loginThrewException(Exception ex);
    }
    public interface GetUserObserver{
        void getUserSucceeded(User user);
        void getUserFailed(String message);
        void getUserThrewException(Exception ex);
    }
    public void login(String alias,String password, LoginObserver observer){
        LoginTask loginTask = new LoginTask(alias,
                password, new LoginHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(loginTask);
    }
    public void getUser(AuthToken authToken,String alias, GetUserObserver observer){
            GetUserTask getUserTask = new GetUserTask(authToken,
                    alias, new GetUserHandler(observer));
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute(getUserTask);
    }

    private class LoginHandler extends Handler {
        private LoginObserver observer;
        public  LoginHandler(LoginObserver observer){
            this.observer = observer;
        }
        @Override
        public void handleMessage(@NonNull Message msg) {
            boolean success = msg.getData().getBoolean(LoginTask.SUCCESS_KEY);
            if (success) {
                User loggedInUser = (User) msg.getData().getSerializable(LoginTask.USER_KEY);
                AuthToken authToken = (AuthToken) msg.getData().getSerializable(LoginTask.AUTH_TOKEN_KEY);

                Cache.getInstance().setCurrUser(loggedInUser);
                Cache.getInstance().setCurrUserAuthToken(authToken);

                observer.loginSucceeded(authToken,loggedInUser);
            } else if (msg.getData().containsKey(LoginTask.MESSAGE_KEY)) {
                String message = msg.getData().getString(LoginTask.MESSAGE_KEY);
                observer.loginFailed(message);
            } else if (msg.getData().containsKey(LoginTask.EXCEPTION_KEY)) {
                Exception ex = (Exception) msg.getData().getSerializable(LoginTask.EXCEPTION_KEY);
                observer.loginThrewException(ex);
            }
        }
    }
    private class GetUserHandler extends Handler {
        private GetUserObserver observer;

        public GetUserHandler(GetUserObserver observer){
            this.observer = observer;
        }
        @Override
        public void handleMessage(@NonNull Message msg) {
            boolean success = msg.getData().getBoolean(GetUserTask.SUCCESS_KEY);
            if (success) {
                User user = (User) msg.getData().getSerializable(GetUserTask.USER_KEY);
                observer.getUserSucceeded(user);
            } else if (msg.getData().containsKey(GetUserTask.MESSAGE_KEY)) {
                String message = msg.getData().getString(GetUserTask.MESSAGE_KEY);
                observer.getUserFailed(message);
            } else if (msg.getData().containsKey(GetUserTask.EXCEPTION_KEY)) {
                Exception ex = (Exception) msg.getData().getSerializable(GetUserTask.EXCEPTION_KEY);
                observer.getUserThrewException(ex);
            }
        }
    }
}