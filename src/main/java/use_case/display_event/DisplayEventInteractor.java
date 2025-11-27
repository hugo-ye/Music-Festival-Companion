package use_case.display_event;

import entity.Event;

import java.time.LocalDate;
import java.util.List;

/**
 * Interactor for rhe DisplayEvent use case.
 */
public class DisplayEventInteractor implements DisplayEventInputBoundary{
    private final DisplayEventOutputBoundary presenter;

    public DisplayEventInteractor(DisplayEventOutputBoundary presenter) {
        this.presenter = presenter;
    }

    /**
     * Extracts relevant data from an event and creates an outputData
     * @param input the input data containing the event to display.
     */
    @Override
    public void execute(DisplayEventInputData input) {

        Event event = input.getEvent();

        // String formattedDate = date != null ? date.toString() : "TBD";
        // String priceRange = (priceMin > 0 || priceMax > 0) ?
                // String.format("Min: %d, Max: %d", priceMin, priceMax) : "N/A";
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
        hasPrice = priceMin > 0 || priceMax > 0;



        DisplayEventOutputData outputData = new DisplayEventOutputData(eventName, artist, venues, city, country, date,
                priceMin, priceMax, ticket,genre, image, hasPrice);


        presenter.prepareSuccessView(outputData);

    }
}
