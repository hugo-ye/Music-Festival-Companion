package entity;

import org.junit.jupiter.api.Test;

public class EventListTest {
    @Test
    public void testConstructorWithParameters() {
        final String id = "ab123";
        final String name = "Event List Name";
        final EventList eventList = new EventList(id, name);
        assert eventList.getId().equals(id);
        assert eventList.getName().equals(name);
    }
}
