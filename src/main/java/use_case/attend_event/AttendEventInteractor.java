package use_case.attend_event;

import entity.Event;

/**
 * Interactor for the Attend Event Use Case
 * Checks whether the user is already attending a given event and,
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
     * Saves an event to the Master List.
     * @param input the event to add
     */
    @Override
    public void execute(AttendEventInputData input) {
        final Event event = input.getEvent();
        final boolean added = !attendEventDataAccessInterface.alreadyAttends(event);

        final AttendEventOutputData outputData = new AttendEventOutputData(event);
        if (added) {
            attendEventDataAccessInterface.saveEventToMasterList(event);
            attendEventOutputBoundary.prepareSuccessView(outputData);
        }
        else {
            attendEventOutputBoundary.prepareFailView("Event already Attended");
        }
    }
}
