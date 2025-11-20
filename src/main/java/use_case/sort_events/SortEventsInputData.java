package use_case.sort_events;

import entity.Event;
import java.util.List;

public class SortEventsInputData {
    private final List<Event> events;
    private final SortEventsMethod sortMethod;
    private final SortEventsOrder sortOrder;


    public SortEventsInputData(List<Event> events, SortEventsMethod sortMethod, SortEventsOrder sortOrder){
        this.events = events;
        this.sortMethod = sortMethod;
        this.sortOrder = sortOrder;
    }

    public List<Event> getEvents() {
        return events;
    }
    public SortEventsMethod getSortMethod() {
        return sortMethod;
    }
    public SortEventsOrder getSortOrder() {
        return sortOrder;
    }
}
