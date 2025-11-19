package interface_adapter.search_event;

import entity.Event;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

public class SearchEventState {
    private String search_keyword = "";
    private String country = "";
    private String city = "";
    private List<String> genres = List.of();
    private String artist = "";
    private String startDate = "";
    private String endDate = "";
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private List<Event> events;
    private String errorMessage;

    public SearchEventState(SearchEventState copy) {
        search_keyword = copy.search_keyword;
        country = copy.country;
        city = copy.city;
        genres = copy.genres;
        artist = copy.artist;
        startDate = copy.startDate;
        endDate = copy.endDate;
    }

    public SearchEventState() {
    }

    public String getSearch_keyword() {
        return search_keyword;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public List<String> getGenre() {
        return genres;
    }

    public String getArtist() {
        return artist;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setSearch_keyword(String search_keyword) {
        this.search_keyword = search_keyword;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setGenre(List<String> genres) {
        this.genres = genres;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
