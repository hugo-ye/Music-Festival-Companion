package interface_adapter.display_event;

import java.util.List;

import entity.Event;
import entity.EventList;

public class DisplayEventState {
    private String eventName = "";
    private String artists = "";
    private String venue = "";
    private String location = "";
    private String date = "";
    private String genres = "";
    private String price = "";
    private String ticketUrl = "";
    private String imageUrl = "";
    private Event event;
    private List<EventList> availableLists;

    // --- NEW FIELD ---
    private String attendMessage = "";

    public String getEventName() {
        return eventName;
    }

    public String getArtists() {
        return artists;
    }

    public String getVenue() {
        return venue;
    }

    public String getDate() {
        return date;
    }

    public String getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getGenres() {
        return genres;
    }

    public String getTicketUrl() {
        return ticketUrl;
    }

    public String getLocation() {
        return location;
    }

    public Event getEvent() {
        return event;
    }

    public List<EventList> getAvailableLists() {
        return availableLists;
    }

    public String getAttendMessage() {
        return attendMessage;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setAttendMessage(String attendMessage) {
        this.attendMessage = attendMessage;
    }

    public void setAvailableLists(List<EventList> availableLists) {
        this.availableLists = availableLists;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public void setTicketUrl(String ticketUrl) {
        this.ticketUrl = ticketUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public void setArtists(String artists) {
        this.artists = artists;
    }
}
