package use_case.display_event;

import java.time.LocalDate;
import java.util.List;

import entity.Event;
import entity.EventList;

/**
 * Interactor for the DisplayEvent use case.
 */
public class DisplayEventInteractor implements DisplayEventInputBoundary {
    private final DisplayEventOutputBoundary presenter;
    private final DisplayEventDataAccessInterface dataAccess;

    public DisplayEventInteractor(DisplayEventOutputBoundary presenter,
                                  DisplayEventDataAccessInterface dataAccess) {
        this.presenter = presenter;
        this.dataAccess = dataAccess;
    }

    /**
     * Extracts relevant data from an event and creates an outputData.
     *
     * @param input the input data containing the event to display.
     */
    @Override
    public void execute(DisplayEventInputData input) {

        final Event event = input.getEvent();
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
        final List<EventList> existingLists = dataAccess.getEventLists();
        final boolean hasPrice = priceMin > 0 || priceMax > 0;

        final DisplayEventOutputData outputData = new DisplayEventOutputData(
                eventName, artist, venues, city, country, date,
                priceMin, priceMax, ticket, genre, image, hasPrice, event, existingLists);

        presenter.prepareSuccessView(outputData);

    }
}
