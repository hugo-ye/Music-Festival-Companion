package interface_adapter.display_all_event_lists;

import interface_adapter.ViewModel;

public class DisplayAllEventListsViewModel extends ViewModel<DisplayAllEventListsState> {
    public DisplayAllEventListsViewModel() {
        super("event lists");
        setState(new DisplayAllEventListsState());
    }
}
