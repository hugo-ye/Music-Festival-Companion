package interface_adapter.save_event_to_list;

import use_case.save_event_to_list.SaveEventToListOutputBoundary;
import use_case.save_event_to_list.SaveEventToListOutputData;

/**
 * Presenter for the SaveEventToList use case.
 * <p>
 *     this class implements {@link SaveEventToListOutputBoundary} and is responsible for taking an output data from
 *     the interactor and updating the {@link SaveEventToListViewModel}.
 * </p>
 */
public class SaveEventToListPresenter implements SaveEventToListOutputBoundary {
    private final SaveEventToListViewModel viewModel;

    public SaveEventToListPresenter(SaveEventToListViewModel viewModel) {
        this.viewModel = viewModel;
    }

    /**
     * Presents the output by updating the viewModel.
     * @param outputData the output data from the interactor.
     */
    @Override
    public void present(SaveEventToListOutputData outputData) {
        viewModel.setEvent(outputData.getEvent());
        viewModel.setEventList(outputData.getEventLists());
        viewModel.setMessage(outputData.getMessage());
    }
}
