package interface_adapter.display_search_results;

import entity.Event;
import use_case.sort_events.SortEventsMethod;
import use_case.sort_events.SortEventsOrder;

import java.util.List;

public class DisplaySearchResultsState {
    private List<Event> events = null;
    private SortEventsMethod sortMethod = null;
    private SortEventsOrder sortOrder = null;

    public List<Event> getEvents() {
        return events;
    }
    public SortEventsMethod getSortMethod() {
        return sortMethod;
    }
    public SortEventsOrder getSortOrder() {
        return sortOrder;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
    public void setSortMethod(SortEventsMethod sortMethod) {
        this.sortMethod = sortMethod;
    }
    public void setSortOrder(SortEventsOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

}
