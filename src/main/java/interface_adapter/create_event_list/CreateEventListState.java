package interface_adapter.create_event_list;

import entity.EventList;

import java.util.ArrayList;
import java.util.List;

public class CreateEventListState {
    private String listId;
    private String listName = "";
    private String errorMessage = "";
    // All user-created lists that the UI should show
    private List<EventList> lists = new ArrayList<>();


    public CreateEventListState() {}

    public String getListName() {
        return listName;
    }

    public String getListId() {
        return listId;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
    public List<EventList> getLists() {
        return lists;
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

    public void setLists(List<EventList> lists) {
        this.lists = lists;
    }

    public EventList getListById(String id) {
        for (EventList list : lists) {
            if (list.getId().equals(id)) {
                return list;
            }
        }
        return null;
    }
}