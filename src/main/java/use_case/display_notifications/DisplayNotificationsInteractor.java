package use_case.display_notifications;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import entity.Event;
import org.jetbrains.annotations.NotNull;
import use_case.sort_events.SortEventsOrder;
import use_case.sort_events.strategies.SortEventsByDate;

/**
 * The interactor for the display_notifications use case.
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
     * @param inputData contains the date.
     */
    @Override
    public void execute(DisplayNotificationsInputData inputData) {
        final LocalDate currDate = inputData.getLocalDate();
        final List<Event> allEvents = dataAccess.getMasterListEvents();
        if (allEvents != null) {
            final SortEventsByDate dateSorter = new SortEventsByDate();
            allEvents.sort(SortEventsOrder.ASCENDING.apply(dateSorter));
            final StringBuilder messageBuilder = new StringBuilder();
            boolean hasNotifications = false;

            for (Event e : allEvents) {
                final long dateDifference = dateCalculator(currDate, e.getDate());
                if (dateDifference >= 0 && dateDifference < DisplayNotificationsConstants.REMIND_DEADLINE) {
                    messageBuilder.append("- ")
                            .append(e.getName())
                            .append(" starts in ")
                            .append(dateDifference)
                            .append(dateDifferenceGetStr(dateDifference));
                    hasNotifications = true;
                }
            }
            if (hasNotifications) {
                messageBuilder.insert(0, "Upcoming Events:\n");
                presenter.prepareSuccessView(new DisplayNotificationsOutputData(messageBuilder.toString()));
            }
            else {
                presenter.prepareSuccessView(new DisplayNotificationsOutputData(""));
            }
        }
    }

    @NotNull
    private static String dateDifferenceGetStr(long dateDifference) {
        String result = " day.\n";
        if (dateDifference != 1) {
            result = " days.\n";
        }
        return result;
    }

    /**
     * Returns the number of days between two dates.
     *
     * @param date1 the start date
     * @param date2 the end date
     *
     * @return the number of days from {@code date1} to {@code date2}.
     */
    private long dateCalculator(LocalDate date1, LocalDate date2) {
        return ChronoUnit.DAYS.between(date1, date2);

    }
}
