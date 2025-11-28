package interface_adapter.create_event_list;

/**
 * A simple data structure representing an event list's summary information.
 */
public class EventListSummary {

    private final String id;
    private final String name;

    /**
     * Constructs a new EventListSummary with the given ID and name.
     *
     * @param id   the event list id
     * @param name the event list name
     */
    public EventListSummary(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
