package use_case.save_event_to_list;

import entity.Event;
import entity.EventList;

/**
 * Interactor for the SaveEventToList use case
 *
 * <p>
 *     This class implements the {@link SaveEventToListInputBoundary} and is responsible for saving/adding an
 *     {@link Event} to one list or more.
 * </p>
 */
public class SaveEventToListInteractor implements SaveEventToListInputBoundary{
    private final SaveEventToListDataAccessInterface dataAccess;
    private final SaveEventToListOutputBoundary presenter;

    public SaveEventToListInteractor(SaveEventToListDataAccessInterface dataAccess, SaveEventToListOutputBoundary preseneter) {
        this.dataAccess = dataAccess;
        this.presenter = preseneter;
    }

    /**
     * Executes the "Save Event To List" use case.
     * <p>
     *     iterates through each {@link EventList}, and adds the given {@link Event} if its not already there.
     * </p>
     * @param inputData the input data containing the {@link Event} and the eventLists.
     */
    @Override
    public void execute(SaveEventToListInputData inputData) {
        Event event = inputData.getEvent();
        EventList[] eventLists = inputData.getEventLists();
        StringBuilder message = new StringBuilder();

        // search all eventList, if Event is not in eventList, then add it
        // since when an Event is added into an arrayList, adding process is omitted if arrayList already contain the
        // Event
        for (EventList eventList : eventLists) {
            dataAccess.saveEventToList(event, eventList);
            message.append("Event added into").append(eventList.getName()).append("\n");
        }

        SaveEventToListOutputData output= new SaveEventToListOutputData(event, eventLists, message.toString());

        presenter.present(output);
    }
}
