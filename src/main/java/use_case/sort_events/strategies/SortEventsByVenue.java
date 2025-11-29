package use_case.sort_events.strategies;

import entity.Event;
import use_case.sort_events.SortEventsStrategy;

/**
 * A sorting strategy that orders events alphabetically by their venue name.
 * This comparison is case-insensitive.
 */
public class SortEventsByVenue implements SortEventsStrategy {

    /**
     * Compares two events based on their venue name.
     * @param o1 The first event to compare.
     * @param o2 The second event to compare.
     * @return A negative integer, zero, or a positive integer as the first event's venue
     *     is lexicographically less than, equal to, or greater than the second, ignoring case.
     */
    @Override
    public int compare(Event o1, Event o2) {
        return o1.getVenue().compareToIgnoreCase(o2.getVenue());
    }
}
