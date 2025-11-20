package use_case.sort_events.strategies;

import entity.Event;
import use_case.sort_events.SortEventsStrategy;

public class SortEventsByTicketPrice implements SortEventsStrategy {
    @Override
    public int compare(Event o1, Event o2) {
        return Integer.compare(o1.getPriceMin(), o2.getPriceMin());
    }

}
