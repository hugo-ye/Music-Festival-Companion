package interface_adapter.save_event_to_list;

import interface_adapter.ViewModel;

public class SaveEventToListViewModel extends ViewModel<SaveEventToListState> {

    public SaveEventToListViewModel() {
        super("save_event");
        setState(new SaveEventToListState());
    }
}
