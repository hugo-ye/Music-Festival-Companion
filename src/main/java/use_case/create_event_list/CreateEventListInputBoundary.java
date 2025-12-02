package use_case.create_event_list;

public interface CreateEventListInputBoundary {
    /**
     * Executes the "create event list" use case.
     * This method performs all the following validation steps: ensures the list name is not empty, the list
     * name is different from the Master list, and finally, it ensures the list name is unique.
     *
     * @param input the input data containing the user-provided list name
     */
    void execute(CreateEventListInputData input);
    // Blueprint for class no code implemented here
}
