package use_case.sort_events;

import entity.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SortEventsInteractorTest {

    private SortEventsOutputBoundary presenter;
    private SortEventsInteractor interactor;

    @BeforeEach
    void setUp() {
        presenter = mock(SortEventsOutputBoundary.class);
        interactor = new SortEventsInteractor(presenter);
    }

    @Test
    void testSortAlphabetically() {
        Event eventB = new Event(
                "id1", "Artist B", new ArrayList<>(), "Venue", "City", "Country",
                LocalDate.now(), 10, 20, "url", new ArrayList<>(), "img"
        );

        Event eventA = new Event(
                "id2", "Artist A", new ArrayList<>(), "Venue", "City", "Country",
                LocalDate.now(), 10, 20, "url", new ArrayList<>(), "img"
        );

        SortEventsInputData input = new SortEventsInputData(
                Arrays.asList(eventB, eventA),
                SortEventsCriteria.ALPHABETICAL,
                SortEventsOrder.ASCENDING
        );

        interactor.execute(input);

        ArgumentCaptor<SortEventsOutputData> captor = ArgumentCaptor.forClass(SortEventsOutputData.class);
        verify(presenter).prepareSuccessView(captor.capture());

        List<Event> result = captor.getValue().getEvents();
        assertEquals(eventA, result.get(0));
        assertEquals(eventB, result.get(1));
    }

    @Test
    void testSortByDate() {
        Event eventToday = new Event(
                "id1", "Today Event", new ArrayList<>(), "Venue", "City", "Country",
                LocalDate.now(), 10, 20, "url", new ArrayList<>(), "img"
        );

        Event eventYesterday = new Event(
                "id2", "Yesterday Event", new ArrayList<>(), "Venue", "City", "Country",
                LocalDate.now().minusDays(1), 10, 20, "url", new ArrayList<>(), "img"
        );

        SortEventsInputData input = new SortEventsInputData(
                Arrays.asList(eventToday, eventYesterday),
                SortEventsCriteria.DATE,
                SortEventsOrder.ASCENDING
        );

        interactor.execute(input);

        ArgumentCaptor<SortEventsOutputData> captor = ArgumentCaptor.forClass(SortEventsOutputData.class);
        verify(presenter).prepareSuccessView(captor.capture());

        List<Event> result = captor.getValue().getEvents();
        assertEquals(eventYesterday, result.get(0));
        assertEquals(eventToday, result.get(1));
    }

    @Test
    void testSortByPriceMin() {
        Event expensive = new Event(
                "id1", "Expensive", new ArrayList<>(), "Venue", "City", "Country",
                LocalDate.now(), 100, 200, "url", new ArrayList<>(), "img"
        );

        Event cheap = new Event(
                "id2", "Cheap", new ArrayList<>(), "Venue", "City", "Country",
                LocalDate.now(), 10, 20, "url", new ArrayList<>(), "img"
        );

        SortEventsInputData input = new SortEventsInputData(
                Arrays.asList(expensive, cheap),
                SortEventsCriteria.PRICE,
                SortEventsOrder.ASCENDING
        );

        interactor.execute(input);

        ArgumentCaptor<SortEventsOutputData> captor = ArgumentCaptor.forClass(SortEventsOutputData.class);
        verify(presenter).prepareSuccessView(captor.capture());

        List<Event> result = captor.getValue().getEvents();
        assertEquals(cheap, result.get(0));
        assertEquals(expensive, result.get(1));
    }

    @Test
    void testSortByVenue() {
        Event venueZ = new Event(
                "id1", "Event 1", new ArrayList<>(), "Zoo", "City", "Country",
                LocalDate.now(), 10, 20, "url", new ArrayList<>(), "img"
        );

        Event venueA = new Event(
                "id2", "Event 2", new ArrayList<>(), "Arena", "City", "Country",
                LocalDate.now(), 10, 20, "url", new ArrayList<>(), "img"
        );

        SortEventsInputData input = new SortEventsInputData(
                Arrays.asList(venueZ, venueA),
                SortEventsCriteria.VENUE,
                SortEventsOrder.ASCENDING
        );

        interactor.execute(input);

        ArgumentCaptor<SortEventsOutputData> captor = ArgumentCaptor.forClass(SortEventsOutputData.class);
        verify(presenter).prepareSuccessView(captor.capture());

        List<Event> result = captor.getValue().getEvents();
        assertEquals(venueA, result.get(0));
        assertEquals(venueZ, result.get(1));
    }

    @Test
    void testChoosingDescendingOrder() {
        Event low = new Event(
                "id1", "Low", new ArrayList<>(), "Venue", "City", "Country",
                LocalDate.now(), 10, 20, "url", new ArrayList<>(), "img"
        );

        Event high = new Event(
                "id2", "High", new ArrayList<>(), "Venue", "City", "Country",
                LocalDate.now(), 100, 200, "url", new ArrayList<>(), "img"
        );

        SortEventsInputData input = new SortEventsInputData(
                Arrays.asList(low, high),
                SortEventsCriteria.PRICE,
                SortEventsOrder.DESCENDING
        );

        interactor.execute(input);

        ArgumentCaptor<SortEventsOutputData> captor = ArgumentCaptor.forClass(SortEventsOutputData.class);
        verify(presenter).prepareSuccessView(captor.capture());

        List<Event> result = captor.getValue().getEvents();
        assertEquals(high, result.get(0));
        assertEquals(low, result.get(1));
    }

    @Test
    void testSortingEmptyList() {
        SortEventsInputData input = new SortEventsInputData(
                Collections.emptyList(),
                SortEventsCriteria.ALPHABETICAL,
                SortEventsOrder.ASCENDING
        );

        interactor.execute(input);

        ArgumentCaptor<SortEventsOutputData> captor = ArgumentCaptor.forClass(SortEventsOutputData.class);
        verify(presenter).prepareSuccessView(captor.capture());

        assertEquals(0, captor.getValue().getEvents().size());
    }

    @Test
    void testPassingChosenCriteriaToOutput() {
        Event event = new Event(
                "id1", "Name", new ArrayList<>(), "Venue", "City", "Country",
                LocalDate.now(), 10, 20, "url", new ArrayList<>(), "img"
        );

        SortEventsInputData input = new SortEventsInputData(
                Collections.singletonList(event),
                SortEventsCriteria.DATE,
                SortEventsOrder.DESCENDING
        );

        interactor.execute(input);

        ArgumentCaptor<SortEventsOutputData> captor = ArgumentCaptor.forClass(SortEventsOutputData.class);
        verify(presenter).prepareSuccessView(captor.capture());

        assertEquals(SortEventsCriteria.DATE, captor.getValue().getSortEventsCriteria());
        assertEquals(SortEventsOrder.DESCENDING, captor.getValue().getSortEventsOrder());
    }

    @Test
    void testSortByPriceWithNoPrice() {
        Event normalEvent = new Event(
                "id1", "Normal", new ArrayList<>(), "Venue", "City", "Country",
                LocalDate.now(), 10, 20, "url", new ArrayList<>(), "img"
        );

        Event noPriceEvent = new Event(
                "id2", "No Price", new ArrayList<>(), "Venue", "City", "Country",
                LocalDate.now(), -1, -1, "url", new ArrayList<>(), "img"
        );

        SortEventsInputData input = new SortEventsInputData(
                Arrays.asList(noPriceEvent, normalEvent),
                SortEventsCriteria.PRICE,
                SortEventsOrder.ASCENDING
        );

        interactor.execute(input);

        ArgumentCaptor<SortEventsOutputData> captor = ArgumentCaptor.forClass(SortEventsOutputData.class);
        verify(presenter).prepareSuccessView(captor.capture());

        List<Event> result = captor.getValue().getEvents();

        assertEquals(normalEvent, result.get(0));
        assertEquals(noPriceEvent, result.get(1));
    }

    @Test
    void testSortByPriceTieBreaker() {
        Event cheapMax = new Event(
                "id1", "Cheap Max", new ArrayList<>(), "Venue", "City", "Country",
                LocalDate.now(), 10, 20, "url", new ArrayList<>(), "img"
        );

        Event expensiveMax = new Event(
                "id2", "Expensive Max", new ArrayList<>(), "Venue", "City", "Country",
                LocalDate.now(), 10, 50, "url", new ArrayList<>(), "img"
        );

        SortEventsInputData input = new SortEventsInputData(
                Arrays.asList(expensiveMax, cheapMax),
                SortEventsCriteria.PRICE,
                SortEventsOrder.ASCENDING
        );

        interactor.execute(input);

        ArgumentCaptor<SortEventsOutputData> captor = ArgumentCaptor.forClass(SortEventsOutputData.class);
        verify(presenter).prepareSuccessView(captor.capture());

        List<Event> result = captor.getValue().getEvents();
        
        assertEquals(cheapMax, result.get(0));
        assertEquals(expensiveMax, result.get(1));
    }
}