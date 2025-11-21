package use_case.sort_events.strategies;

import use_case.sort_events.SortEventsStrategy;

import entity.Event;

public class SortEventsByDate implements SortEventsStrategy {
    @Override
    public int compare(Event o1, Event o2) {
        return o1.getDate().compareTo(o2.getDate());
    }
}
