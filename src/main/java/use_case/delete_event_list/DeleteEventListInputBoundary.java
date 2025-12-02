package use_case.delete_event_list;

public interface DeleteEventListInputBoundary {

    /**
     * Executes the delete event list use case using the provided input data.
     *
     * @param inputData the input data object containing the ID of the list to be deleted
     */
    void execute(DeleteEventListInputData inputData);
}
