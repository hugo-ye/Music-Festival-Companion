package use_case.delete_event_list;

public class DeleteEventListInputData {

    private final String listId;

    public DeleteEventListInputData(String listId) {
        this.listId = listId;
    }

    public String getListId() {
        return listId;
    }
}
