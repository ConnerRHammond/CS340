package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.AuthenticatedRequest;
import edu.byu.cs.tweeter.model.net.response.CountResponse;
import edu.byu.cs.tweeter.server.dao.DynamoDAOFactory;
import edu.byu.cs.tweeter.server.service.GetCountService;

public class GetFolloweeCountHandler  implements RequestHandler<AuthenticatedRequest, CountResponse> {
    @Override
    public CountResponse handleRequest(AuthenticatedRequest request, Context context) {
        GetCountService count = new GetCountService();
        DynamoDAOFactory factory = new DynamoDAOFactory();
        return count.getFolloweesCount(request,factory);
    }

//    public static void main(String[] args) {
////        GetCountService count = new GetCountService();
////        CountResponse response =  count.getFolloweesCount(new AuthenticatedRequest(new AuthToken("23fcQD0YNv"),
////                new User("allen","something","@allen","something")));
////        System.out.print(response.getCount());
//    }
}
