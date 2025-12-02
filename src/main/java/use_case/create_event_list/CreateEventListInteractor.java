package use_case.create_event_list;

import java.util.UUID;

import entity.EventList;

/**
 * The {@code CreateEventListInteractor} validates user's input and creates a new {@link EventList}.
 */
public class CreateEventListInteractor implements CreateEventListInputBoundary {

    private final CreateEventListDataAccessInterface dataAccess;
    private final CreateEventListOutputBoundary presenter;
    // Another field for user at Logged In State

    public CreateEventListInteractor(CreateEventListDataAccessInterface dataAccess,
                                     CreateEventListOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    /**
     * Executes the "create event list" use case.
     * This method performs all the following validation steps: ensures the list name is not empty, the list
     * name is different from the Master list, and finally, it ensures the list name is unique.
     *
     * @param input the input data containing the user-provided list name
     */
    @Override
    public void execute(CreateEventListInputData input) {
        final String rawName = input.getListName();
        final String name = rawName.trim();

        if (!createEventListCriteria(name)) {
            final String id = UUID.randomUUID().toString();
            final EventList newList = new EventList(id, name);
            dataAccess.createEventList(newList);
            final CreateEventListOutputData outputData =
                    new CreateEventListOutputData(id, name);

            presenter.prepareSuccessView(outputData);
        }
    }

    private boolean createEventListCriteria(String name) {
        if (name.isEmpty()) {
            presenter.prepareFailView("List name cannot be empty.");
            return true;
        }
        if ("Master List".equalsIgnoreCase(name)) {
            presenter.prepareFailView("You cannot create a list named '" + "Master List" + "'.");
            return true;
        }
        if (dataAccess.existsByName(name)) {
            presenter.prepareFailView("A list with this name already exists.");
            return true;
        }
        return false;
    }
}
