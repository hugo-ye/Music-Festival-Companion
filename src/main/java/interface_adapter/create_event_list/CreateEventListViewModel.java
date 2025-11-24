package interface_adapter.create_event_list;

import interface_adapter.ViewModel;


public class CreateEventListViewModel extends ViewModel<CreateEventListState> {

    public CreateEventListViewModel() {
        super("event lists");
        setState(new CreateEventListState());
    }
}