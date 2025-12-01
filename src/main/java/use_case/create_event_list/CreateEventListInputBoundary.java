package use_case.create_event_list;

public interface CreateEventListInputBoundary {

    /**
     * Executes the Create Event List use case.
     *
     * @param inputData the input data required to create an event list
     */
    void execute(CreateEventListInputData inputData);
}
