package interface_adapter.attend_event;

import use_case.attend_event.AttendEventInputBoundary;
import entity.Event;
import use_case.attend_event.AttendEventInputData;

public class AttendEventController {
    private final AttendEventInputBoundary attendEventInteractor;

    public AttendEventController(AttendEventInputBoundary attendEventInputInteractor){
        this.attendEventInteractor = attendEventInputInteractor;
    }

    public void execute(String username, Event event){
        AttendEventInputData attendEventInputData = new AttendEventInputData(username, event);
        attendEventInteractor.execute(attendEventInputData);
    }
}
