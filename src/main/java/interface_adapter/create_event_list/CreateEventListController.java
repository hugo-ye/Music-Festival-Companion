package interface_adapter.create_event_list;

import use_case.create_event_list.CreateEventListInputBoundary;
import use_case.create_event_list.CreateEventListInputData;

/**
 * Controller responsible for create_event_list use case
 * This class recieves input from the UI, and packages it into a {CreateEventListInputData} object before
 *  * forwarding it to the use-case interactor.
 */
public class CreateEventListController {

    private final CreateEventListInputBoundary interactor;

    public CreateEventListController(CreateEventListInputBoundary interactor) {
        this.interactor = interactor;
    }
    /**
     * Executes the Create Event List use case using the provided list name.
     * This method is called by the UI when a user attempts to create
     * a new list.
     * @param name the name of the new event list to be created
     */

    public void execute(String name) {
        final CreateEventListInputData inputData = new CreateEventListInputData(name);
        interactor.execute(inputData);
    }
}
