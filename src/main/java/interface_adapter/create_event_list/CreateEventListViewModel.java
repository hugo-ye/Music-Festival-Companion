package interface_adapter.create_event_list;

import interface_adapter.ViewModel;

/**
 * ViewModel for managing UI state related to creating event lists.
 */
public class CreateEventListViewModel extends ViewModel<CreateEventListState> {

    public CreateEventListViewModel() {
        super("event lists");
        setState(new CreateEventListState());
    }
}