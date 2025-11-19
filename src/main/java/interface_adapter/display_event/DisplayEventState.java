package interface_adapter.display_event;

import entity.Event;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class DisplayEventState {
    private  String eventName = "";
    private  String artists = "";
    private  String venue = "";
    private  String location = "";
    private  String date = "";
    private  String genres = "";
    private  String price = "";
    private  String ticketUrl = "";
    private  String imageUrl = "";

    // private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    // remove this
    /*
    public DisplayEventState(DisplayEventState copy){
        eventName = copy.eventName;
        artists = copy.artists;
        venue = copy.venue;
        location = copy.location;
        date = copy.date;
        genres = copy.genres;
        price = copy.price;
        ticketUrl = copy.ticketUrl;
        imageUrl = copy.imageUrl;
    }

     */

    // public DisplayEventState(){
    // }

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
    //setters
    public void setEventName(String eventName){
        this.eventName = eventName;
    }

    public void setArtists(String artists){
        this.artists = artists;
    }

    public void setVenue(String venue){
        this.venue = venue;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public void setDate(String date){
        this.date = date;
    }

    public void setGenres(String genres){
        this.genres = genres;
    }

    public void setPrice(String price){
        this.price = price;
    }

    public void setTicketUrl(String ticketUrl){
        this.ticketUrl = ticketUrl;
    }

    public void setImageUrl(String imageUrl){
        this.imageUrl = imageUrl;
    }
    // remove this
    // public void addPropertyChangeListener(PropertyChangeListener listener){
        // support.addPropertyChangeListener(listener);
    // }
}
