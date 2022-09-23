package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;
import edu.byu.cs.tweeter.server.dao.DynamoDAOFactory;
import edu.byu.cs.tweeter.server.daoInterfaces.FollowsDAO;
import edu.byu.cs.tweeter.server.daoInterfaces.UsersDAO;
import edu.byu.cs.tweeter.server.service.RegisterService;

public class RegisterHandler implements RequestHandler<RegisterRequest, LoginResponse> {
    public LoginResponse handleRequest(RegisterRequest request, Context context) {
        RegisterService RegisterService = new RegisterService();
        DynamoDAOFactory factory = new DynamoDAOFactory();
        return RegisterService.register(request,factory);
    }
}
