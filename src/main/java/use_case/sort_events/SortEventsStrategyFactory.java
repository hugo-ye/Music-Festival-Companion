package use_case.sort_events;

import use_case.sort_events.strategies.SortEventsByDate;
import use_case.sort_events.strategies.SortEventsAlphabetical;
import use_case.sort_events.strategies.SortEventsByTicketPrice;
import use_case.sort_events.strategies.SortEventsByVenue;

public class SortEventsStrategyFactory {

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