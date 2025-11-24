package use_case.save_event_to_list;

import entity.Event;
import entity.EventList;

public class SaveEventToListInteractor implements SaveEventToListInputBoundary {
    private final SaveEventToListDataAccessInterface dataAccess;
    private final SaveEventToListOutputBoundary presenter;

    public SaveEventToListInteractor(SaveEventToListDataAccessInterface dataAccess, SaveEventToListOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

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