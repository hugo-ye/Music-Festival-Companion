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
        DisplayEventState displayEventState = viewModel.getState();
        displayEventState.setEventName(outputData.getEventName());
        displayEventState.setArtists(outputData.getArtists());
        displayEventState.setDate(outputData.getDate());
        displayEventState.setGenres(outputData.getGenres());
        displayEventState.setLocation(outputData.getLocation());
        displayEventState.setImageUrl(outputData.getImageUrl());
        displayEventState.setVenue(outputData.getVenue());
        displayEventState.setPrice(outputData.getPrice());
        displayEventState.setTicketUrl(outputData.getTicketUrl());

        this.viewModel.setState(displayEventState);
        this.viewModel.firePropertyChanged();
        this.viewManagerModel.setActiveView(viewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }
}
