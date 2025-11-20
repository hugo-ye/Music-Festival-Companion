package use_case.sort_events.strategies;
import entity.Event;
import use_case.sort_events.SortEventsStrategy;

public class SortEventsByVenue implements SortEventsStrategy{
    @Override
    public int compare(Event o1, Event o2) {
        return o1.getVenue().compareToIgnoreCase(o2.getVenue());
    }
}
