package use_case.remove_event_from_list;

public interface RemoveEventFromListInputBoundary {
    /**
     * Removes the specified event from the given event list.
     *
     * @param inputData contains the event to remove and the target list.
     */
    void execute(RemoveEventFromListInputData inputData);
}
