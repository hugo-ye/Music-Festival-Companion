package interface_adapter.display_search_results;

import java.util.List;

import entity.Event;
import use_case.sort_events.SortEventsCriteria;
import use_case.sort_events.SortEventsOrder;

/**
 * The state for the Display Search Results View Model.
 */
public class DisplaySearchResultsState {
    private List<Event> events;
    private SortEventsCriteria sortEventsCriteria;
    private SortEventsOrder sortEventsOrder;

    public List<Event> getEvents() {
        return events;
    }

    public SortEventsCriteria getSortEventsCriteria() {
        return sortEventsCriteria;
    }

    public SortEventsOrder getSortEventsOrder() {
        return sortEventsOrder;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public void setSortEventsCriteria(SortEventsCriteria sortEventsCriteria) {
        this.sortEventsCriteria = sortEventsCriteria;
    }

    public void setSortEventsOrder(SortEventsOrder sortEventsOrder) {
        this.sortEventsOrder = sortEventsOrder;
    }

}
