package interface_adapter.search_event_result;

import entity.Event;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

public class DisplaySearchResultsViewModel {
    private List<Event> events;
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public DisplaySearchResultsViewModel(DisplaySearchResultsViewModel copy) {
        this.events = copy.getEvents();
    }

    public DisplaySearchResultsViewModel() {
        this.events = null;
    }

    public List<Event> getEvents() {
        return events;
    }

    public Event getEvent(int index) {
        return events.get(index);
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public void setEvent(int index, Event event) {
        this.events.set(index, event);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.propertyChangeSupport.addPropertyChangeListener(listener);
    }
}
