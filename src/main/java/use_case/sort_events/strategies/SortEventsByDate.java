package use_case.sort_events.strategies;

import java.util.Comparator;

import entity.Event;
import use_case.sort_events.SortEventsStrategy;

/**
 * A sorting strategy that orders events chronologically by their date.
 * This strategy relies on the natural ordering of the date objects.
 * It explicitly handles missing dates by placing events with ´null dates
 * at the end of the list.
 */
public class SortEventsByDate implements SortEventsStrategy {

    /**
     * Compares two events based on their date.
     * @param o1 The first event to compare.
     * @param o2 The second event to compare.
     * @return A negative integer, zero, or a positive integer as the first event's date
     *     is earlier than, equal to, or later than the second.
     */
    @Override
    public int compare(Event o1, Event o2) {
        return Comparator.comparing(Event::getDate, Comparator.nullsLast(Comparator.naturalOrder()))
                .compare(o1, o2);
    }
}
