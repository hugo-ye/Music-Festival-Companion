package use_case.display_notifications;

import entity.Event;
import use_case.sort_events.SortEventsOrder;
import use_case.sort_events.strategies.SortEventsByDate;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * The {@code DisplayNotificationsInteractor} generates notification messages for upcoming events based on the current
 * date.
 */
public class DisplayNotificationsInteractor implements DisplayNotificationsInputBoundary {
    private final DisplayNotificationsDataAccessInterface dataAccess;
    private final DisplayNotificationsOutputBoundary presenter;

    public DisplayNotificationsInteractor(DisplayNotificationsDataAccessInterface dataAccess,
                                          DisplayNotificationsOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    /**
     * Executes the display_notifications use case.
     * The method does the following: Fetches all events from the master list, sorts them by date, Builds a message for
     * events occurring within the reminder window, and Sends an empty message if no reminders apply.
     *
     * @param inputData contains the date.
     */
    @Override
    public void execute(DisplayNotificationsInputData inputData) {
        final LocalDate currDate = inputData.getLocalDate();
        final List<Event> allEvents = dataAccess.getMasterListEvents();
        if (allEvents == null) {
            return;
        }

        final SortEventsByDate dateSorter = new SortEventsByDate();
        allEvents.sort(SortEventsOrder.ASCENDING.apply(dateSorter));

        StringBuilder messageBuilder = new StringBuilder();
        boolean hasNotifications = false;

        for (Event e : allEvents) {
            long dateDifference = dateCalculator(currDate, e.getDate());

            if (dateDifference >= 0 && dateDifference < DisplayNotificationsConstants.REMIND_DEADLINE) {
                messageBuilder.append("- ")
                        .append(e.getName())
                        .append(" starts in ")
                        .append(dateDifference)
                        .append(dateDifference == 1 ? " day.\n" : " days.\n");
                hasNotifications = true;
            }
        }

        if (hasNotifications) {
            messageBuilder.insert(0, "Upcoming Events:\n");
            presenter.prepareSuccessView(new DisplayNotificationsOutputData(messageBuilder.toString()));
        } else {
            presenter.prepareSuccessView(new DisplayNotificationsOutputData(""));
        }
    }

    /**
     * Returns the number of days between two dates.
     *
     * @param d1 the start date
     * @param d2 the end date
     *
     * @return the number of days from {@code d1} to {@code d2}.
     */
    private long dateCalculator(LocalDate d1, LocalDate d2) {
        return ChronoUnit.DAYS.between(d1, d2);
    }
}
