package use_case.attend_event;

import entity.Event;
import entity.User;

import javax.swing.*;

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
        String username = inputData.getUsername();
        // assuming that FileUserData... has a getByUsername that returns a User given a username

        User user = attendEventDataAccessInterface.getByUsername(username);

        Event event = inputData.getEvent();
        AttendEventOutputData outputData = new AttendEventOutputData(user, event);
        if(!attendEventDataAccessInterface.alreadyAttends(user, event)){
            // do we do this
            // user.getMasterList().addEvent(event);
            // or this.
            // attendEventDataAccessInterface.saveAttend(user, event);
            attendEventOutputBoundary.prepareSuccessView(outputData);
        }else{
            attendEventOutputBoundary.prepareFailView(outputData);
        }
    }

}
