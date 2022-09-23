package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;

import edu.byu.cs.tweeter.model.net.request.FeedSQSMessage;
import edu.byu.cs.tweeter.server.dao.DynamoFeedDAO;
import edu.byu.cs.tweeter.server.daoInterfaces.StatusDAO;
import edu.byu.cs.tweeter.server.service.SQSFeedService;
import edu.byu.cs.tweeter.server.util.JsonSerializer;

public class SQSFeedHandler implements RequestHandler<SQSEvent, Void> {

    @Override
    public Void handleRequest(SQSEvent event, Context context) {
        StatusDAO dao = new DynamoFeedDAO();

        SQSFeedService service = new SQSFeedService();
        for (SQSEvent.SQSMessage msg : event.getRecords()) {
            String json = msg.getBody();
            FeedSQSMessage message = JsonSerializer.deserialize(json, FeedSQSMessage.class);
            service.batchWrite(message,dao);
        }
        return null;
    }

}
