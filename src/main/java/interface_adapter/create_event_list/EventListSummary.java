package interface_adapter.create_event_list;

public class EventListSummary {

    private final String id;
    private final String name;

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