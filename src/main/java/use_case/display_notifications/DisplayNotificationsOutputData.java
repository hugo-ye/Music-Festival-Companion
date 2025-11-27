package use_case.display_notifications;

public class DisplayNotificationsOutputData {
    private final String message;

    public DisplayNotificationsOutputData(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
