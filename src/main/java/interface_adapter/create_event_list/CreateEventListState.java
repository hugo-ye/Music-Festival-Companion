package interface_adapter.create_event_list;

import java.util.ArrayList;
import java.util.List;

public class CreateEventListState {

    private String listId;
    private String listName = "";
    private String errorMessage = "";

    private final List<EventListSummary> lists = new ArrayList<>();

    public CreateEventListState() {
    }

    public String getListName() {
        return listName;
    }

    public String getListId() {
        return listId;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Replaces the current list of event summaries.
     *
     * @param lists the new list of event summaries
     */
    public void setLists(List<EventListSummary> lists) {
        this.lists.clear();
        this.lists.addAll(lists);
    }

    public List<EventListSummary> getLists() {
        return new ArrayList<>(lists);
    }
}
