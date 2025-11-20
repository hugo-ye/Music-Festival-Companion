package use_case.sort_events;

import entity.Event;
import java.util.List;

public class SortEventsInputData {
    private final List<Event> events;
    private final SortEventsCriteria sortEventsCriteria; // Changed from SortEventsMethod
    private final SortEventsOrder sortEventsOrder;

    public SortEventsInputData(List<Event> events, SortEventsCriteria criteria, SortEventsOrder sortOrder){
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