package app.use_case.save_event_to_list;

import entity.Event;
import entity.EventList;
import org.junit.jupiter.api.Test;
import use_case.save_event_to_list.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SaveEventToListInteractorTest {

    static class FakeDataAccess implements SaveEventToListDataAccessInterface {

        public ArrayList<String> savedToLists = new ArrayList<>();

        @Override
        public void save(Event event, EventList eventList) {
            savedToLists.add(eventList.getName());
            eventList.addEvent(event);
        }
    }

    static class FakePresenter implements SaveEventToListOutputBoundary {

        public SaveEventToListOutputData lastOutput;

        @Override
        public void present(SaveEventToListOutputData outputData) {
            this.lastOutput = outputData;
        }
    }

    public Event getOneEvent(){
        return new Event(
                "E001",
                "Coldplay World Tour",
                List.of("Coldplay"),
                "Rogers Centre",
                "Toronto",
                "Canada",
                LocalDate.of(2025, 7, 15),
                120,
                350,
                "https://ticketmaster.com/coldplay/toronto",
                List.of("Rock", "Pop"),
                "https://images.example.com/coldplay.jpg"
        );
    }

    @Test
    void testSaveEventToMultipleLists() {
        FakeDataAccess dao = new FakeDataAccess();
        FakePresenter presenter = new FakePresenter();
        SaveEventToListInteractor interactor = new SaveEventToListInteractor(dao, presenter);

        Event event = getOneEvent();
        EventList list1 = new EventList("1","Favorites");
        EventList list2 = new EventList("2","Weekend Plan");

        EventList[] lists = { list1, list2 };
        SaveEventToListInputData input = new SaveEventToListInputData(event, lists);

        interactor.execute(input);

        assertEquals(2, dao.savedToLists.size());
        assertTrue(dao.savedToLists.contains("Favorites"));
        assertTrue(dao.savedToLists.contains("Weekend Plan"));

        assertNotNull(presenter.lastOutput);
        assertEquals(event, presenter.lastOutput.getEvent());
        assertEquals(2, presenter.lastOutput.getEventLists().length);

        String msg = presenter.lastOutput.getMessage();
        assertTrue(msg.contains("Event added intoFavorites"));
        assertTrue(msg.contains("Event added intoWeekend Plan"));
    }

    @Test
    void testSaveEventToSingleLists() {
        FakeDataAccess dao = new FakeDataAccess();
        FakePresenter presenter = new FakePresenter();
        SaveEventToListInteractor interactor = new SaveEventToListInteractor(dao, presenter);

        Event event = getOneEvent();
        EventList list1 = new EventList("1","Favorites");

        EventList[] lists = { list1 };
        SaveEventToListInputData input = new SaveEventToListInputData(event, lists);

        interactor.execute(input);

        assertEquals(1, dao.savedToLists.size());
        assertTrue(dao.savedToLists.contains("Favorites"));

        assertNotNull(presenter.lastOutput);
        assertEquals(event, presenter.lastOutput.getEvent());
        assertEquals(1, presenter.lastOutput.getEventLists().length);

        String msg = presenter.lastOutput.getMessage();
        assertTrue(msg.contains("Event added intoFavorites"));
    }


}
