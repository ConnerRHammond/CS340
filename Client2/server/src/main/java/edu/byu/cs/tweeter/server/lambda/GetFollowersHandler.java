package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FollowRequest;

import edu.byu.cs.tweeter.model.net.request.FollowingRequest;
import edu.byu.cs.tweeter.model.net.response.UsersResponse;
import edu.byu.cs.tweeter.server.dao.DynamoDAOFactory;
import edu.byu.cs.tweeter.server.service.UserService;
import edu.byu.cs.tweeter.server.util.JsonSerializer;

public class GetFollowersHandler implements RequestHandler<FollowingRequest, UsersResponse> {

    @Override
    public UsersResponse handleRequest(FollowingRequest request, Context context) {
        UserService service = new UserService();
        System.out.print((JsonSerializer.serialize(request)));
        DynamoDAOFactory factory = new DynamoDAOFactory();
        return service.getFollowers(request,factory);
    }

//    public static void main(String[] args) {
//        FollowingRequest request = new FollowingRequest(new AuthToken("11111"),"@allen",10,null);
//        UserService service = new UserService();
//        UsersResponse rep = service.getFollowers(request);
//
//        System.out.print(rep.getItems());
//        System.out.print(rep.isSuccess()+"\n\n");
//
////         new User("Fran", "Franklin","@fran", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png")
//        request = new FollowingRequest(new AuthToken("11111"),"@allen",10,rep.getItems().get(9).getAlias());
//        rep = service.getFollowers(request);
//        System.out.print(rep.getItems());
//        System.out.print(rep.isSuccess() + "\n\n");
//
//        request = new FollowingRequest(new AuthToken("11111"),"@allen",10,null);
//        rep = service.getFollowing(request);
//        System.out.print(rep.getItems());
//        System.out.print(rep.isSuccess()+"\n\n");
//
//        request = new FollowingRequest(new AuthToken("11111"),"@allen",10,rep.getItems().get(9).getAlias());
//        service = new UserService();
//
//        rep = service.getFollowing(request);
//        System.out.print(rep.getItems());
//        System.out.print(rep.isSuccess());

//    }
}
