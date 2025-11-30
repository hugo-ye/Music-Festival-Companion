package interface_adapter.attend_event;

import use_case.attend_event.AttendEventInputBoundary;
import entity.Event;
import use_case.attend_event.AttendEventInputData;

/**
 * Controller for the AttendEvent use case.
 */
public class AttendEventController {
    private final AttendEventInputBoundary attendEventInteractor;

    public AttendEventController(AttendEventInputBoundary attendEventInputInteractor){
        this.attendEventInteractor = attendEventInputInteractor;
    }

    /**
     * Executes the AttendEvent by passing the provided event to the interactor.
     * @param event the event the user wants to attend
     */
    public void execute(Event event){
        AttendEventInputData attendEventInputData = new AttendEventInputData(event);
        attendEventInteractor.execute(attendEventInputData);
    }
}
