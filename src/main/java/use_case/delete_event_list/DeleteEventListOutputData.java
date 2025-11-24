package use_case.delete_event_list;

public class DeleteEventListOutputData {

    private final String listId;

    public DeleteEventListOutputData(String listId) {
        this.listId = listId;
    }

    public String getListId() {
        return listId;
    }
}