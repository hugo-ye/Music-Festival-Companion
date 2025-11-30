package entity;

import java.time.LocalDate;
import java.util.List;

/**
 * A separate class to construct Event objects.
 * Corresponds to "ProductBuilder" in the UML.
 */
public class EventBuilder {
    private String id;
    private String name;
    private List<String> artists;
    private String venue;
    private String city;
    private String country;
    private LocalDate date;
    private int priceMin;
    private int priceMax;
    private String ticketUrl;
    private List<String> genres;
    private String imageURL;

    public EventBuilder id(String id) {
        this.id = id;
        return this;
    }

    public EventBuilder name(String name) {
        this.name = name;
        return this;
    }

    public EventBuilder artists(List<String> artists) {
        this.artists = artists;
        return this;
    }

    public EventBuilder venue(String venue) {
        this.venue = venue;
        return this;
    }

    public EventBuilder city(String city) {
        this.city = city;
        return this;
    }

    public EventBuilder country(String country) {
        this.country = country;
        return this;
    }

    public EventBuilder date(LocalDate date) {
        this.date = date;
        return this;
    }

    public EventBuilder priceMin(int priceMin) {
        this.priceMin = priceMin;
        return this;
    }

    public EventBuilder priceMax(int priceMax) {
        this.priceMax = priceMax;
        return this;
    }

    public EventBuilder ticketUrl(String ticketUrl) {
        this.ticketUrl = ticketUrl;
        return this;
    }

    public EventBuilder genres(List<String> genres) {
        this.genres = genres;
        return this;
    }

    public EventBuilder imageURL(String imageURL) {
        this.imageURL = imageURL;
        return this;
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
     * Constructs the Event object by calling its package-private constructor.
     */
    public Event build() {
        return new Event(this);
    }
}