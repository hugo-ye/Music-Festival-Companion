package interface_adapter.display_search_results;

import interface_adapter.ViewModel;

public class DisplaySearchResultsViewModel extends ViewModel<DisplaySearchResultsState> {

    public DisplaySearchResultsViewModel() {
        super("search results");
        setState(new DisplaySearchResultsState());
    }
}
