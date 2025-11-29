package use_case.sort_events;

import java.util.List;

import entity.Event;

/**
 * The Input Data for the Sort Events Use Case.
 */
public class SortEventsInputData {
    private final List<Event> events;
    private final SortEventsCriteria sortEventsCriteria;
    private final SortEventsOrder sortEventsOrder;

    public SortEventsInputData(List<Event> events, SortEventsCriteria criteria, SortEventsOrder sortOrder) {
        this.events = events;
        this.sortEventsCriteria = criteria;
        this.sortEventsOrder = sortOrder;
    }

    public List<Event> getEvents() {
        return events;
    }

    public SortEventsCriteria getSortEventsCriteria() {
        return sortEventsCriteria;
    }

    public SortEventsOrder getSortEventsOrder() {
        return sortEventsOrder;
    }
}
