package interface_adapter.delete_event_list;

import use_case.delete_event_list.DeleteEventListOutputBoundary;
import use_case.delete_event_list.DeleteEventListOutputData;

public class DeleteEventListPresenter implements DeleteEventListOutputBoundary {

    private final DeleteEventListViewModel viewModel;

    public DeleteEventListPresenter(DeleteEventListViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(DeleteEventListOutputData outputData) {
        // On success: store the deleted list id and a success message
        viewModel.setListId(outputData.getListId());
        viewModel.setMessage("List deleted successfully!");
    }

    @Override
    public void prepareFailView(String errorMessage) {
        // On failure: clear id (nothing deleted) and show error message
        viewModel.setListId(null);
        viewModel.setMessage(errorMessage);
    }

    public DeleteEventListViewModel getViewModel() {
        return viewModel;
    }
}