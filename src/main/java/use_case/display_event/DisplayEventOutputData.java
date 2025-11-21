package use_case.display_event;

import entity.Event;

import java.time.LocalDate;
import java.util.List;

public class DisplayEventOutputData {
    //private Event event;
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

    public DisplayEventOutputData(String eventName, List<String> artists, String venue, String city,
                                  String country, LocalDate date, int priceMin, int priceMax, String ticketUrl,
                                  List<String> genres, String imageUrl, boolean hasPrice ){
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
    }

    //getters
    public String getEventName(){return eventName;}
    public List<String> getArtists(){return artists;}
    public String getVenue(){return venue;}
    public String getCity(){return city;}
    public String getCountry(){return country;}
    public LocalDate getDate(){return date;}
    public int getPriceMax() {return priceMax;}
    public int getPriceMin() {return priceMin;}
    public List<String> getGenres(){return genres;}
    public String getTicketUrl(){return ticketUrl;}
    public String getImageUrl(){return imageUrl;}
    public boolean getHasPrice(){return hasPrice;}


}
