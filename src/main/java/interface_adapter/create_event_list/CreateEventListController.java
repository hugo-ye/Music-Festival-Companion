package interface_adapter.create_event_list;

import use_case.create_event_list.CreateEventListInputBoundary;
import use_case.create_event_list.CreateEventListInputData;

public class CreateEventListController {

    private final CreateEventListInputBoundary interactor;

    public CreateEventListController(CreateEventListInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void create(String name) {
        CreateEventListInputData inputData = new CreateEventListInputData(name);
        interactor.execute(inputData);
    }
}