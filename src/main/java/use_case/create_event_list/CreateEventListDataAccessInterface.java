package use_case.create_event_list;

import entity.EventList;

/**
 * Data access interface for creating event lists.
 */
public interface CreateEventListDataAccessInterface {

    /**
     * Returns whether a list with the given name already exists.
     *
     * @param listName the name of the list to check
     * @return true if a list with this name exists, false otherwise
     */
    boolean existsByName(String listName);

    /**
     * Saves the newly created event list.
     *
     * @param eventList the event list entity to create
     */
    void createEventList(EventList eventList);
}

//existByName and save is done in data access layer for In Memory