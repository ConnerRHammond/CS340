package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.GetUserRequest;
import edu.byu.cs.tweeter.model.net.response.GetUserResponse;
import edu.byu.cs.tweeter.server.dao.DynamoDAOFactory;
import edu.byu.cs.tweeter.server.daoInterfaces.AuthtokenDAO;
import edu.byu.cs.tweeter.server.daoInterfaces.DAOFactory;
import edu.byu.cs.tweeter.server.daoInterfaces.UsersDAO;

public class GetUserService extends BaseService{
    public GetUserResponse getUser(GetUserRequest request, DAOFactory factory){
        UsersDAO usersDAO = factory.getUserDAO();
        AuthtokenDAO authtokenDAO = factory.getAuthtokenDAO();
        if(checkDate(authtokenDAO.getTokenTime(request.getAuthToken().getToken()))){
            User user = usersDAO.getUser(request.getAlias(),null);
            GetUserResponse response = new GetUserResponse(user);
            return response;
        }
        return new GetUserResponse("Failed");
    }
}
