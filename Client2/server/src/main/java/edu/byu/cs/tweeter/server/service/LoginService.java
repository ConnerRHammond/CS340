package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;
import edu.byu.cs.tweeter.server.dao.DynamoDAOFactory;
import edu.byu.cs.tweeter.server.daoInterfaces.AuthtokenDAO;
import edu.byu.cs.tweeter.server.daoInterfaces.DAOFactory;
import edu.byu.cs.tweeter.server.daoInterfaces.UsersDAO;
import edu.byu.cs.tweeter.server.util.RandomIDGenerator;
import edu.byu.cs.tweeter.model.domain.AuthToken;

public class LoginService extends AuthenticationService {

    public LoginService(LoginRequest loginRequest){
        super(loginRequest);
    }
    public LoginResponse login(DAOFactory factory){
        factory = new DynamoDAOFactory();
        UsersDAO usersDAO = factory.getUserDAO();
        AuthtokenDAO authtokenDAO = factory.getAuthtokenDAO();
            try{
                User loggedInUser = usersDAO.getUser(getUsername(), getPassword());
                if (loggedInUser != null) {
                    String token = RandomIDGenerator.createId(10);
                    authtokenDAO.createToken(loggedInUser.getAlias(), token);
                    return new LoginResponse(loggedInUser,new AuthToken(token));
                }
                System.out.print("Failed" + "user not found");
                return new LoginResponse("Failed" + "user not found");
            }catch (Exception e){
                System.out.print("Failed caught an exception:" + e.toString());
                return new LoginResponse("Failed"+e.getMessage() +"Exception thrown");
            }
       }
}
