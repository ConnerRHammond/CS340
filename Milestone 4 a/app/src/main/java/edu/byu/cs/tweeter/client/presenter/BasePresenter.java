package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.Views.View;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class BasePresenter extends presenter {
    public BasePresenter(View view) {
        super(view);
    }
    public void userSucceeded(AuthToken authToken, User user) {
        view.navigateToUser(user);
        view.displayInfoMessage("Hello "+ user.getFirstName());
    }
    public void actionFailed(String message) {
        view.displayInfoMessage("Login Failed: " + message);
    }

}
