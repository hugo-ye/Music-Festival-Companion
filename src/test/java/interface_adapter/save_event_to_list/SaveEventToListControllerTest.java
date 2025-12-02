package interface_adapter.save_event_to_list;

import entity.Event;
import entity.EventBuilder;
import entity.EventList;
import org.junit.jupiter.api.Test;
import use_case.save_event_to_list.SaveEventToListInputBoundary;
import use_case.save_event_to_list.SaveEventToListInputData;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SaveEventToListControllerTest {

    static class FakeInputBoundary implements SaveEventToListInputBoundary {
        SaveEventToListInputData lastInput;

        @Override
        public void execute(SaveEventToListInputData inputData) {
            this.lastInput = inputData;
        }
    }

    public Event getOneEvent(){
        return new EventBuilder().id("E001").name("Coldplay World Tour").artists(List.of("Coldplay"))
                .venue("Rogers Centre").city("Toronto").country("Canada")
                .date(LocalDate.of(2025, 7, 15))
                .priceMax(350).priceMin(120).ticketUrl("https://ticketmaster.com/coldplay/toronto")
                .genres(List.of("Rock", "Pop")).imageURL( "https://images.example.com/coldplay.jpg").build();
    }

    @Test
    void controllerBuildsInputDataAndCallsInteractor() {
        FakeInputBoundary interactor = new FakeInputBoundary();
        SaveEventToListController controller = new SaveEventToListController(interactor);

        Event event = getOneEvent();
        EventList list = new EventList("1", "My List");
        EventList[] lists = {list};

        controller.execute(event, lists);

        assertNotNull(interactor.lastInput);
        assertEquals(event, interactor.lastInput.getEvent());
        assertArrayEquals(lists, interactor.lastInput.getEventLists());
    }
}
