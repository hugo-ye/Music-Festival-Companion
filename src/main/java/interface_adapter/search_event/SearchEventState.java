package interface_adapter.search_event;

import java.util.List;

import entity.Event;

public class SearchEventState {
    private String searchKeyword = "";
    private String country = "";
    private String city = "";
    private List<String> genres = List.of();
    private String artist = "";
    private String startDate = "";
    private String endDate = "";
    private List<Event> events;
    private String errorMessage;
    private String username;

    public String getUsername() {
        return username;
    }

    public String getSearchKeyword() {
        return searchKeyword;
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

    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
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

    public void setUsername(String username) {
        this.username = username;
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

}
