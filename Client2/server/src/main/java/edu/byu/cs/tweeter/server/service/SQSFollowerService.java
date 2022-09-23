package edu.byu.cs.tweeter.server.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.model.net.request.FeedSQSMessage;
import edu.byu.cs.tweeter.server.daoInterfaces.FollowsDAO;
import edu.byu.cs.tweeter.server.util.JsonSerializer;
import edu.byu.cs.tweeter.util.Pair;

public class SQSFollowerService {
    public void gatherFollowersForFeed(String json, String alias, String date, FollowsDAO dao){
        boolean hasMorePages = false;
        ArrayList key = null;
        do {
            Pair<List<String>, Boolean> pair = dao.getFollowers(alias, 25, key);
            hasMorePages = pair.getSecond();
            if(hasMorePages){
                if(key == null){
                    key = new ArrayList();
                }
                key.add(0,alias);
                key.add(1,pair.getFirst().get(pair.getFirst().size()-1));
            }
            String queueUrl = "https://sqs.us-west-1.amazonaws.com/818838503642/PostFeedQue";
                SendMessageRequest send_msg_request = new SendMessageRequest()
                        .withQueueUrl(queueUrl)
                        .withMessageBody(JsonSerializer.serialize(new FeedSQSMessage(pair.getFirst(),json,date)))
                        .withDelaySeconds(5);
                AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
                sqs.sendMessage(send_msg_request);
        }while (hasMorePages);
    }
}
