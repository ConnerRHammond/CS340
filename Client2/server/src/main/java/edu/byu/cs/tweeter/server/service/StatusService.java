package edu.byu.cs.tweeter.server.service;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.request.StatusRequest;

import edu.byu.cs.tweeter.model.net.response.StatusResponse;
import edu.byu.cs.tweeter.server.dao.DynamoDAOFactory;
import edu.byu.cs.tweeter.server.dao.StatusList;
import edu.byu.cs.tweeter.server.daoInterfaces.AuthtokenDAO;
import edu.byu.cs.tweeter.server.daoInterfaces.DAOFactory;
import edu.byu.cs.tweeter.server.daoInterfaces.StatusDAO;
import edu.byu.cs.tweeter.util.Pair;

public class StatusService extends BaseService{


    public StatusResponse getStory(StatusRequest request,DAOFactory factory) {
        StatusDAO storyDAO = factory.getStoryDAO();
        AuthtokenDAO authtokenDAO = factory.getAuthtokenDAO();
        return  getStatuses(storyDAO,request,authtokenDAO);
    }
    public StatusResponse getFeed(StatusRequest request,DAOFactory factory) {
        StatusDAO feedDAO = factory.getFeedDAO();
        AuthtokenDAO authtokenDAO = factory.getAuthtokenDAO();
        return  getStatuses(feedDAO,request,authtokenDAO);
    }
    StatusResponse getStatuses(StatusDAO dao , StatusRequest request,AuthtokenDAO authtokenDAO){
        try {
            if (checkDate(authtokenDAO.getTokenTime(request.getAuthToken().getToken()))) {
                if (request.getItem() == null) {
                    Pair<ArrayList<Status>, Boolean> pair = dao.getStatuses(request.getTarget(),null);
                return new StatusResponse(pair.getFirst(), pair.getSecond());
                }else{
                    ArrayList<String> queryList = new ArrayList<>();
                    queryList.add(request.getItem().getDate());
                    Pair<ArrayList<Status>, Boolean> pair = dao.getStatuses(request.getTarget(),queryList);
                    return new StatusResponse(pair.getFirst(), pair.getSecond());
                }
            }
            return new StatusResponse(false, "failed");
        }catch (Exception e){
            return new StatusResponse(false, "exception throw:"+ e.toString());
        }
    }

}
