package edu.byu.cs.tweeter.client.model.service.Tasks;

import android.os.Handler;

import java.io.IOException;
import java.util.List;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.StatusRequest;
import edu.byu.cs.tweeter.model.net.response.StatusResponse;
import edu.byu.cs.tweeter.util.Pair;

/**
 * Background task that retrieves a page of statuses from a user's story.
 */
public class GetStoryTask extends PagedStatusTask {
    StatusRequest request;
    public GetStoryTask(StatusRequest request, Handler messageHandler) {
        super(request,messageHandler);
        this.request = request;
    }

    @Override
    protected Pair<List<Status>, Boolean> getItems() throws IOException, TweeterRemoteException {
        StatusResponse response = new ServerFacade().GetStatus(request,"/getstory");
        Pair<List<Status>, Boolean> pair = new Pair(response.getItems(),response.getHasMorePages());
        return pair;
//        return getFakeData().getPageOfStatus(getLastItem(), getLimit());
    }
}
