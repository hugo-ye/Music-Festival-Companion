package interface_adapter.create_event_list;

import use_case.create_event_list.CreateEventListOutputBoundary;
import use_case.create_event_list.CreateEventListOutputData;

public class CreateEventListPresenter implements CreateEventListOutputBoundary {

    private final CreateEventListViewModel viewModel;

    public CreateEventListPresenter(CreateEventListViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(CreateEventListOutputData outputData) {
        // Update ViewModel for success
        viewModel.setListId(outputData.getListId());
        viewModel.setListName(outputData.getListName());
    }

    @Override
    public void prepareFailView(String errorMessage) {
        // Clear data, only show error message
        viewModel.setListId(null);
        viewModel.setListName(null);
        viewModel.setMessage(errorMessage);
    }

    public CreateEventListViewModel getViewModel() {
        return viewModel;
    }
}