package interface_adapter.display_event_lists;

import interface_adapter.ViewManagerModel;
import interface_adapter.create_event_list.CreateEventListState;
import interface_adapter.create_event_list.CreateEventListViewModel;
import use_case.display_event_lists.DisplayEventListsOutputBoundary;
import use_case.display_event_lists.DisplayEventListsOutputData;

public class DisplayEventListsPresenter implements DisplayEventListsOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final CreateEventListViewModel createEventListViewModel;

    public DisplayEventListsPresenter(ViewManagerModel viewManagerModel,
                                      CreateEventListViewModel createEventListViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.createEventListViewModel = createEventListViewModel;

    }

    @Override
    public void prepareSuccessView(DisplayEventListsOutputData outputData) {
        final CreateEventListState state = createEventListViewModel.getState();
        state.setLists(outputData.getEventLists());
        createEventListViewModel.setState(state);
        createEventListViewModel.firePropertyChanged();

        viewManagerModel.setState(createEventListViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        System.out.println("could not get the lists from current user");
    }
}
