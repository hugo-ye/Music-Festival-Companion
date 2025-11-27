package interface_adapter.save_event_to_list;

import entity.Event;
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

    private Event createSampleEvent() {
        return new Event(
                "E002",
                "Taylor Swift Eras Tour",
                List.of("Taylor Swift"),
                "Scotiabank Arena",
                "Toronto",
                "Canada",
                LocalDate.of(2025, 8, 3),
                150,
                600,
                "https://ticket.taylor.example.com",
                List.of("Pop"),
                "https://image.taylor.example.com"
        );
    }

    @Test
    void controllerBuildsInputDataAndCallsInteractor() {
        FakeInputBoundary interactor = new FakeInputBoundary();
        SaveEventToListController controller = new SaveEventToListController(interactor);

        Event event = createSampleEvent();
        EventList list = new EventList("1", "My List");
        EventList[] lists = {list};

        controller.execute(event, lists);

        assertNotNull(interactor.lastInput);
        assertEquals(event, interactor.lastInput.getEvent());
        assertArrayEquals(lists, interactor.lastInput.getEventLists());
    }
}
