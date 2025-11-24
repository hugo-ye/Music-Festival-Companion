package entity;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Objects;

public class Event {
    final String id;
    final String name;
    final List<String> artists;
    final String venue;
    final String city;
    final String country;
    final LocalDate date;
    final int priceMin;
    final int priceMax;
    final String ticketUrl;
    final List<String> genres;
    final String imageURL;

    public Event(String id, String name, List<String> artists, String venue, String city, String country, LocalDate date, int priceMin, int priceMax, String ticketUrl, List<String> genres, String imageURL) {
        this.id = id;
        this.name = name;
        this.artists = artists;
        this.venue = venue;
        this.city = city;
        this.country = country;
        this.date = date;
        this.priceMin = priceMin;
        this.priceMax = priceMax;
        this.ticketUrl = ticketUrl;
        this.genres = genres;
        this.imageURL = imageURL;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getArtists() {
        return artists;
    }

    public String getVenue() {
        return venue;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getPriceMin() {
        return priceMin;
    }

    public int getPriceMax() {
        return priceMax;
    }

    public String getTicketUrl() {
        return ticketUrl;
    }

    public List<String> getGenres() {
        return genres;
    }

    public String getImageURL() {
        return imageURL;
    }


    @Override
    public String toString() {
        // Simple date formatter for human readability
        String formattedDate = date != null ? date.toString() : "TBD";
        String priceRange = (priceMin > 0 || priceMax > 0) ?
                String.format("Min: %d, Max: %d", priceMin, priceMax) : "N/A";

        return "--- EVENT DETAILS ---\n" +
                "Event Name: " + name + " (" + id + ")\n" +
                "Artist(s):  " + String.join(", ", artists) + "\n" +
                "Location:   " + city + ", " + country + "\n" +
                "Venue:      " + venue + "\n" +
                "Date/Time:  " + formattedDate + "\n" +
                "Genres:     " + String.join(", ", genres) + "\n" +
                "Price ($):  " + priceRange + "\n" +
                "Tickets:    " + ticketUrl + "\n" +
                "Image:      " + imageURL + "\n" +
                "----------------------";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
