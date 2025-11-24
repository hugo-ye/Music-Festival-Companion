package use_case.remove_event_from_list;

public class RemoveEventFromListOutputData {
    private final String message;


    public RemoveEventFromListOutputData(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
