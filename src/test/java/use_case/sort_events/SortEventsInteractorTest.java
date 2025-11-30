package use_case.sort_events;

import entity.Event;
import entity.EventBuilder;
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
        Event eventB = new EventBuilder()
                .id("id1")
                .name("Artist B")
                .artists(new ArrayList<>())
                .venue("Venue")
                .city("City")
                .country("Country")
                .date(LocalDate.now())
                .priceMin(10)
                .priceMax(20)
                .ticketUrl("url")
                .genres(new ArrayList<>())
                .imageURL("img")
                .build();

        Event eventA = new EventBuilder()
                .id("id2")
                .name("Artist A")
                .artists(new ArrayList<>())
                .venue("Venue")
                .city("City")
                .country("Country")
                .date(LocalDate.now())
                .priceMin(10)
                .priceMax(20)
                .ticketUrl("url")
                .genres(new ArrayList<>())
                .imageURL("img")
                .build();

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
        Event eventToday = new EventBuilder()
                .id("id1")
                .name("Today Event")
                .artists(new ArrayList<>())
                .venue("Venue")
                .city("City")
                .country("Country")
                .date(LocalDate.now())
                .priceMin(10)
                .priceMax(20)
                .ticketUrl("url")
                .genres(new ArrayList<>())
                .imageURL("img")
                .build();

        Event eventYesterday = new EventBuilder()
                .id("id2")
                .name("Yesterday Event")
                .artists(new ArrayList<>())
                .venue("Venue")
                .city("City")
                .country("Country")
                .date(LocalDate.now().minusDays(1))
                .priceMin(10)
                .priceMax(20)
                .ticketUrl("url")
                .genres(new ArrayList<>())
                .imageURL("img")
                .build();

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
        Event expensive = new EventBuilder()
                .id("id1")
                .name("Expensive")
                .artists(new ArrayList<>())
                .venue("Venue")
                .city("City")
                .country("Country")
                .date(LocalDate.now())
                .priceMin(100)
                .priceMax(200)
                .ticketUrl("url")
                .genres(new ArrayList<>())
                .imageURL("img")
                .build();

        Event cheap = new EventBuilder()
                .id("id2")
                .name("Cheap")
                .artists(new ArrayList<>())
                .venue("Venue")
                .city("City")
                .country("Country")
                .date(LocalDate.now())
                .priceMin(10)
                .priceMax(20)
                .ticketUrl("url")
                .genres(new ArrayList<>())
                .imageURL("img")
                .build();

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
        Event venueZ = new EventBuilder()
                .id("id1")
                .name("Event 1")
                .artists(new ArrayList<>())
                .venue("Zoo")
                .city("City")
                .country("Country")
                .date(LocalDate.now())
                .priceMin(10)
                .priceMax(20)
                .ticketUrl("url")
                .genres(new ArrayList<>())
                .imageURL("img")
                .build();

        Event venueA = new EventBuilder()
                .id("id2")
                .name("Event 2")
                .artists(new ArrayList<>())
                .venue("Arena")
                .city("City")
                .country("Country")
                .date(LocalDate.now())
                .priceMin(10)
                .priceMax(20)
                .ticketUrl("url")
                .genres(new ArrayList<>())
                .imageURL("img")
                .build();

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
        Event low = new EventBuilder()
                .id("id1")
                .name("Low")
                .artists(new ArrayList<>())
                .venue("Venue")
                .city("City")
                .country("Country")
                .date(LocalDate.now())
                .priceMin(10)
                .priceMax(20)
                .ticketUrl("url")
                .genres(new ArrayList<>())
                .imageURL("img")
                .build();

        Event high = new EventBuilder()
                .id("id2")
                .name("High")
                .artists(new ArrayList<>())
                .venue("Venue")
                .city("City")
                .country("Country")
                .date(LocalDate.now())
                .priceMin(100)
                .priceMax(200)
                .ticketUrl("url")
                .genres(new ArrayList<>())
                .imageURL("img")
                .build();

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
        Event event = new EventBuilder()
                .id("id1")
                .name("Name")
                .artists(new ArrayList<>())
                .venue("Venue")
                .city("City")
                .country("Country")
                .date(LocalDate.now())
                .priceMin(10)
                .priceMax(20)
                .ticketUrl("url")
                .genres(new ArrayList<>())
                .imageURL("img")
                .build();

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
        Event normalEvent = new EventBuilder()
                .id("id1")
                .name("Normal")
                .artists(new ArrayList<>())
                .venue("Venue")
                .city("City")
                .country("Country")
                .date(LocalDate.now())
                .priceMin(10)
                .priceMax(20)
                .ticketUrl("url")
                .genres(new ArrayList<>())
                .imageURL("img")
                .build();

        Event noPriceEvent = new EventBuilder()
                .id("id2")
                .name("No Price")
                .artists(new ArrayList<>())
                .venue("Venue")
                .city("City")
                .country("Country")
                .date(LocalDate.now())
                .priceMin(-1)
                .priceMax(-1)
                .ticketUrl("url")
                .genres(new ArrayList<>())
                .imageURL("img")
                .build();

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
        Event cheapMax = new EventBuilder()
                .id("id1")
                .name("Cheap Max")
                .artists(new ArrayList<>())
                .venue("Venue")
                .city("City")
                .country("Country")
                .date(LocalDate.now())
                .priceMin(10)
                .priceMax(20)
                .ticketUrl("url")
                .genres(new ArrayList<>())
                .imageURL("img")
                .build();

        Event expensiveMax = new EventBuilder()
                .id("id2")
                .name("Expensive Max")
                .artists(new ArrayList<>())
                .venue("Venue")
                .city("City")
                .country("Country")
                .date(LocalDate.now())
                .priceMin(10)
                .priceMax(50)
                .ticketUrl("url")
                .genres(new ArrayList<>())
                .imageURL("img")
                .build();

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