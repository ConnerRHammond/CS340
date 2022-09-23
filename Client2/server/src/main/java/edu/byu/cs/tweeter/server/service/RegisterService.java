package edu.byu.cs.tweeter.server.service;

import com.amazonaws.auth.policy.actions.S3Actions;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.Region;

import java.io.ByteArrayInputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.Base64;


import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;
import edu.byu.cs.tweeter.server.dao.DynamoDAOFactory;
import edu.byu.cs.tweeter.server.daoInterfaces.AuthtokenDAO;
import edu.byu.cs.tweeter.server.daoInterfaces.DAOFactory;
import edu.byu.cs.tweeter.server.daoInterfaces.StatusDAO;
import edu.byu.cs.tweeter.server.daoInterfaces.UsersDAO;
import edu.byu.cs.tweeter.server.util.RandomIDGenerator;
import com.amazonaws.services.s3.AmazonS3;

import com.amazonaws.services.s3.AmazonS3ClientBuilder;
public class RegisterService{
    public LoginResponse register(RegisterRequest request, DAOFactory factory){
        factory = new DynamoDAOFactory();
        UsersDAO userDAO = factory.getUserDAO();
        AuthtokenDAO authtokenDAO = factory.getAuthtokenDAO();
        AmazonS3 s3 = AmazonS3ClientBuilder
                .standard()
                .withRegion("us-west-1")
                .build();

        byte[] array = Base64.getDecoder().decode((request.getImage().substring(request.getImage().indexOf(",") + 1)));
        ByteArrayInputStream stream = new ByteArrayInputStream(array);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(array.length);

        PutObjectRequest putRequest = new PutObjectRequest("crhcs340",request.getFirstName(),
                stream,metadata);
        PutObjectResult result = s3.putObject(putRequest);
        s3.setObjectAcl("crhcs340", request.getFirstName(), CannedAccessControlList.PublicRead);
        String url = s3.getUrl("crhcs340",request.getFirstName()).toString();


        userDAO.createUser(request.getUsername(),request.getFirstName(),request.getLastName(),url,request.getPassword());

        User loggedInUser = userDAO.getUser(request.getUsername(),request.getPassword());
        String token = RandomIDGenerator.createId(10);
        authtokenDAO.createToken(loggedInUser.getAlias(), token);

        return new LoginResponse(loggedInUser,new AuthToken(token));
    }
}
