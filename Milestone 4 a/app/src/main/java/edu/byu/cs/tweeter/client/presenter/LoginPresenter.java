package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.services.UserService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.Views.View;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.UserObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class LoginPresenter extends RegisterLoginPresenter implements UserObserver {

    public LoginPresenter(View view){
        super(view);
    }
    public void login(String alias,String password) {
        String message = validateAliasPassword(alias, password);
        if (message == null) {
            view.displayInfoMessage("Loggin in ....");
            new UserService().login(alias, password, this);
        } else {
            view.displayInfoMessage("Login failed: " + message);
        }
    }
}
