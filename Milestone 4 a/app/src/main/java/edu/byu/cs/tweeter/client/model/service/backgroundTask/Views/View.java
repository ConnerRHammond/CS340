package edu.byu.cs.tweeter.client.model.service.backgroundTask.Views;

import edu.byu.cs.tweeter.model.domain.User;

public interface View {
    void displayInfoMessage(String Message);
    void navigateToUser(User user);
}
