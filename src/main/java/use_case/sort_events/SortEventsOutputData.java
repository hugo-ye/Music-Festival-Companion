package use_case.sort_events;

import java.util.List;

import entity.Event;

/**
 * Output Data for the Sort Events Use Case.
 */
public class SortEventsOutputData {
    private final List<Event> events;
    private final SortEventsCriteria criteria;
    private final SortEventsOrder sortOrder;

    public SortEventsOutputData(List<Event> events, SortEventsCriteria criteria, SortEventsOrder sortOrder) {
        this.events = events;
        this.criteria = criteria;
        this.sortOrder = sortOrder;
    }

    public List<Event> getEvents() {
        return events;
    }

    public SortEventsCriteria getSortEventsCriteria() {
        return criteria;
    }

    public SortEventsOrder getSortEventsOrder() {
        return sortOrder;
    }
}
