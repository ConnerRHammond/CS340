package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.net.request.FeedSQSMessage;
import edu.byu.cs.tweeter.server.daoInterfaces.StatusDAO;

public class SQSFeedService {
    public void batchWrite(FeedSQSMessage message, StatusDAO dao){
        dao.postStatus(message.getPost(),message.getAliases(),message.getDate());
    }
}
