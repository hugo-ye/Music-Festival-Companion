package interface_adapter.display_notifications;

import use_case.display_notifications.DisplayNotificationsOutputBoundary;
import use_case.display_notifications.DisplayNotificationsOutputData;

public class DisplayNotificationsPresenter implements DisplayNotificationsOutputBoundary {
    private final DisplayNotificationsViewModel displayNotificationsViewModel;

    public DisplayNotificationsPresenter(DisplayNotificationsViewModel viewModel) {
        this.displayNotificationsViewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(DisplayNotificationsOutputData outputData) {
        final DisplayNotificationsState state = displayNotificationsViewModel.getState();
        state.setMessage(outputData.getMessage());
        displayNotificationsViewModel.setState(state);
        displayNotificationsViewModel.firePropertyChanged("message");
    }
}