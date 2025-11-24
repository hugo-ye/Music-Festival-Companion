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
    public void removeEventFromList(RemoveEventFromListInputData inputData) { // execute is the proper boundary method name
        Event removedEvent = inputData.getRemovedEvent();
        EventList targetEventList = inputData.getTargetEventList();

        dataAccess.removeEventFromList(removedEvent, targetEventList);

        RemoveEventFromListOutputData outputData = new RemoveEventFromListOutputData("Event removed from " + targetEventList.getName(), targetEventList.getId());
        presenter.present(outputData);
    }
}