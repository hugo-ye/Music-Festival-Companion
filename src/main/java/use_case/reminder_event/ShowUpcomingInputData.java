package use_case.reminder_event;

import java.time.LocalDate;

/* Request model for the use case. */
public final class ShowUpcomingInputData {
    private final LocalDate now;
    private final int windowDays;

    public ShowUpcomingInputData(LocalDate now, int windowDays) {
        this.now = now;
        this.windowDays = windowDays;
    }

    public LocalDate getNow()        { return now; }
    public int       getWindowDays() { return windowDays; }
}
