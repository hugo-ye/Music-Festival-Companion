package use_case.attend_event;

import entity.Event;


public class AttendEventInteractor implements AttendEventInputBoundary{
    public final AttendEventDataAccessInterface attendEventDataAccessInterface;
    public final AttendEventOutputBoundary attendEventOutputBoundary;

    public AttendEventInteractor(AttendEventDataAccessInterface attendEventDataAccessInterface,
                                 AttendEventOutputBoundary attendEventOutputBoundary){
        this.attendEventDataAccessInterface = attendEventDataAccessInterface;
        this.attendEventOutputBoundary = attendEventOutputBoundary;
    }

    @Override
    public void execute(AttendEventInputData inputData) {
        Event event = inputData.getEvent();
        boolean added = !attendEventDataAccessInterface.alreadyAttends(event);

        AttendEventOutputData outputData = new AttendEventOutputData(event);
        if(added){
            attendEventDataAccessInterface.saveEventToMasterList(event);
            attendEventOutputBoundary.prepareSuccessView(outputData);
        }else {
            attendEventOutputBoundary.prepareFailView("Event already Attended");
        }
    }

}
