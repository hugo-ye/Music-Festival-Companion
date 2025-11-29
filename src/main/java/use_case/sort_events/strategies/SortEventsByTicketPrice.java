package use_case.sort_events.strategies;

import entity.Event;
import use_case.sort_events.SortEventsStrategy;

/**
 * A sorting strategy that orders events by their ticket price.
 * The sorting logic is as follows:
 * Primary: Minimum Price (ascending).
 * Secondary: Maximum Price (ascending) is used as a tie-breaker
 * Exceptions: Events with no price (indicated by -1)
 * are sorted to the bottom of the list.
 */
public class SortEventsByTicketPrice implements SortEventsStrategy {

    private static final int NO_PRICE_SENTINEL = -1;

    /**
     * Compares two events based on their ticket prices.
     * @param o1 The first event to compare.
     * @param o2 The second event to compare.
     * @return A negative integer, zero, or a positive integer as the first argument
     *     is cheaper than, equal to, or more expensive than the second.
     */
    @Override
    public int compare(Event o1, Event o2) {
        int result = Integer.compare(
                getSortablePrice(o1.getPriceMin()),
                getSortablePrice(o2.getPriceMin())
        );

        if (result == 0) {
            result = Integer.compare(
                    getSortablePrice(o1.getPriceMax()),
                    getSortablePrice(o2.getPriceMax())
            );
        }

        return result;
    }

    /**
     * Normalizes the price for sorting purposes.
     * Maps the sentinel value (-1) to Integer.MAX_VALUE to ensure it appears last.
     * @param price The raw price from the entity.
     * @return The price, or Integer.MAX_VALUE if the price is invalid/missing.
     */
    private int getSortablePrice(int price) {
        final int result;
        if (price == NO_PRICE_SENTINEL) {
            result = Integer.MAX_VALUE;
        }
        else {
            result = price;
        }
        return result;
    }
}
