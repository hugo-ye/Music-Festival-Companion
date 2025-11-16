package interface_adapter.reminder_event;

import use_case.reminder_event.ShowUpcomingInputBoundary;
import use_case.reminder_event.ShowUpcomingInputData;

import java.time.LocalDate;

/* Controller translates "app loaded" into a use-case invocation. */
public final class ReminderController {
    private final ShowUpcomingInputBoundary interactor;

    public ReminderController(ShowUpcomingInputBoundary interactor) {
        this.interactor = interactor;
    }

    /* Trigger at app startup (or login success). */
    public void onAppLoaded() {
        interactor.execute(new ShowUpcomingInputData(LocalDate.now(), 7));
    }

    /* Optional manual trigger with custom window days. */
    public void triggerWithWindow(int days) {
        interactor.execute(new ShowUpcomingInputData(LocalDate.now(), days));
    }
}

