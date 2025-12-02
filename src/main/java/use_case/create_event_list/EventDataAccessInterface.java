package use_case.create_event_list;

import java.util.List;

import entity.Event;

public interface EventDataAccessInterface {
    // Why is this file here? Should be at SearchEvents instead?
    /**
     * Returns the events associated with the given parameters.
     * @param keyword          The keyword or artist name string from the UI.
     * @param artist             The artist string (e.g, the exact name of artist).
     * @param startDateTime    The start date string (e.g., "YYYY-MM-DD").
     * @param endDateTime     The end date string (e.g., "YYYY-MM-DD").
     * @param country            The name of the country with proper spelling
     * @param city           The name of the city (e.g, Toronto).
     * @param genre          The genre of event.
     * @return get a list of event that satisfies given conditions.
     */
    List<Event> getEvents(String keyword, String artist, String country, String city, String startDateTime,
                          String endDateTime, String genre);
}
