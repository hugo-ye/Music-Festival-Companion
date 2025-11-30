package use_case.create_event_list;

import entity.EventList;

import java.util.UUID;

/**
 * The {@code CreateEventListInteractor} validates user's input and creates a new {@link EventList}
 */
public class CreateEventListInteractor implements CreateEventListInputBoundary {

    private final CreateEventListDataAccessInterface dataAccess;
    private final CreateEventListOutputBoundary presenter;
    private final String MASTER_LIST_NAME = "Master List";
    // Another field for user at Logged In State

    public CreateEventListInteractor(CreateEventListDataAccessInterface dataAccess,
                                     CreateEventListOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    /**
     * Executes the "create event list" use case.
     * This method performs all the following validation steps: ensures the list name is not empty, the list
     * name is not the same as the master list, and finally it ensures the list name is unique.
     *
     * @param inputData the input data containing the user-provided list name
     */
    @Override
    public void execute(CreateEventListInputData inputData) {

        String rawName = inputData.getListName(); // Cleaned according to Abdullah's suggestion
        String name = rawName.trim();

        if (name.isEmpty()) {
            presenter.prepareFailView("List name cannot be empty.");
            return;
        }

        if (name.equalsIgnoreCase(MASTER_LIST_NAME)) {
            presenter.prepareFailView("You cannot create a list named '" + MASTER_LIST_NAME + "'.");
            return;
        }

        // List names must be unique
        if (dataAccess.existsByName(name)) {
            presenter.prepareFailView("A list with this name already exists.");
            return;
        }
        // Generate ID for the new list
        String id = UUID.randomUUID().toString();

        EventList newList = new EventList(id, name);

        dataAccess.createEventList(newList);

        CreateEventListOutputData outputData =
                new CreateEventListOutputData(id, name);

        presenter.prepareSuccessView(outputData);
    }
}