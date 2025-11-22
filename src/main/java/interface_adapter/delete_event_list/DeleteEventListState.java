package interface_adapter.delete_event_list;

public class DeleteEventListState {
    private String listId = "";
    private String errorMessage = "";

    public DeleteEventListState(DeleteEventListState copy) {
        this.listId = copy.listId;
        this.errorMessage = copy.errorMessage;
    }

    public DeleteEventListState() {
    }

    // Getter
    public String getListId() {
        return listId;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    // Setter
    public void setListId(String listId) {
        this.listId = listId;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
