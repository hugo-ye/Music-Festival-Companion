package interface_adapter.search_event;

import entity.Event;
import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

public class SearchEventViewModel extends ViewModel<SearchEventState> {

    public SearchEventViewModel() {

        super("search event");
        setState(new SearchEventState());
    }
}
