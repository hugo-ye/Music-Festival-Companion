package use_case.save_event_to_list;

import entity.Event;
import entity.EventList;

/**
 * Interactor for the SaveEventToList use case
 * This class implements the SaveEventToListInputBoundary and is responsible for saving/adding an
 *Event to one list or more.
 *
 */
public class SaveEventToListInteractor implements SaveEventToListInputBoundary {
    private final SaveEventToListDataAccessInterface dataAccess;
    private final SaveEventToListOutputBoundary presenter;

    public SaveEventToListInteractor(SaveEventToListDataAccessInterface dataAccess,
                                     SaveEventToListOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    /**
     * Executes the "Save Event To List" use case.
     * Iterates through each Event List and adds the given Event if it is not already there.
     * @param input the input data containing the Event and the Event Lists.
     */
    @Override
    public void execute(SaveEventToListInputData input) {
        final Event event = input.getEvent();
        final EventList[] eventLists = input.getEventLists();
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

        final SaveEventToListOutputData output = new SaveEventToListOutputData(event,
                eventLists, message.toString());
        presenter.present(output);
    }
}