package use_case.create_event_list;

public class CreateEventListInputData {
    private final String listName;

    public CreateEventListInputData(String listName) {
        this.listName = listName;
    }

    public String getListName() {
        return listName;
    }
}

// Users only has to provide list name for creating an Event List