package use_case.display_notifications;

import java.time.LocalDate;

public class DisplayNotificationsInputData {
    private final LocalDate localDate;

    public DisplayNotificationsInputData(LocalDate localDate) {
        this.localDate = localDate;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }
}
