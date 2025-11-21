package interface_adapter.attend_event;

import use_case.attend_event.AttendEventInputBoundary;
import entity.Event;
import use_case.attend_event.AttendEventInputData;

public class AttendEventController {
    private final AttendEventInputBoundary attendEventInteractor;

    public AttendEventController(AttendEventInputBoundary attendEventInputInteractor){
        this.attendEventInteractor = attendEventInputInteractor;
    }

    public void execute(Event event){
        AttendEventInputData attendEventInputData = new AttendEventInputData(event);
        attendEventInteractor.execute(attendEventInputData);
    }
}
