package edu.byu.cs.tweeter.model.net.response;

import java.io.Serializable;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.request.ListRequest;

/**
 * A response that can indicate whether there is more data available from the server.
 */
public class PagedResponse<T> extends Response implements Serializable {

    private boolean hasMorePages;
    private List<T> items;

    public PagedResponse(boolean success, String message) {
        super(success,message);
    }
    public PagedResponse() {
        super(false);
    }
    public PagedResponse(List<T> items, boolean hasMorePages) {
        super(true);
        this.hasMorePages = hasMorePages;
        this.items = items;
    }

    /**
     * An indicator of whether more data is available from the server. A value of true indicates
     * that the result was limited by a maximum value in the request and an additional request
     * would return additional data.
     *
     * @return true if more data is available; otherwise, false.
     */
    public boolean getHasMorePages() {
        return hasMorePages;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
