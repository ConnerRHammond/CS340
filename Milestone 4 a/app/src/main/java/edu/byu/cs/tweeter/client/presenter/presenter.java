package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.Views.View;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class presenter <T extends View>{
    public presenter(T view) {
        this.view = view;
    }
    T view;

}
