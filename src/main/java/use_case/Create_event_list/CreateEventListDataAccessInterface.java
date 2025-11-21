package use_case.create_event_list;

import entity.EventList;

public interface CreateEventListDataAccessInterface {

    boolean existsByName(String listName);
    void create(EventList eventList);
}

//existByName and save is done in data access layer for In Memory