package use_case.display_event;

import entity.Event;

public class DisplayEventOutputData {
    //private Event event;
    private final String eventName;
    private final String artists;
    private final String venue;
    private final String location;
    private final String date;
    private final String genres;
    private final String price;
    private final String ticketUrl;
    private final String imageUrl;

    public DisplayEventOutputData(String eventName, String artists, String venue, String location, String date,
                                  String genres, String price, String ticketUrl, String imageUrl){
        this.eventName = eventName;
        this.artists = artists;
        this.venue = venue;
        this.location = location;
        this.date = date;
        this.genres = genres;
        this.price = price;
        this.ticketUrl = ticketUrl;
        this.imageUrl = imageUrl;
    }

    //getters
    public String getEventName(){return eventName;}
    public String getArtists(){return artists;}
    public String getVenue(){return venue;}
    public String getLocation(){return location;}
    public String getDate(){return date;}
    public String getGenres(){return genres;}
    public String getPrice(){return price;}
    public String getTicketUrl(){return ticketUrl;}
    public String getImageUrl(){return imageUrl;}


}
