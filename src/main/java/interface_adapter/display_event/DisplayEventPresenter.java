package interface_adapter.display_event;

import org.jetbrains.annotations.NotNull;

import interface_adapter.ViewManagerModel;
import use_case.display_event.DisplayEventOutputBoundary;
import use_case.display_event.DisplayEventOutputData;

/**
 * Presenter for the DisplayEvent use case.
 */
public class DisplayEventPresenter implements DisplayEventOutputBoundary {
    private final DisplayEventViewModel viewModel;
    private final ViewManagerModel viewManagerModel;

    public DisplayEventPresenter(DisplayEventViewModel displayEventViewModel, ViewManagerModel viewManagerModel) {
        this.viewModel = displayEventViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    /**
     * Prepares the UI when the event is successfully displayed.
     * @param outputData the event details produced by the interactor.
     */
    @Override
    public void prepareSuccessView(DisplayEventOutputData outputData) {
        final String date = getDate(outputData);
        final String price;
        if (outputData.getHasPrice()) {
            price = String.format("Min: %d, Max: %d", outputData.getPriceMin(), outputData.getPriceMax());
        }
        else {
            price = "N/A";
        }
        final DisplayEventState displayEventState = viewModel.getState();
        displayEventState.setEventName(outputData.getEventName());
        displayEventState.setArtists(String.join(", ", outputData.getArtists()));
        displayEventState.setDate(date);
        displayEventState.setGenres(String.join(", ", outputData.getGenres()));
        displayEventState.setLocation(outputData.getCity() + ", " + outputData.getCountry());
        displayEventState.setImageUrl(outputData.getImageUrl());
        displayEventState.setVenue(outputData.getVenue());
        displayEventState.setPrice(price);
        displayEventState.setTicketUrl(outputData.getTicketUrl());
        displayEventState.setEvent(outputData.getEvent());
        displayEventState.setAvailableLists(outputData.getExistingLists());

        this.viewModel.setState(displayEventState);
        this.viewModel.firePropertyChanged("refresh");

    }

    @NotNull
    private static String getDate(DisplayEventOutputData outputData) {
        String result = "TBD";
        if (outputData.getDate() != null) {
            result = outputData.getDate().toString();
        }
        return result;
    }
}
