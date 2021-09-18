package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class LoginPresenter implements  UserService.LoginObserver{


    public interface View{
        void navigateToUser(User user);
        void displayErrorMessage(String Message);
        void clearErrorMessage();

        void displayInfoMessage(String message);
        void clearInfoMessage();
    }

    private View view;

    public LoginPresenter(View view){
        this.view = view;
    }
    public void login(String alias,String password) {
        String message = validateLogin(alias, password);

        view.clearErrorMessage();
        view.clearInfoMessage();
        if (message == null) {
            view.displayInfoMessage("Loggin in ....");
            new UserService().login(alias, password, this);
        } else {
            view.displayErrorMessage("Login failed: " + message);

        }

    }

    private String validateLogin(String alias, String password) {
        if (alias.charAt(0) != '@') {
            return "Alias must begin with @.";
        }
        if (alias.length() < 2) {
            return"Alias must contain 1 or more characters after the @.";
        }
        if (password.length() == 0) {
            return "Password cannot be empty.";
        }
        return null;
    }
    @Override
    public void loginSucceeded(AuthToken authToken, User user) {
        view.navigateToUser(user);
        view.displayInfoMessage("Hello "+ user.getFirstName());
    }

    @Override
    public void loginFailed(String message) {
        view.displayErrorMessage("Login Failed: " + message);
    }

    @Override
    public void loginThrewException(Exception ex) {
        view.displayErrorMessage("Login Failed: " + ex);
    }


}
