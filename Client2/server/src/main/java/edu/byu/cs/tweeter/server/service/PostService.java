package edu.byu.cs.tweeter.server.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.response.Response;
import edu.byu.cs.tweeter.server.dao.DynamoDAOFactory;
import edu.byu.cs.tweeter.server.daoInterfaces.AuthtokenDAO;
import edu.byu.cs.tweeter.server.daoInterfaces.DAOFactory;
import edu.byu.cs.tweeter.server.daoInterfaces.StatusDAO;
import edu.byu.cs.tweeter.util.Pair;

public class PostService extends BaseService {
    public Response doPost(PostStatusRequest input, DAOFactory factory){
        AuthtokenDAO authtokenDAO = factory.getAuthtokenDAO();
        StatusDAO storyDAO = factory.getStoryDAO();
        input.getStatus().getUser().setImageBytes(null);
        String json = new Gson().toJson(input.getStatus());
        Pair<String, Double> authtoken = authtokenDAO.getAliasToken(input.getAuthToken().getToken());
//        if( checkDate(authtoken.getSecond())){

            List<String> handlerAlias = new ArrayList<String>();
            handlerAlias.add(authtoken.getFirst());
            storyDAO.postStatus(json,handlerAlias,input.getDate());
//            String queueUrl = "https://sqs.us-west-1.amazonaws.com/818838503642/PostFollowQue";
//
//            SendMessageRequest send_msg_request = new SendMessageRequest()
//                    .withQueueUrl(queueUrl)
//                    .withMessageBody(json)
//                    .withDelaySeconds(5);
//
//            AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
//            sqs.sendMessage(send_msg_request);
            return new Response(true,"Post Successful");
//        }
//        else {
//             return new Response(false,"Failed to authenticate authotoken");
//        }

    }
}

