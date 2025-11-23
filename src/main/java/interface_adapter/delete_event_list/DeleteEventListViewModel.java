package interface_adapter.delete_event_list;

import interface_adapter.ViewModel;

public class DeleteEventListViewModel extends ViewModel<DeleteEventListState> {

    public static final String VIEW_NAME = "event lists";

    public DeleteEventListViewModel() {
        super(VIEW_NAME);
        // start with an empty state
        setState(new DeleteEventListState());
    }
}