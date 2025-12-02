package use_case.search_event;

import java.util.List;

import entity.Event;

public class SearchEventOutputData {
    private final List<Event> events;

    public SearchEventOutputData(List<Event> events) {
        this.events = events;
    }

    public List<Event> getEvents() {
        return events;
    }
}
