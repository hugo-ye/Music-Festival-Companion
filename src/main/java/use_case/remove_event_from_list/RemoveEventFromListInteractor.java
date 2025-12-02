package use_case.remove_event_from_list;

import entity.Event;
import entity.EventList;

/**
 * Interactor for Remove Event Use Case
 * Handles removing an event from a specific event list.
 */
public class RemoveEventFromListInteractor implements RemoveEventFromListInputBoundary {
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
     * @param input contains the event to remove and the target list.
     */
    @Override
    public void execute(RemoveEventFromListInputData input) {
        final Event removedEvent = input.getRemovedEvent();
        final EventList targetEventList = input.getTargetEventList();

        dataAccess.removeEventFromList(removedEvent, targetEventList);

        final RemoveEventFromListOutputData outputData =
                new RemoveEventFromListOutputData("Event removed from "
                + targetEventList.getName(), targetEventList.getId());
        presenter.present(outputData);
    }
}
