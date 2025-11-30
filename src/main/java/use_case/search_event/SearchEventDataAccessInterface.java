package use_case.search_event;

/**
 * DAO for the SearchEvent use case
 */
public interface SearchEventDataAccessInterface {
    /**
     * Searches for events matching the provided criteria.
     * @param keyword a keyword to search for an event.
     * @param countryCode a country code to filter events.
     * @param city the city to filter events.
     * @param startDateTime the start date of the event.
     * @param endDateTime the end date of the event.
     * @param genreIds a comma-separated list of genre IDs to filter events.
     * @return a {@link String} containing the raw data of the event.
     */
    String search(String keyword,
                  String countryCode, String city,
                  String startDateTime, String endDateTime,
                  String genreIds);
}
