package entity;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * Represents an event in our program.
 */
public class Event {
    private final String id;
    private final String name;
    private final List<String> artists;
    private final String venue;
    private final String city;
    private final String country;
    private final LocalDate date;
    private final int priceMin;
    private final int priceMax;
    private final String ticketUrl;
    private final List<String> genres;
    private final String imageURL;

    /**
     * Constructor for an event.
     * @param builder the builder that constructs the event.
     */
    Event(EventBuilder builder) {
        this.id = builder.getId();
        this.name = builder.getName();
        this.artists = builder.getArtists();
        this.venue = builder.getVenue();
        this.city = builder.getCity();
        this.country = builder.getCountry();
        this.date = builder.getDate();
        this.priceMin = builder.getPriceMin();
        this.priceMax = builder.getPriceMax();
        this.ticketUrl = builder.getTicketUrl();
        this.genres = builder.getGenres();
        this.imageURL = builder.getImageURL();
    }

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

    /**
     * Defines business logic for equality. Two events are considered equal if they have the same ID.
     */
    @Override
    public boolean equals(Object o) {
        boolean result = false;
        if (o != null && getClass() == o.getClass()) {
            final Event event = (Event) o;
            result = Objects.equals(id, event.id);
        }
        return result;
    }

    /**
     * Defines the hash code of an event based on its ID.
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
