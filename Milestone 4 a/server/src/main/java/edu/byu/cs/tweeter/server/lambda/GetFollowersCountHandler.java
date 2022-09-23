package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.net.request.AuthenticatedRequest;
import edu.byu.cs.tweeter.model.net.response.CountResponse;
import edu.byu.cs.tweeter.server.dao.DynamoDAOFactory;
import edu.byu.cs.tweeter.server.service.GetCountService;

public class GetFollowersCountHandler implements RequestHandler<AuthenticatedRequest, CountResponse> {
    @Override
    public CountResponse handleRequest(AuthenticatedRequest request, Context context) {
        GetCountService count = new GetCountService();
        DynamoDAOFactory factory = new DynamoDAOFactory();
        return count.getFollowersCount(request,factory);
    }
}
