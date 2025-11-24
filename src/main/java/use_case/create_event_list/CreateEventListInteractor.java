package use_case.create_event_list;

import entity.EventList;

import java.util.UUID;

public class CreateEventListInteractor implements CreateEventListInputBoundary {

    private final CreateEventListDataAccessInterface dataAccess;
    private final CreateEventListOutputBoundary presenter;
    // Another field for user at Logged In State

    public CreateEventListInteractor(CreateEventListDataAccessInterface dataAccess,
                                     CreateEventListOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(CreateEventListInputData inputData) {

        String rawName = inputData.getListName(); // Cleaned according to Abdullah's suggestion
        String name = rawName.trim();

        if (name.isEmpty()) {
            presenter.prepareFailView("List name cannot be empty.");
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