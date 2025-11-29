package use_case.sort_events;

/**
 * Defines the supported criteria for sorting lists of events.
 * This enumeration is used to specify the ordering strategy in the
 * event sorting use case, allowing sorting by name, date, venue, or price.
 */
public enum SortEventsCriteria {
    ALPHABETICAL,
    DATE,
    VENUE,
    PRICE
}
