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

    public void removeEventFromList(Event event, EventList eventList){
        RemoveEventFromListInputData inputData = new RemoveEventFromListInputData(event, eventList);
        interactor.removeEventFromList(inputData);
    }
}
