package interface_adapter.create_event_list;

import use_case.create_event_list.CreateEventListInputBoundary;
import use_case.create_event_list.CreateEventListInputData;

public class CreateEventListController {

    private final CreateEventListInputBoundary interactor;

    public CreateEventListController(CreateEventListInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void create(String rawName) {
        String name = rawName.trim(); // Fix raw user input
        CreateEventListInputData inputData = new CreateEventListInputData(name); // Create input data object
        interactor.execute(inputData); // 3. Call the use case
    }
}