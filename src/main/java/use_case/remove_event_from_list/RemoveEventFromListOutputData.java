package use_case.remove_event_from_list;

public class RemoveEventFromListOutputData {
    private final String message;
    private final String targetListId;

    public RemoveEventFromListOutputData(String message, String targetListId) {
        this.message = message;
        this.targetListId = targetListId;
    }

    public String getMessage() {
        return message;
    }

    public String getTargetListId() {
        return targetListId;
    }
}
