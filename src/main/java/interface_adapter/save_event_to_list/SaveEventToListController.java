package interface_adapter.save_event_to_list;

import entity.Event;
import entity.EventList;
import use_case.save_event_to_list.SaveEventToListInputBoundary;
import use_case.save_event_to_list.SaveEventToListInputData;

/**
 * Controller for the SaveEventToList use case
 */
public class SaveEventToListController{
    private final SaveEventToListInputBoundary interactor;

    public SaveEventToListController(SaveEventToListInputBoundary interactor) {
        this.interactor = interactor;
    }
    /**
     * Saves an {@link Event} to on or more {@link EventList}.
     * <p>
     *     This method creates an {@link SaveEventToListInputData} object and then passes it to the interactor
     *     for execution
     * </p>
     * @param event the event to add to a list/s.
     * @param eventLists a list of {@link EventList} to which the event should be added.
     */
    public void execute(Event event, EventList[] eventLists){
        SaveEventToListInputData inputData = new SaveEventToListInputData(event, eventLists);
        interactor.execute(inputData);
    }
}
