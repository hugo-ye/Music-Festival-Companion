package entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a list of events in our program. Stores Event entities and has user-defined name.
 */
public class EventList {
    private final String id;
    private final String name;
    private final List<Event> events;

    /**
     * A constructor for User that initializes a user with the given username and password.
     * @param id is the unique identifier of the list.
     * @param name is the user-defined name of the list.
     */
    public EventList(String id, String name) {
        this.id = id;
        this.name = name;
        this.events = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Event> getEvents() {
        return new ArrayList<>(events);
    }

    /**
     * Adds an event to the list if it is not already present.
     * @param event is the event to be added.
     */
    public void addEvent(Event event) {
        if (!events.contains(event)) {
            events.add(event);
        }
    }

    /**
     * Removes an event from the list.
     * @param event is the event to be removed.
     */
    public void removeEvent(Event event) {
        events.remove(event);
    }

    /**
     * Defines the business logic of comparing two event lists.
     * Two event lists are equal based on their ID.
     * @param o the reference object with which to compare.
     * @return true if the objects are the same and false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        boolean result = false;
        if (o != null && getClass() == o.getClass()) {
            final EventList eventList = (EventList) o;
            result = Objects.equals(id, eventList.id);
        }
        return result;
    }

    /**
     * Defines the hash code of an event list based on its ID.
     * @return the hash code of the event list.
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
