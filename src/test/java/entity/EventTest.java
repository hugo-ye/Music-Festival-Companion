package entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

public class EventTest {
    @Test
    public void testConstructorWithParameters() {
        final String id = "ab123";
        final String name = "Event Name";
        final List<String> artists = List.of("Artist 1", "Artist 2");
        final String venue = "Venue Name";
        final String city = "City Name";
        final String country = "Country1";
        final LocalDate date = LocalDate.of(2025, 12, 25);
        final int priceMin = 3;
        final int priceMax = 50;
        final String ticketUrl = "https://www.example.com";
        final List<String> genres = List.of("Genre 1", "Genre 2");
        final String imageURL = "https://www.example.com/image.jpg";

        Event event = new Event(id, name, artists, venue, city, country, date, priceMin, priceMax, ticketUrl, genres, imageURL);
        assert event.getId().equals(id);
        assert event.getName().equals(name);
        assert event.getArtists().equals(artists);
        assert event.getVenue().equals(venue);
        assert event.getCity().equals(city);
        assert event.getCountry().equals(country);
        assert event.getDate().equals(date);
        assert event.getPriceMin() == priceMin;
        assert event.getPriceMax() == priceMax;
        assert event.getTicketUrl().equals(ticketUrl);
    }
}
