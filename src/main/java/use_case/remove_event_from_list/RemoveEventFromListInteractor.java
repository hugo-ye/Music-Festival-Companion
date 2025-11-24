package use_case.remove_event_from_list;

import entity.Event;
import entity.EventList;

public class RemoveEventFromListInteractor implements RemoveEventFromListInputBoundary{
    private final RemoveEventFromListDataAccessInterface dataAccess;
    private final RemoveEventFromListOutputBoundary presenter;

    public RemoveEventFromListInteractor(RemoveEventFromListDataAccessInterface dataAccess, RemoveEventFromListOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }


    @Override
    public void removeEventFromList(RemoveEventFromListInputData inputData) {
        Event removedEvent = inputData.getRemovedEvent();
        EventList targetEvent = inputData.getTargetEventList();

        dataAccess.removeEventFromList(removedEvent, targetEvent);

        RemoveEventFromListOutputData outputData = new RemoveEventFromListOutputData("Event removed");
        presenter.present(outputData);
    }
}
