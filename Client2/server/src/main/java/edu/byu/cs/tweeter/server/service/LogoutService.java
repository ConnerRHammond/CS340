package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.net.request.AuthTokenRequest;
import edu.byu.cs.tweeter.model.net.request.AuthenticatedRequest;
import edu.byu.cs.tweeter.model.net.response.Response;
import edu.byu.cs.tweeter.server.dao.DynamoAuthtokenDAO;
import edu.byu.cs.tweeter.server.dao.DynamoDAOFactory;
import edu.byu.cs.tweeter.server.daoInterfaces.AuthtokenDAO;
import edu.byu.cs.tweeter.server.daoInterfaces.DAOFactory;

public class LogoutService{
    public Response logOut(AuthTokenRequest request, DAOFactory factory){
        factory = new DynamoDAOFactory();
        AuthtokenDAO authtokenDAO = new DynamoAuthtokenDAO();
        authtokenDAO.removeToken(request.getAuthToken().getToken());
        return new Response(true,"Log out successful");
    }

    public static void main(String[] args) {
        DynamoDAOFactory factory = new DynamoDAOFactory();
        AuthtokenDAO authtokenDAO = new DynamoAuthtokenDAO();
        System.out.print(authtokenDAO.removeToken("rrb684C0jF"));
    }
}
