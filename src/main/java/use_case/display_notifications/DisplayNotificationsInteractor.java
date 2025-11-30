package use_case.display_notifications;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import entity.Event;
import use_case.sort_events.SortEventsOrder;
import use_case.sort_events.strategies.SortEventsByDate;

public class DisplayNotificationsInteractor implements DisplayNotificationsInputBoundary {
    private final DisplayNotificationsDataAccessInterface dataAccess;
    private final DisplayNotificationsOutputBoundary presenter;

    public DisplayNotificationsInteractor(DisplayNotificationsDataAccessInterface dataAccess,
                                          DisplayNotificationsOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(DisplayNotificationsInputData inputData) {
        final LocalDate currDate = inputData.getLocalDate();
        final List<Event> allEvents = dataAccess.getMasterListEvents();
        if (allEvents != null) {
            executeHelper(allEvents, currDate);
        }
    }

    private void executeHelper(List<Event> allEvents, LocalDate currDate) {
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
                        .append(dateDifference == 1 ? " day.\n" : " days.\n");
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

    private long dateCalculator(LocalDate date1, LocalDate date2) {
        return ChronoUnit.DAYS.between(date1, date2);
    }
}
