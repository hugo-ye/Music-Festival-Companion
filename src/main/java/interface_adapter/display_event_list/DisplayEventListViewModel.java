package interface_adapter.display_event_list;

import interface_adapter.ViewModel;

public class DisplayEventListViewModel extends ViewModel<DisplayEventListState> {
    public DisplayEventListViewModel() {
        super("event list");
        setState(new DisplayEventListState());
    }

}
