package interface_adapter.create_event_list;

import entity.EventList;

    // For information that affects the UI
    // 1. Input name of the event list
    // 2. Output new created List
    // 3. Message from interactor (Checking whether event names are duplicated)

public class CreateEventListViewModel {
    private String listId;
    private String listName;
    private String message;

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}