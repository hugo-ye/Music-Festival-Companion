package use_case.remove_event_from_list;

import entity.Event;
import entity.EventList;

/**
 * the {@code RemoveEventFromListInteractor} handles removing an event from a specific event list.
 */
public class RemoveEventFromListInteractor implements RemoveEventFromListInputBoundary{
    private final RemoveEventFromListDataAccessInterface dataAccess;
    private final RemoveEventFromListOutputBoundary presenter;

    public RemoveEventFromListInteractor(RemoveEventFromListDataAccessInterface dataAccess,
                                         RemoveEventFromListOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    /**
     * Removes the specified event from the given event list.
     *
     * @param inputData contains the event to remove and the target list.
     */
    @Override
    public void removeEventFromList(RemoveEventFromListInputData inputData) { // execute is the proper boundary method name
        final Event removedEvent = inputData.getRemovedEvent();
        final EventList targetEventList = inputData.getTargetEventList();

        dataAccess.removeEventFromList(removedEvent, targetEventList);

        final RemoveEventFromListOutputData outputData = new RemoveEventFromListOutputData("Event removed from "
                + targetEventList.getName(), targetEventList.getId());
        presenter.present(outputData);
    }
}