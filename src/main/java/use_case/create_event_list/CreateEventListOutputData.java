package use_case.create_event_list;

import entity.EventList;

public class CreateEventListOutputData {

    private final String listId;
    private final String listName;
    private final EventList newList;

    public CreateEventListOutputData(String listId, String listName, EventList newList) {
        this.listId = listId;
        this.listName = listName;
        this.newList = newList;
    }

    public String getListId() {
        return listId;
    }

    public String getListName() {
        return listName;
    }

    public EventList getNewList() {
        return newList;
    }
}