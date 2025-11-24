package data_access;

import entity.Event;
import entity.EventList;
import entity.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryUserDataAccessObjectTest {

    private Event createSampleEvent() {
        return new Event(
                "E003",
                "Jazz Night",
                List.of("Tokyo Jazz Ensemble"),
                "Blue Note Tokyo",
                "Tokyo",
                "Japan",
                LocalDate.of(2025, 11, 21),
                60,
                150,
                "https://ticket.jazz.example.com",
                List.of("Jazz"),
                "https://image.jazz.example.com"
        );
    }

    @Test
    void save_addsEventToCorrectEventList_empty_eventList() {
        EventList list1 = new EventList("1","Favorites");
        EventList list2 = new EventList("2","Weekend Plan");

        List<EventList> eventLists = new ArrayList<>();
        eventLists.add(list1);
        eventLists.add(list2);

        User user = new User("test", "123");

        InMemoryUserDataAccessObject dao = new InMemoryUserDataAccessObject();
        dao.setCurrentUser(user);

        Event event = createSampleEvent();

        dao.saveEventToList(event, list2);

        assertFalse(list2.getEvents().contains(event));
        assertFalse(list1.getEvents().contains(event));
    }
}
