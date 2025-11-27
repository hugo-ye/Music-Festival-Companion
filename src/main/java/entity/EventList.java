package entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EventList {
    // Fields
    private final String id;
    private final String name;
    private final List<Event> events;
    /*
    Use Final as we don't want to modify the whole list, only the modify its content
     */

    public EventList(String id, String name) {
        this.id = id;
        this.name = name;
        this.events = new ArrayList<>();
    }

    @Override
    public String toString() {
        String eventsStr = "";
        for (Event event : events) {
            eventsStr += event.toString() + "\n";
        }
        return "EventList{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", events=" + events +
                '}';
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Event> getEvents() {
        return new ArrayList<>(events);
    }

    public Event getEventById(String eventId) {
        for (Event event : events) {
            if (event.getId().equals(eventId)) {
                return event;
            }
        }
        return null;
    }

    // Mutators
    public void addEvent(Event event) {
        if (!events.contains(event)) {
            events.add(event);
        }

    }

    public void removeEvent(Event event) {
        events.remove(event);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EventList eventList = (EventList) o;
        return Objects.equals(id, eventList.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
