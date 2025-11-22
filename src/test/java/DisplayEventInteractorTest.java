import entity.Event;
import org.junit.jupiter.api.Test;
import use_case.display_event.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DisplayEventInteractorTest {
    String id = "2113";
    String eventName = "coachella";
    List<String> artists = Arrays.asList("Artist1", "Artist2", "Artist3");
    String venue = "venue";
    String city = "Toronto";
    String country = "Canada";
    LocalDate eventDate = LocalDate.of(2025,11,21);
    int priceMin = 0;
    int priceMax = 0;
    String ticketUrl = "http://example.com/tic";
    String imageUrl = "http://example.com/img";
    List<String> genres = Arrays.asList("Rock", "Jazz");

    @Test
    void execute(){
        DisplayEventOutputBoundary presenter = new DisplayEventOutputBoundary() {
            @Override
            public void prepareSuccessView(DisplayEventOutputData outputData) {
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

                assertEquals(outputData.getEventName(), eventName);
                assertEquals(outputData.getArtists(), artists);
                assertEquals(outputData.getVenue(), venue);
                assertEquals(outputData.getCity(), city);
                assertEquals(outputData.getCountry(), country);
                assertEquals(outputData.getDate(), eventDate);
                assertEquals(outputData.getPriceMin(), priceMin);
                assertEquals(outputData.getPriceMax(), priceMax);
                assertEquals(outputData.getTicketUrl(), ticketUrl);
                assertEquals(outputData.getImageUrl(), imageUrl);
                assertEquals(outputData.getGenres(), genres);

                assertFalse(outputData.getHasPrice());
            }
        };
        Event event = new Event(id, eventName,
                artists, venue, city, country, eventDate, priceMin, priceMax, ticketUrl, genres, imageUrl);
        DisplayEventInputData inputData = new DisplayEventInputData(event);

        DisplayEventInputBoundary interactor = new DisplayEventInteractor(presenter);

        interactor.execute(inputData);
    }


}
