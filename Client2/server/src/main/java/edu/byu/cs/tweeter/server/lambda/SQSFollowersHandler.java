package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.server.dao.DynamoFollowDAO;
import edu.byu.cs.tweeter.server.daoInterfaces.FollowsDAO;
import edu.byu.cs.tweeter.server.service.SQSFollowerService;
import edu.byu.cs.tweeter.server.util.JsonSerializer;

public class SQSFollowersHandler implements RequestHandler<SQSEvent, Void> {

    @Override
    public Void handleRequest(SQSEvent event, Context context) {
        SQSFollowerService service = new SQSFollowerService();
        FollowsDAO followsDAO = new DynamoFollowDAO();
        for (SQSEvent.SQSMessage msg : event.getRecords()) {
            String json = msg.getBody();
            Status status = JsonSerializer.deserialize(json,Status.class);
            service.gatherFollowersForFeed(json,status.getUser().getAlias(),status.getDate(),followsDAO);
        }
        return null;
    }
}
