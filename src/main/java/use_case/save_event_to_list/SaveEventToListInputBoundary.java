package use_case.save_event_to_list;

public interface SaveEventToListInputBoundary {
    /**
     * Executes the "Save Event To List" use case.
     * Iterates through each Event List and adds the given Event if it is not already there.
     * @param inputData the input data containing the Event and the Event Lists.
     */
    void execute(SaveEventToListInputData inputData);
}
