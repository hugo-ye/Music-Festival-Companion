package interface_adapter.save_event_to_list;

import entity.Event;
import entity.EventList;
import org.junit.jupiter.api.Test;
import use_case.save_event_to_list.SaveEventToListOutputData;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SaveEventToListPresenterTest {

    private Event createSampleEvent() {
        return new Event(
                "E001",
                "Coldplay World Tour",
                List.of("Coldplay"),
                "Rogers Centre",
                "Toronto",
                "Canada",
                LocalDate.of(2025, 7, 15),
                100,
                300,
                "https://ticket.example.com",
                List.of("Rock", "Pop"),
                "https://image.example.com"
        );
    }

    @Test
    void present_updatesViewModelCorrectly() {
        SaveEventToListViewModel viewModel = new SaveEventToListViewModel();
        SaveEventToListPresenter presenter = new SaveEventToListPresenter(viewModel);

        Event event = createSampleEvent();
        EventList list1 = new EventList("1","Favorites");
        EventList list2 = new EventList("1", "Weekend");
        EventList[] lists = {list1, list2};

        String message = "Event added intoFavorites\nEvent added intoWeekend\n";
        SaveEventToListOutputData output =
                new SaveEventToListOutputData(event, lists, message);

        presenter.present(output);

        assertEquals(event, viewModel.getEvent());
        assertArrayEquals(lists, viewModel.getEventList());
        assertEquals(message, viewModel.getMessage());
    }
}
