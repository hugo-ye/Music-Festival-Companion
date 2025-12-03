package use_case.attend_event;

import data_access.InMemoryUserDataAccessObject;
import entity.Event;
import entity.EventBuilder;
import entity.User;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AttendEventInteractorTest {

    @Test
    void EventNotInMasterList(){
        final String  eventName = "eventName";
        final Event event = getEvent();
        final User user = new User("Abdullah", "123");

        final AttendEventDataAccessInterface attendEventDataAccessInterface = new InMemoryUserDataAccessObject();
        attendEventDataAccessInterface.setCurrentUser(user);
        AttendEventOutputBoundary presenter = new AttendEventOutputBoundary() {
            @Override
            public void prepareSuccessView(AttendEventOutputData attendEventOutputData) {
                assertNotNull(attendEventOutputData.getEventName());
                assertEquals(eventName, attendEventOutputData.getEventName());
                assertTrue(user.getMasterList().getEvents().contains(event));
            }

            @Override
            public void prepareFailView(String error_message) {
                throw new AssertionError("Event already Attended");
            }
        };

        final AttendEventInputData inputData = new AttendEventInputData(event);

        final AttendEventInputBoundary interactor = new AttendEventInteractor(attendEventDataAccessInterface, presenter);

        interactor.execute(inputData);
    }

    @NotNull
    private Event getEvent() {
        final String id = "2113";
        final List<String> artists = Arrays.asList("Artist1", "Artist2", "Artist3");
        final String venue = "venue";
        final String city = "Toronto";
        final String country = "Canada";
        final LocalDate eventDate = LocalDate.of(2025,11,21);
        final int priceMin = 0;
        final int priceMax = 10;
        final String ticketUrl = "http://example.com/tic";
        final String imageUrl = "http://example.com/img";
        final List<String> genres = Arrays.asList("Rock", "Jazz");
        return new EventBuilder()
                .id(id)
                .name("eventName")
                .artists(artists)
                .venue(venue)
                .city(city)
                .country(country)
                .date(eventDate)
                .priceMin(priceMin)
                .priceMax(priceMax)
                .ticketUrl(ticketUrl)
                .genres(genres)
                .imageURL(imageUrl)
                .build();
    }

    @Test
    void EventInMasterList(){
        final Event event = getEvent1();

        final User user = new User("Abdullah", "123");

        user.getMasterList().addEvent(event);

        final AttendEventDataAccessInterface attendEventDataAccessInterface = new InMemoryUserDataAccessObject();

        attendEventDataAccessInterface.setCurrentUser(user);

        AttendEventOutputBoundary presenter = new AttendEventOutputBoundary() {
            @Override
            public void prepareSuccessView(AttendEventOutputData attendEventOutputData) {
                fail("Since the event is already in the Master List");
            }

            @Override
            public void prepareFailView(String error_message) {
                assertEquals("Event already Attended", error_message);
            }
        };


        final AttendEventInputData inputData = new AttendEventInputData(event);

        final AttendEventInputBoundary interactor = new AttendEventInteractor(attendEventDataAccessInterface,
                presenter);

        interactor.execute(inputData);
    }
    private Event getEvent1() {
        final String eventName = "coachella";
        final String id = "453";
        final List<String> artists = Arrays.asList("Artist1", "Artist2", "Artist3");
        final String venue = "venue";
        final String city = "Toronto";
        final String country = "Canada";
        final LocalDate eventDate = LocalDate.of(2025,11,21);
        final int priceMin = 0;
        final int priceMax = 10;
        final String ticketUrl = "http://example.com/tic";
        final String imageUrl = "http://example.com/img";
        final List<String> genres = Arrays.asList("Rap", "jazz");
        return new EventBuilder()
                .id(id)
                .name(eventName)
                .artists(artists)
                .venue(venue)
                .city(city)
                .country(country)
                .date(eventDate)
                .priceMin(priceMin)
                .priceMax(priceMax)
                .ticketUrl(ticketUrl)
                .genres(genres)
                .imageURL(imageUrl)
                .build();
    }
}



