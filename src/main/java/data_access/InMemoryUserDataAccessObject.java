package data_access;

import entity.Event;
import entity.EventList;
import use_case.save_event_to_list.SaveEventToListDataAccessInterface;
import use_case.create_event_list.CreateEventListDataAccessInterface;

import java.util.ArrayList;
import java.util.List;

public class InMemoryUserDataAccessObject
        implements SaveEventToListDataAccessInterface, CreateEventListDataAccessInterface {

    // Stores all event lists created for this user in memory
    private final List<EventList> eventLists = new ArrayList<>();

    // create_event_list methods
    @Override
    public boolean existsByName(String listName) {
        for (EventList list : eventLists) {
            if (list.getName().equals(listName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void save(EventList eventList) {
        // Save a newly created list
        eventLists.add(eventList);
    } // Java Recognise through method overloading


    // Save event to list method
    @Override
    public void save(Event event, EventList eventList) {
        int index = eventLists.indexOf(eventList);

        if (index != -1) {
            EventList found = eventLists.get(index);
            found.addEvent(event);
            System.out.println("some methods to store eventList into a persistent file");
        }
    }
}