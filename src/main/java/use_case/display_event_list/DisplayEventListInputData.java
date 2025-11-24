package use_case.display_event_list;

public class DisplayEventListInputData {
    private final String listId;

    public DisplayEventListInputData(String listId) {
        this.listId = listId;
    }

    public String getListId() {
        return listId;
    }
}
