package use_case.sort_events.strategies;

import use_case.sort_events.SortEventsStrategy;
import entity.Event;
import java.util.Comparator;

public class SortEventsByDate implements SortEventsStrategy {
    @Override
    public int compare(Event o1, Event o2) {
        return Comparator.comparing(Event::getDate, Comparator.nullsLast(Comparator.naturalOrder()))
                .compare(o1, o2);
    }
}