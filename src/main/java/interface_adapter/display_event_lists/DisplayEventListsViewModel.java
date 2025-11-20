package interface_adapter.display_event_lists;

import interface_adapter.ViewModel;

public class DisplayEventListsViewModel extends ViewModel<DisplayEventListsState> {
    public DisplayEventListsViewModel() {
        super("event lists");
        setState(new DisplayEventListsState());
    }
}
