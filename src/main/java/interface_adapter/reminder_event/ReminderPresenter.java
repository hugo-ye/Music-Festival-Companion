package interface_adapter.reminder_event;

import use_case.reminder_event.ShowUpcomingOutputBoundary;
import use_case.reminder_event.ShowUpcomingOutputData;

/** Presenter moves use-case output into the ViewModel. */
public final class ReminderPresenter implements ShowUpcomingOutputBoundary {
    private final ReminderViewModel viewModel;

    public ReminderPresenter(ReminderViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(ShowUpcomingOutputData output) {
        viewModel.setLines(output.getLines());
    }
}

