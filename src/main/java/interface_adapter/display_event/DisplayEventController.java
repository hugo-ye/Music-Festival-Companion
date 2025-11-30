package interface_adapter.display_event;

import entity.Event;
import use_case.display_event.DisplayEventInputBoundary;
import use_case.display_event.DisplayEventInputData;

/**
 * Controller for the DisplayEvent use case.
 */
public class DisplayEventController {

    private final DisplayEventInputBoundary displayEventInteractor;

    public DisplayEventController(DisplayEventInputBoundary displayEventInteractor) {
        this.displayEventInteractor = displayEventInteractor;
    }

    /**
     * Executes the DisplayEvent action by passing the given event to the interactor.
     * @param event the event that is going to be displayed.
     */
    public void execute(Event event){
        final DisplayEventInputData inputData = new DisplayEventInputData(event);
        displayEventInteractor.execute(inputData);
    }
}
