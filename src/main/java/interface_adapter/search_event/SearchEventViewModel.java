package interface_adapter.search_event;

import interface_adapter.ViewModel;

public class SearchEventViewModel extends ViewModel<SearchEventState> {

    public SearchEventViewModel() {
        super("search event");
        setState(new SearchEventState());
    }
}
