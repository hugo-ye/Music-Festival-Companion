package use_case.sort_events.strategies;

import entity.Event;
import use_case.sort_events.SortEventsStrategy;

/**
 * A sorting strategy that orders events alphabetically by their name (title).
 * This comparison is case-insensitive to ensure consistent ordering regardless of capitalization.
 */
public class SortEventsAlphabetical implements SortEventsStrategy {

    /**
     * Compares two events based on their name.
     * @param o1 The first event to compare.
     * @param o2 The second event to compare.
     * @return A negative integer, zero, or a positive integer as the first event's name
     *     is lexicographically less than, equal to, or greater than the second, ignoring case.
     */
    @Override
    public int compare(Event o1, Event o2) {
        return o1.getName().compareToIgnoreCase(o2.getName());
    }
}
