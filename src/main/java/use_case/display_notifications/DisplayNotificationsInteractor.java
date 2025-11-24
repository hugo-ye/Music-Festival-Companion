package use_case.display_notifications;

import entity.Event;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class DisplayNotificationsInteractor implements DisplayNotificationsInputBoundary{
    private final DisplayNotificationsDataAccessInterface dataAccess;
    private final DisplayNotificationsOutputBoundary presenter;


    public DisplayNotificationsInteractor(DisplayNotificationsDataAccessInterface dataAccess, DisplayNotificationsOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(DisplayNotificationsInputData inputData) {
        LocalDate currData = inputData.getLocalDate();

        List<Event> allEvent = dataAccess.getMasterListEvents();
        List<Event> recentEvents = new ArrayList<>();

        StringBuilder message = new StringBuilder();
        message.append("recent events:").append("\n");
        for(Event e: allEvent){
            long dateDifference = dateCalculator(currData, e.getDate());
            if(dateDifference < 0){
                continue;
            }
            if(dateDifference < DisplayNotificationsConstants.remindDeadline){
                message.append(e.getName()).append("will start in ").append(dateDifference)
                        .append("day").append("\n");
            }
        }

        DisplayNotificationsOutputData outputData = new DisplayNotificationsOutputData(message.toString());
        presenter.present(outputData);
    }

    private long dateCalculator(LocalDate d1, LocalDate d2){
        return ChronoUnit.DAYS.between(d1, d2);
    }
}
