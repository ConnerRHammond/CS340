package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.Views.View;

public abstract class RegisterLoginPresenter extends BasePresenter {
    public RegisterLoginPresenter(View view) {
        super(view);
    }
    String validateAliasPassword(String alias, String password){
        if (alias.length() == 0) {
            return "Alias cannot be empty.";
        }
        if (alias.charAt(0) != '@') {
            return "Alias must begin with @.";
        }
        if (alias.length() < 2) {
            return "Alias must contain 1 or more characters after the @.";
        }
        if (password.length() == 0) {
            return "Password cannot be empty.";
        }
        return  null;
    }

}
