package use_case.display_event;

import entity.Event;

import java.time.LocalDate;
import java.util.List;

public class DisplayEventInteractor implements DisplayEventInputBoundary{
    private final DisplayEventOutputBoundary presenter;

    public DisplayEventInteractor(DisplayEventDataAccessInterface dataAccess, DisplayEventOutputBoundary presenter) {
        this.presenter = presenter;
    }
    @Override
    public void execute(DisplayEventInputData input) {
        Event event = input.getEvent();

        final LocalDate date = event.getDate();
        final int priceMin = event.getPriceMin();
        final int priceMax = event.getPriceMax();
        String formattedDate = date != null ? date.toString() : "TBD";
        String priceRange = (priceMin > 0 || priceMax > 0) ?
                String.format("Min: %d, Max: %d", priceMin, priceMax) : "N/A";

        final String eventName = event.getName() + " (id: " + event.getId() +")";
        final String artist = String.join(", ", event.getArtists());
        final String venues = event.getVenue();
        final String location = event.getCity() + ", " + event.getCountry();
        final String time = formattedDate;
        final String genre = String.join(", ", event.getGenres());
        final String price = priceRange;
        final String ticket = event.getTicketUrl();
        final String image = event.getImageURL();

        DisplayEventOutputData outputData = new DisplayEventOutputData(eventName, artist, venues, location, time, genre,
                price, ticket, image);
        presenter.prepareSuccessView(outputData);

    }
}
