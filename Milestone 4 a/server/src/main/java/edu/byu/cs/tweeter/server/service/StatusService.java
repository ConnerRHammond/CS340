package edu.byu.cs.tweeter.server.service;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.request.StatusRequest;

import edu.byu.cs.tweeter.model.net.response.StatusResponse;
import edu.byu.cs.tweeter.server.dao.DynamoDAOFactory;
import edu.byu.cs.tweeter.server.daoInterfaces.AuthtokenDAO;
import edu.byu.cs.tweeter.server.daoInterfaces.DAOFactory;
import edu.byu.cs.tweeter.server.daoInterfaces.StatusDAO;
import edu.byu.cs.tweeter.util.Pair;

public class StatusService extends BaseService{


    public StatusResponse getStory(StatusRequest request,DAOFactory factory) {
        factory = new DynamoDAOFactory();
        StatusDAO storyDAO = factory.getStoryDAO();
        AuthtokenDAO authtokenDAO = factory.getAuthtokenDAO();
        return  getStatuses(storyDAO,request,authtokenDAO);
    }
    public StatusResponse getFeed(StatusRequest request,DAOFactory factory) {
        factory = new DynamoDAOFactory();
        StatusDAO feedDAO = factory.getFeedDAO();
        AuthtokenDAO authtokenDAO = factory.getAuthtokenDAO();
        return  getStatuses(feedDAO,request,authtokenDAO);
    }
    StatusResponse getStatuses(StatusDAO dao , StatusRequest request,AuthtokenDAO authtokenDAO){
        try {
            DAOFactory factory = new DynamoDAOFactory();
            ArrayList<Status> newList = new ArrayList<>();
            boolean hasMorePages = false;
            if (checkDate(authtokenDAO.getTokenTime(request.getAuthToken().getToken()))) {
                ArrayList<Status> list = dao.getStatuses(request.getTarget()).getArray();
                if (request.getItem() == null) {
                    if (list.size() > 0) {
                        int j = list.size() - 10 >= 0 ? 10 : list.size();
                        for (int i = 0; i < j; i++) {
                            newList.add(list.get(i));
                        }
                        hasMorePages = list.size() - 10 > 0 ? true : false;
                    }
                } else {
                    int index = list.indexOf(request.getItem()) + 1;
                    if (list.size() - index - 1 > 0) {
                        int j = list.size() - (index + 10) >= 0 ? 10 : list.size() - index;
                        for (int i = index; i < index + j; i++) {
                            newList.add(list.get(i));
                        }
                        hasMorePages = list.size() - (index + j) > 0 ? true : false;
                    }
                }
                return new StatusResponse(newList, hasMorePages);
            }

            return new StatusResponse(false, "failed");
        }catch (Exception e){
            return new StatusResponse(false, "exception throw:"+ e.toString());
        }
    }
}
