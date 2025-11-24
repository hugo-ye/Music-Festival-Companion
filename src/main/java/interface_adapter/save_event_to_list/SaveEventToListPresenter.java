package interface_adapter.save_event_to_list;

import use_case.save_event_to_list.SaveEventToListOutputBoundary;
import use_case.save_event_to_list.SaveEventToListOutputData;

public class SaveEventToListPresenter implements SaveEventToListOutputBoundary {
    private final SaveEventToListViewModel viewModel;

    public SaveEventToListPresenter(SaveEventToListViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(SaveEventToListOutputData outputData) {
        SaveEventToListState state = viewModel.getState();

        state.setEvent(outputData.getEvent());
        state.setEventLists(outputData.getEventLists());
        state.setMessage(outputData.getMessage());

        viewModel.firePropertyChanged("message");
    }
}