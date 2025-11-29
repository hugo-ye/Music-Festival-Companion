package use_case.sort_events;

import use_case.sort_events.strategies.SortEventsAlphabetical;
import use_case.sort_events.strategies.SortEventsByDate;
import use_case.sort_events.strategies.SortEventsByTicketPrice;
import use_case.sort_events.strategies.SortEventsByVenue;

/**
 * A static factory class responsible for instantiating specific SortEventsStrategy implementations.
 * This factory encapsulates the creation logic, allowing the application to select
 * the appropriate sorting algorithm dynamically based on the provided SortEventsCriteria.
 */
public class SortEventsStrategyFactory {

    /**
     * Creates and returns a sorting strategy corresponding to the given criteria.
     * @param criteria The criteria defining the desired sort attribute.
     * @return A concrete implementation of SortEventsStrategy matching the criteria.
     *     Returns a new instance of SortEventsAlphabetical as the default fallback.
     */
    public static SortEventsStrategy create(SortEventsCriteria criteria) {
        switch (criteria) {
            case ALPHABETICAL:
                return new SortEventsAlphabetical();
            case DATE:
                return new SortEventsByDate();
            case VENUE:
                return new SortEventsByVenue();
            case PRICE:
                return new SortEventsByTicketPrice();
            default:
                return new SortEventsAlphabetical();
        }
    }
}
