package use_case.display_notifications;

import entity.Event;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class DisplayNotificationsInteractor implements DisplayNotificationsInputBoundary {
    private final DisplayNotificationsDataAccessInterface dataAccess;
    private final DisplayNotificationsOutputBoundary presenter;

    public DisplayNotificationsInteractor(DisplayNotificationsDataAccessInterface dataAccess, DisplayNotificationsOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(DisplayNotificationsInputData inputData) {
        LocalDate currDate = inputData.getLocalDate();
        List<Event> allEvents = dataAccess.getMasterListEvents();

        StringBuilder messageBuilder = new StringBuilder();
        boolean hasNotifications = false;

        for (Event e : allEvents) {
            long dateDifference = dateCalculator(currDate, e.getDate());

            if (dateDifference >= 0 && dateDifference < DisplayNotificationsConstants.REMIND_DEADLINE) {
                messageBuilder.append("- ")
                        .append(e.getName())
                        .append(" starts in ")
                        .append(dateDifference)
                        .append(dateDifference == 1 ? " day.\n" : " days.\n"); // Grammar fix
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

    private long dateCalculator(LocalDate d1, LocalDate d2) {
        return ChronoUnit.DAYS.between(d1, d2);
    }
}