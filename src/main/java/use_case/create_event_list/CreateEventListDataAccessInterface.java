package use_case.create_event_list;

import entity.EventList;

public interface CreateEventListDataAccessInterface {
    // existByName and save is done in data access layer for In Memory
    /**
     * A method check whether a list of event is existing by checking list name.
     *
     * @param listName name of the target list.
     * @return whether the list is existing.
     */
    boolean existsByName(String listName);

    /**
     * A method that create a eventList into user.
     *
     * @param eventList the created eventList.
     */
    void createEventList(EventList eventList);

}
