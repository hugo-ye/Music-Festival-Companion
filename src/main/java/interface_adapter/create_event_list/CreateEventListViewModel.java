package interface_adapter.create_event_list;

import interface_adapter.ViewModel;

    // For information that affects the UI
    // 1. Input name of the event list
    // 2. Output new created List
    // 3. Message from interactor (Checking whether event names are duplicated)

public class CreateEventListViewModel extends ViewModel<CreateEventListState> {

    public static final String VIEW_NAME = "event lists";
    public CreateEventListViewModel() {
        super(VIEW_NAME);
        // initial empty state
        setState(new CreateEventListState());
    }
}