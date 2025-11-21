package interface_adapter.display_event;

import entity.Event;
import use_case.display_event.DisplayEventInputBoundary;
import use_case.display_event.DisplayEventInputData;

public class DisplayEventController {

    private final DisplayEventInputBoundary displayEventInteractor;

    public DisplayEventController(DisplayEventInputBoundary displayEventInteractor) {
        this.displayEventInteractor = displayEventInteractor;
    }


    public void execute(Event event){
        final DisplayEventInputData inputData = new DisplayEventInputData(event);
        displayEventInteractor.execute(inputData);
    }
}
