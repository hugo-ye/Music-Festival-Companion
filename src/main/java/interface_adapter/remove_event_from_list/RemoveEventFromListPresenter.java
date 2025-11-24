package interface_adapter.remove_event_from_list;

import use_case.remove_event_from_list.RemoveEventFromListOutputBoundary;
import use_case.remove_event_from_list.RemoveEventFromListOutputData;

public class RemoveEventFromListPresenter implements RemoveEventFromListOutputBoundary {

    private final RemoveEventFromListViewModel viewModel;

    public RemoveEventFromListPresenter(RemoveEventFromListViewModel viewModel) {
        this.viewModel = viewModel;

    }

    @Override
    public void present(RemoveEventFromListOutputData outputData) {
        viewModel.setRemoveEventMessage(outputData.getMessage());
    }
}
