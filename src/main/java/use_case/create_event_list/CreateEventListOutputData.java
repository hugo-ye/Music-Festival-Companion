package use_case.create_event_list;

public class CreateEventListOutputData {

    private final String listId;
    private final String listName;

    public CreateEventListOutputData(String listId, String listName) {
        this.listId = listId;
        this.listName = listName;
    }

    public String getListId() {
        return listId;
    }

    public String getListName() {
        return listName;
    }
}
