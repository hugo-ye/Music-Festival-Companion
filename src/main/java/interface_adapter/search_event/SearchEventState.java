package interface_adapter.search_event;


import java.util.Collections;
import java.util.List;

import entity.Event;

/**
 * Represents the state for the search event feature.
 * It holds the data entered by the user and the results of the search.
 */
public class SearchEventState {

    private String searchKeyword = "";
    private String country = "";
    private String city = "";
    private List<String> genres = Collections.emptyList();
    private String artist = "";
    private String startDate = "";
    private String endDate = "";
    private List<Event> events;
    private String errorMessage;
    private String username;

    /**
     * Gets the username.
     * @return the username.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Gets the search keyword.
     * @return the search keyword.
     */
    public String getSearchKeyword() {
        return this.searchKeyword;
    }

    /**
     * Gets the country.
     * @return the country.
     */
    public String getCountry() {
        return this.country;
    }

    /**
     * Gets the city.
     * @return the city.
     */
    public String getCity() {
        return this.city;
    }

    /**
     * Gets the list of genres.
     * @return the list of genres.
     */
    public List<String> getGenre() {
        return this.genres;
    }

    /**
     * Gets the artist.
     * @return the artist.
     */
    public String getArtist() {
        return this.artist;
    }

    /**
     * Gets the start date.
     * @return the start date.
     */
    public String getStartDate() {
        return this.startDate;
    }

    /**
     * Gets the end date.
     * @return the end date.
     */
    public String getEndDate() {
        return this.endDate;
    }

    /**
     * Sets the search keyword.
     * @param searchKeyword the new search keyword.
     */
    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    /**
     * Sets the country.
     * @param country the new country.
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Sets the city.
     * @param city the new city.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Sets the list of genres.
     * @param genre the new list of genres.
     */
    public void setGenre(List<String> genre) {
        this.genres = genre;
    }

    /**
     * Sets the username.
     * @param username the new username.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets the artist.
     * @param artist the new artist.
     */
    public void setArtist(String artist) {
        this.artist = artist;
    }

    /**
     * Sets the start date.
     * @param startDate the new start date.
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * Sets the end date.
     * @param endDate the new end date.
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * Sets the list of events.
     * @param events the new list of events.
     */
    public void setEvents(List<Event> events) {
        this.events = events;
    }

    /**
     * Gets the list of events.
     * @return the list of events.
     */
    public List<Event> getEvents() {
        return this.events;
    }

    /**
     * Sets the error message.
     * @param errorMessage the new error message.
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Gets the error message.
     * @return the error message.
     */
    public String getErrorMessage() {
        return this.errorMessage;
    }
}
