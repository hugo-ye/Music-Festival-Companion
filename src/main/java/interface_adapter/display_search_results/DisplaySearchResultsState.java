package interface_adapter.display_search_results;

import entity.Event;
import use_case.sort_events.SortEventsCriteria;
import use_case.sort_events.SortEventsOrder;

import java.util.List;

public class DisplaySearchResultsState {
    private List<Event> events = null;
    private SortEventsCriteria sortEventsCriteria = null;
    private SortEventsOrder sortOrder = null;

    public List<Event> getEvents() {
        return events;
    }
    public SortEventsCriteria getSortEventsCriteria() {
        return sortEventsCriteria;
    }
    public SortEventsOrder getSortOrder() {
        return sortOrder;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
    public void setSortEventsCriteria(SortEventsCriteria sortEventsCriteria) {
        this.sortEventsCriteria = sortEventsCriteria;
    }
    public void setSortOrder(SortEventsOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

}
