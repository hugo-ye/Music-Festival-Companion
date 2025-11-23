package interface_adapter.save_event_to_list;

import entity.Event;
import entity.EventList;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SaveEventToListViewModelTest {

    @Test
    void settersAndGettersWork() {
        SaveEventToListViewModel vm = new SaveEventToListViewModel();

        Event event = new Event(
                "E004", "Test Event", List.of(), "Venue",
                "City", "Country", LocalDate.now(),
                10, 20, "url", List.of(), "img"
        );
        EventList[] lists = { new EventList("1","ListA") };
        String msg = "Test message";

        vm.setEvent(event);
        vm.setEventList(lists);
        vm.setMessage(msg);

        assertEquals(event, vm.getEvent());
        assertArrayEquals(lists, vm.getEventList());
        assertEquals(msg, vm.getMessage());
    }
}
