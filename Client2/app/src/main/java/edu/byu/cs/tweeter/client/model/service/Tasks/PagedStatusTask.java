package edu.byu.cs.tweeter.client.model.service.Tasks;

import android.os.Handler;

import java.util.List;
import java.util.stream.Collectors;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.StatusRequest;

public abstract class PagedStatusTask extends PagedTask<Status> {

    protected PagedStatusTask(StatusRequest request, Handler messageHandler) {
        super( messageHandler);
    }


    @Override
    protected final List<User> getUsersForItems(List<Status> items) {
        return items.stream().map(x -> x.user).collect(Collectors.toList());
    }
}
