package use_case.display_event;
import data_access.InMemoryUserDataAccessObject;
import entity.Event;
import entity.EventBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DisplayEventInteractorTest {
    @Test
    void TestWithPriceNotAvailable(){

        final String id = "2113";
        final String eventName = "coachella";
        final List<String> artists = Arrays.asList("Artist1", "Artist2", "Artist3");
        final String venue = "venue";
        final String city = "Toronto";
        final String country = "Canada";
        final LocalDate eventDate = LocalDate.of(2025,11,21);
        final int priceMin = 0;
        final int priceMax = 0;
        final String ticketUrl = "http://example.com/tic";
        final String imageUrl = "http://example.com/img";
        final List<String> genres = Arrays.asList("Rock", "Jazz");

        final DisplayEventDataAccessInterface displayEventDataAccessInterface = new InMemoryUserDataAccessObject();
        DisplayEventOutputBoundary presenter = outputData -> {
            // check for null
            assertNotNull(outputData.getEventName());
            assertNotNull(outputData.getArtists());
            assertNotNull(outputData.getVenue());
            assertNotNull(outputData.getCity());
            assertNotNull(outputData.getCountry());
            assertNotNull(outputData.getDate());
            assertNotNull(outputData.getTicketUrl());
            assertNotNull(outputData.getImageUrl());
            assertNotNull(outputData.getGenres());

            Assertions.assertEquals(eventName, outputData.getEventName());
            Assertions.assertEquals(outputData.getArtists(), artists);
            Assertions.assertEquals(venue, outputData.getVenue());
            Assertions.assertEquals(city, outputData.getCity());
            Assertions.assertEquals(country, outputData.getCountry());
            Assertions.assertEquals(outputData.getDate(), eventDate);
            Assertions.assertEquals(priceMin, outputData.getPriceMin());
            Assertions.assertEquals(priceMax, outputData.getPriceMax());
            Assertions.assertEquals(ticketUrl, outputData.getTicketUrl());
            Assertions.assertEquals(imageUrl, outputData.getImageUrl());
            Assertions.assertEquals(outputData.getGenres(), genres);

            Assertions.assertFalse(outputData.hasPrice());
        };

        final Event event = new EventBuilder()
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

        final DisplayEventInputData inputData = new DisplayEventInputData(event);

        final DisplayEventInputBoundary interactor = new DisplayEventInteractor(presenter,
                displayEventDataAccessInterface);

        interactor.execute(inputData);
    }


    @Test
    void TestWithPriceAvailable(){

        final String id = "2113";
        final String eventName = "coachella";
        final List<String> artists = Arrays.asList("Artist1", "Artist2", "Artist3");
        final String venue = "venue";
        final String city = "Toronto";
        final String country = "Canada";
        final LocalDate eventDate = LocalDate.of(2025,11,21);
        final int priceMin = 50;
        final int priceMax = 100;
        final String ticketUrl = "http://example.com/tic";
        final String imageUrl = "http://example.com/img";
        final List<String> genres = Arrays.asList("Rock", "Jazz");

        final DisplayEventDataAccessInterface displayEventDataAccessInterface = new InMemoryUserDataAccessObject();
        DisplayEventOutputBoundary presenter = outputData -> {
            // check for null
            assertNotNull(outputData.getEventName());
            assertNotNull(outputData.getArtists());
            assertNotNull(outputData.getVenue());
            assertNotNull(outputData.getCity());
            assertNotNull(outputData.getCountry());
            assertNotNull(outputData.getDate());
            assertNotNull(outputData.getTicketUrl());
            assertNotNull(outputData.getImageUrl());
            assertNotNull(outputData.getGenres());

            Assertions.assertEquals(eventName, outputData.getEventName());
            Assertions.assertEquals(outputData.getArtists(), artists);
            Assertions.assertEquals(venue, outputData.getVenue());
            Assertions.assertEquals(city, outputData.getCity());
            Assertions.assertEquals(country, outputData.getCountry());
            Assertions.assertEquals(outputData.getDate(), eventDate);
            Assertions.assertEquals(priceMin, outputData.getPriceMin());
            Assertions.assertEquals(priceMax, outputData.getPriceMax());
            Assertions.assertEquals(ticketUrl, outputData.getTicketUrl());
            Assertions.assertEquals(imageUrl, outputData.getImageUrl());
            Assertions.assertEquals(outputData.getGenres(), genres);

            Assertions.assertTrue(outputData.hasPrice());
        };

        final Event event = new EventBuilder()
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
        
        final DisplayEventInputData inputData = new DisplayEventInputData(event);

        final DisplayEventInputBoundary interactor = new DisplayEventInteractor(presenter,
                displayEventDataAccessInterface);

        interactor.execute(inputData);
    }

    @Test
    void TestWithMinPriceNotAvailable(){

        final String id = "2113";
        final String eventName = "coachella";
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
        final Event event = new EventBuilder()
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

        final DisplayEventDataAccessInterface displayEventDataAccessInterface = new InMemoryUserDataAccessObject();
        DisplayEventOutputBoundary presenter = outputData -> {
            // check for null
            assertNotNull(outputData.getEventName());
            assertNotNull(outputData.getArtists());
            assertNotNull(outputData.getVenue());
            assertNotNull(outputData.getCity());
            assertNotNull(outputData.getCountry());
            assertNotNull(outputData.getDate());
            assertNotNull(outputData.getTicketUrl());
            assertNotNull(outputData.getImageUrl());
            assertNotNull(outputData.getGenres());

            Assertions.assertEquals(eventName, outputData.getEventName());
            Assertions.assertEquals(outputData.getArtists(), artists);
            Assertions.assertEquals(venue, outputData.getVenue());
            Assertions.assertEquals(city, outputData.getCity());
            Assertions.assertEquals(country, outputData.getCountry());
            Assertions.assertEquals(outputData.getDate(), eventDate);
            Assertions.assertEquals(priceMin, outputData.getPriceMin());
            Assertions.assertEquals(priceMax, outputData.getPriceMax());
            Assertions.assertEquals(ticketUrl, outputData.getTicketUrl());
            Assertions.assertEquals(imageUrl, outputData.getImageUrl());
            Assertions.assertEquals(outputData.getGenres(), genres);
            Assertions.assertTrue(outputData.hasPrice());

        };
        final DisplayEventInputData inputData = new DisplayEventInputData(event);

        final DisplayEventInputBoundary interactor = new DisplayEventInteractor(presenter, displayEventDataAccessInterface);

        interactor.execute(inputData);
    }

}