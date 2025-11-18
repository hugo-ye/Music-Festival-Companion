package interface_adapter.search_event;

import entity.Event;
import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

public class SearchEventViewModel extends ViewModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    private List<Event> events;
    private String errorMessage;
    private SearchEventState state = new SearchEventState();

    public SearchEventViewModel() {
        super("search event");
    }

    @Override
    public SearchEventState getState() {
        return state;
    }

    public void setState(SearchEventState state) {
        this.state = state;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
        support.firePropertyChange("events", null, events);
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        support.firePropertyChange("errorMessage", null, errorMessage);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
