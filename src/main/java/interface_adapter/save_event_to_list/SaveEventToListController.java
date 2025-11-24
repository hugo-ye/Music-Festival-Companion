package interface_adapter.save_event_to_list;

import entity.Event;
import entity.EventList;
import use_case.save_event_to_list.SaveEventToListInputBoundary;
import use_case.save_event_to_list.SaveEventToListInputData;

public class SaveEventToListController{
    private final SaveEventToListInputBoundary interactor;

    public SaveEventToListController(SaveEventToListInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(Event event, EventList[] eventLists){
        SaveEventToListInputData inputData = new SaveEventToListInputData(event, eventLists);
        interactor.execute(inputData);
    }
}
