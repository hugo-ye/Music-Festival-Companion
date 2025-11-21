package use_case.sort_events.strategies;

import entity.Event;
import use_case.sort_events.SortEventsStrategy;

public class SortEventsByTicketPrice implements SortEventsStrategy {

    @Override
    public int compare(Event o1, Event o2) {
        // Handle no price provided logic
        boolean noPrice1 = (o1.getPriceMin() == -1);
        boolean noPrice2 = (o2.getPriceMin() == -1);

        // If both have no price, they are equal
        if (noPrice1 && noPrice2) return 0;

        // If o1 has no price, push it to the bottom.
        if (noPrice1) return 1;

        // If o2 has no price, o1 comes first.
        if (noPrice2) return -1;

        // Primary comparison: minimum price
        int minPriceComparison = Integer.compare(o1.getPriceMin(), o2.getPriceMin());

        // If minimum prices are different, return that result immediately
        if (minPriceComparison != 0) {
            return minPriceComparison;
        }

        // secondary Comparison: maximum price tie-breaker
        // if minimums are the same
        // the one with the lower MAXIMUM is cheaper
        return Integer.compare(o1.getPriceMax(), o2.getPriceMax());
    }
}