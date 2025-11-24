package interface_adapter.delete_event_list;

import interface_adapter.ViewModel;

public class DeleteEventListViewModel extends ViewModel<DeleteEventListState> {

    public DeleteEventListViewModel() {
        super("event lists");
        setState(new DeleteEventListState());
    }
}