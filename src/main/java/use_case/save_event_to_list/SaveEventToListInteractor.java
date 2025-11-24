package use_case.save_event_to_list;

import entity.Event;
import entity.EventList;

public class SaveEventToListInteractor implements SaveEventToListInputBoundary{
    private final SaveEventToListDataAccessInterface dataAccess;
    private final SaveEventToListOutputBoundary presenter;

    public SaveEventToListInteractor(SaveEventToListDataAccessInterface dataAccess, SaveEventToListOutputBoundary preseneter) {
        this.dataAccess = dataAccess;
        this.presenter = preseneter;
    }

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
