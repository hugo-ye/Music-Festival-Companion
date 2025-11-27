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
public class SaveEventToListInteractor implements SaveEventToListInputBoundary {
    private final SaveEventToListDataAccessInterface dataAccess;
    private final SaveEventToListOutputBoundary presenter;

    public SaveEventToListInteractor(SaveEventToListDataAccessInterface dataAccess, SaveEventToListOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
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

        for (EventList eventList : eventLists) {
            if (eventList.getEvents().contains(event)) {
                message.append("Event is already in ")
                        .append(eventList.getName())
                        .append(".\n");
            } else {
                dataAccess.saveEventToList(event, eventList);
                message.append("Event added to ")
                        .append(eventList.getName())
                        .append(".\n");
            }
        }

        SaveEventToListOutputData output = new SaveEventToListOutputData(event, eventLists, message.toString());
        presenter.present(output);
    }
}
