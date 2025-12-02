package use_case.delete_event_list;

public interface DeleteEventListInputBoundary {
    /**
     * Executes the delete event list use case.
     * The method performs the following steps:
     * Trims and validates the input data (list id), Fails if the list does not
     * exist or is the Master List, and Deletes the list and reports success otherwise.
     *
     * @param input contains the ID of the list to delete.
     */
    void execute(DeleteEventListInputData input);
}
