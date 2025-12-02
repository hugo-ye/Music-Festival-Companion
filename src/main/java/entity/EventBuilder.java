package entity;

import java.time.LocalDate;
import java.util.List;

/**
 * A builder class to construct Event objects.
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

    /**
     * Add id for event.
     * @param id target id to be added.
     * @return EventBuilder that with id.
     */
    public EventBuilder id(String id) {
        this.id = id;
        return this;
    }

    /**
     * Add name fpr event.
     * @param name name of target event.
     * @return EventBuilder with name
     */
    public EventBuilder name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Add artists for event.
     * @param artists list of artists of the event.
     * @return EventBuilder with artists.
     */
    public EventBuilder artists(List<String> artists) {
        this.artists = artists;
        return this;
    }

    /**
     * Add venue for event.
     * @param venue venue of the event.
     * @return EventBuilder with venue.
     */
    public EventBuilder venue(String venue) {
        this.venue = venue;
        return this;
    }

    /**
     * Add city for event.
     * @param city target city of the event.
     * @return EventBuilder with city.
     */
    public EventBuilder city(String city) {
        this.city = city;
        return this;
    }

    /**
     * Add country for event.
     * @param country target country of the event.
     * @return EventBuilder with country.
     */
    public EventBuilder country(String country) {
        this.country = country;
        return this;
    }

    /**
     * Add date for event.
     * @param date target event date.
     * @return EventBuilder with date.
     */
    public EventBuilder date(LocalDate date) {
        this.date = date;
        return this;
    }

    /**
     * Add minimum price for event.
     * @param priceMin minimum ticket price.
     * @return EventBuilder with minimum price.
     */
    public EventBuilder priceMin(int priceMin) {
        this.priceMin = priceMin;
        return this;
    }

    /**
     * Add maximum price for event.
     * @param priceMax maximum ticket price.
     * @return EventBuilder with maximum price.
     */
    public EventBuilder priceMax(int priceMax) {
        this.priceMax = priceMax;
        return this;
    }

    /**
     * Add ticket URL for event.
     * @param ticketUrl URL for purchasing tickets.
     * @return EventBuilder with ticket URL.
     */
    public EventBuilder ticketUrl(String ticketUrl) {
        this.ticketUrl = ticketUrl;
        return this;
    }

    /**
     * Add genres for event.
     * @param genres list of genres of the event.
     * @return EventBuilder with genres.
     */
    public EventBuilder genres(List<String> genres) {
        this.genres = genres;
        return this;
    }

    /**
     * Add image URL for event.
     * @param imageURL URL of the event image.
     * @return EventBuilder with image URL.
     */
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
     * @return the constructed Event object.
     */
    public Event build() {
        return new Event(this);
    }
}
