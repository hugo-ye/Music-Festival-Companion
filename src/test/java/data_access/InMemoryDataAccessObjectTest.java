package data_access;

import entity.Event;
import entity.EventBuilder;
import entity.EventList;
import entity.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryUserDataAccessObjectTest {

    public Event getOneEvent(){
        return new EventBuilder().id("E001").name("Coldplay World Tour").artists(List.of("Coldplay"))
                .venue("Rogers Centre").city("Toronto").country("Canada")
                .date(LocalDate.of(2025, 7, 15))
                .priceMax(350).priceMin(120).ticketUrl("https://ticketmaster.com/coldplay/toronto")
                .genres(List.of("Rock", "Pop")).imageURL( "https://images.example.com/coldplay.jpg").build();
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

        Event event = getOneEvent();

        dao.saveEventToList(event, list2);

        assertFalse(list2.getEvents().contains(event));
        assertFalse(list1.getEvents().contains(event));
    }
}
