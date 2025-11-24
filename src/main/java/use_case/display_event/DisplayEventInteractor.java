package use_case.display_event;

import entity.Event;
import entity.EventList;

import java.time.LocalDate;
import java.util.List;


public class DisplayEventInteractor implements DisplayEventInputBoundary{
    private final DisplayEventOutputBoundary presenter;
    private final DisplayEventDataAccessInterface dataAccess;

    public DisplayEventInteractor(DisplayEventOutputBoundary presenter, DisplayEventDataAccessInterface dataAccess) {
        this.presenter = presenter;
        this.dataAccess = dataAccess;
    }
    @Override
    public void execute(DisplayEventInputData input) {

        Event event = input.getEvent();
        final boolean hasPrice;
        final int priceMin = event.getPriceMin();
        final int priceMax = event.getPriceMax();
        final LocalDate date = event.getDate();
        final String eventName = event.getName();
        final List<String> artist = event.getArtists();
        final String venues = event.getVenue();
        final String city = event.getCity();
        final String country = event.getCountry();
        final List<String> genre = event.getGenres();
        final String ticket = event.getTicketUrl();
        final String image = event.getImageURL();
        List<EventList> existingLists = dataAccess.getEventLists();
        hasPrice = priceMin > 0 || priceMax > 0;



        DisplayEventOutputData outputData = new DisplayEventOutputData(eventName, artist, venues, city, country, date,
                priceMin, priceMax, ticket,genre, image, hasPrice, event, existingLists);


        presenter.prepareSuccessView(outputData);

    }
}
