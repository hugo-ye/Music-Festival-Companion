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


    }
}
