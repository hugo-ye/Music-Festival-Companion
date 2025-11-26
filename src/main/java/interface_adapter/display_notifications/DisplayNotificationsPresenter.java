package interface_adapter.display_notifications;

import use_case.display_notifications.DisplayNotificationsOutputBoundary;
import use_case.display_notifications.DisplayNotificationsOutputData;

public class DisplayNotificationsPresenter implements DisplayNotificationsOutputBoundary {
    private final DisplayNotificationsViewModel viewModel;

    public DisplayNotificationsPresenter(DisplayNotificationsViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(DisplayNotificationsOutputData outputData) {
        viewModel.setMessage(outputData.getMessage());
        viewModel.firePropertyChanged("message");
    }
}