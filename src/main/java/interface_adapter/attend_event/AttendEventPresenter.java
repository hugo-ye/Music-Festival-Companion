package interface_adapter.attend_event;

import interface_adapter.ViewManagerModel;
import interface_adapter.display_event.DisplayEventState;
import interface_adapter.display_event.DisplayEventViewModel;
import use_case.attend_event.AttendEventOutputBoundary;
import use_case.attend_event.AttendEventOutputData;

public class AttendEventPresenter implements AttendEventOutputBoundary {

    // We reuse the existing ViewModel!
    private final DisplayEventViewModel displayEventViewModel;
    private final ViewManagerModel viewManagerModel;

    public AttendEventPresenter(DisplayEventViewModel displayEventViewModel,
                                ViewManagerModel viewManagerModel) {
        this.displayEventViewModel = displayEventViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(AttendEventOutputData outputData) {
        DisplayEventState state = displayEventViewModel.getState();

        // Update the message in the state
        state.setAttendMessage("Successfully attending: " + outputData.getEventName());

        displayEventViewModel.setState(state);

        // Fire a specific event so the View shows a popup
        displayEventViewModel.firePropertyChanged("attend_message");
    }

    @Override
    public void prepareFailView(String error_message) {
        DisplayEventState state = displayEventViewModel.getState();

        state.setAttendMessage("Error: " + error_message);

        displayEventViewModel.setState(state);
        displayEventViewModel.firePropertyChanged("attend_message");
    }
}