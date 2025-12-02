package use_case.display_event;

import java.time.LocalDate;
import java.util.List;

import entity.Event;
import entity.EventList;

public class DisplayEventOutputData {
    private final String eventName;
    private final List<String> artists;
    private final String venue;
    private final String city;
    private final String country;
    private final LocalDate date;
    private final int priceMin;
    private final int priceMax;
    private final String ticketUrl;
    private final List<String> genres;
    private final String imageUrl;
    private boolean hasPrice;
    private Event event;
    private List<EventList> existingLists;

    public DisplayEventOutputData(String eventName, List<String> artists, String venue, String city,
                                  String country, LocalDate date, int priceMin, int priceMax, String ticketUrl,
                                  List<String> genres, String imageUrl, boolean hasPrice, Event event,
                                  List<EventList> existingLists) {
        this.eventName = eventName;
        this.artists = artists;
        this.venue = venue;
        this.city = city;
        this.country = country;
        this.date = date;
        this.priceMin = priceMin;
        this.priceMax = priceMax;
        this.ticketUrl = ticketUrl;
        this.genres = genres;
        this.imageUrl = imageUrl;
        this.hasPrice = hasPrice;
        this.event = event;
        this.existingLists = existingLists;
    }

    public String getEventName() {
        return eventName;
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

    public int getPriceMax() {
        return priceMax;
    }

    public int getPriceMin() {
        return priceMin;
    }

    public List<String> getGenres() {
        return genres;
    }

    public String getTicketUrl() {
        return ticketUrl;
    }

    public boolean hasPrice() {
        return hasPrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Event getEvent() {
        return event;
    }

    public List<EventList> getExistingLists() {
        return existingLists;
    }
}
