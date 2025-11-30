package use_case.attend_event;

import entity.Event;

/**
 * The {@code AttendEventInteractor} checks whether the user
 * is already attending a given event and,
 * based on the results, updates the MasterList.
 */
public class AttendEventInteractor implements AttendEventInputBoundary {
    private final AttendEventDataAccessInterface attendEventDataAccessInterface;
    private final AttendEventOutputBoundary attendEventOutputBoundary;

    public AttendEventInteractor(AttendEventDataAccessInterface attendEventDataAccessInterface,
                                 AttendEventOutputBoundary attendEventOutputBoundary) {
        this.attendEventDataAccessInterface = attendEventDataAccessInterface;
        this.attendEventOutputBoundary = attendEventOutputBoundary;
    }

    /**
     * saves an event to the master list.
     * @param inputData the event to add
     */
    @Override
    public void execute(AttendEventInputData inputData) {
        final Event event = inputData.getEvent();
        final boolean added = !attendEventDataAccessInterface.alreadyAttends(event);

        final AttendEventOutputData outputData = new AttendEventOutputData(event);
        if (added) {
            attendEventDataAccessInterface.saveEventToMasterList(event);
            attendEventOutputBoundary.prepareSuccessView(outputData);
        } else {
            attendEventOutputBoundary.prepareFailView("Event already Attended");
        }
    }

}
