package interface_adapter.display_event;

import interface_adapter.ViewManagerModel;
import use_case.display_event.DisplayEventOutputBoundary;
import use_case.display_event.DisplayEventOutputData;

public class DisplayEventPresenter implements DisplayEventOutputBoundary {
    private final DisplayEventViewModel viewModel;
    private final ViewManagerModel viewManagerModel;

    public DisplayEventPresenter(DisplayEventViewModel displayEventViewModel, ViewManagerModel viewManagerModel) {
        this.viewModel = displayEventViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(DisplayEventOutputData outputData) {
        String date = outputData.getDate() != null ? outputData.getDate().toString() : "TBD";
        String price;
        if(outputData.getHasPrice()){
            price = String.format("Min: %d, Max: %d", outputData.getPriceMin(), outputData.getPriceMax());
        }else{
            price = "N/A";
        }
        DisplayEventState displayEventState = viewModel.getState();
        displayEventState.setEventName(outputData.getEventName());
        displayEventState.setArtists(String.join(", ", outputData.getArtists()));
        displayEventState.setDate(date);
        displayEventState.setGenres(String.join(", ", outputData.getGenres()));
        displayEventState.setLocation(outputData.getCity() + ", " + outputData.getCountry() );
        displayEventState.setImageUrl(outputData.getImageUrl());
        displayEventState.setVenue(outputData.getVenue());
        displayEventState.setPrice(price);
        displayEventState.setTicketUrl(outputData.getTicketUrl());

        this.viewModel.setState(displayEventState);
        this.viewModel.firePropertyChanged("refresh");

        viewManagerModel.setState(viewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();

    }
}
