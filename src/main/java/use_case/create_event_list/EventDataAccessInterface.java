package use_case.create_event_list;

import java.util.List;

import entity.Event;

/**
 * Data access interface for retrieving events from an external source.
 * Provides a filtered list of events based on search parameters.
 */
public interface EventDataAccessInterface {

    /**
     * Returns a list of events matching the provided search filters.
     *
     * @param keyword       the general search keyword
     * @param artist        the artist name filter
     * @param country       the country filter
     * @param city          the city filter
     * @param startDateTime the start of the date range (inclusive)
     * @param endDateTime   the end of the date range (inclusive)
     * @param genre         the music genre filter
     * @return a list of events that satisfy the given parameters
     */
    List<Event> getEvents(
            String keyword,
            String artist,
            String country,
            String city,
            String startDateTime,
            String endDateTime,
            String genre
    );
}
