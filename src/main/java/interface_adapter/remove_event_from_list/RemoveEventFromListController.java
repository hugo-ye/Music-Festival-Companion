package interface_adapter.remove_event_from_list;

import entity.Event;
import entity.EventList;
import use_case.remove_event_from_list.RemoveEventFromListInputBoundary;
import use_case.remove_event_from_list.RemoveEventFromListInputData;

public class RemoveEventFromListController {
    private final RemoveEventFromListInputBoundary interactor;

    public RemoveEventFromListController(RemoveEventFromListInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Method of RemoveEventFromList to use its interactor.
     * @param event target event to be removed.
     * @param eventList target eventList to remove event from.
     */
    public void execute(Event event, EventList eventList) {
        final RemoveEventFromListInputData inputData = new RemoveEventFromListInputData(event, eventList);
        interactor.execute(inputData);
    }
}
