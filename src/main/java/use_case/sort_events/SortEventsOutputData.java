package use_case.sort_events;

import entity.Event;
import java.util.List;

public class SortEventsOutputData {
    private final List<Event> events;
    private final SortEventsCriteria criteria; // Changed from SortEventsMethod
    private final SortEventsOrder sortOrder;

    public SortEventsOutputData(List<Event> events, SortEventsCriteria criteria, SortEventsOrder sortOrder){
        this.events = events;
        this.criteria = criteria;
        this.sortOrder = sortOrder;
    }

    public List<Event> getEvents() {
        return events;
    }

    public SortEventsCriteria getCriteria() {
        return criteria;
    }

    public SortEventsOrder getSortOrder() {
        return sortOrder;
    }
}