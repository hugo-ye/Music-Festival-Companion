package interface_adapter.attend_event;

import interface_adapter.ViewManagerModel;
import interface_adapter.display_event.DisplayEventState;
import interface_adapter.display_event.DisplayEventViewModel;
import use_case.attend_event.AttendEventOutputBoundary;
import use_case.attend_event.AttendEventOutputData;

public class AttendEventPresenter implements AttendEventOutputBoundary {
    private final DisplayEventViewModel attendEventViewModel;
    private final ViewManagerModel viewManagerModel;

    public AttendEventPresenter(DisplayEventViewModel attendEventViewModel,
                                ViewManagerModel viewManagerModel){
        this.attendEventViewModel = attendEventViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(AttendEventOutputData attendEventOutputData){
        DisplayEventState attendEventState = attendEventViewModel.getState();
        // to display something like Added coachella
        attendEventState.setEventName(attendEventOutputData.getEventName());
        // viewManagerModel.setActiveView(attendEventViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
        attendEventViewModel.setState(attendEventState);
        attendEventViewModel.firePropertyChanged();
    }
    @Override
    public void prepareFailView(String error_message ){
        DisplayEventState attendEventState = attendEventViewModel.getState();
        attendEventViewModel.setState(attendEventState);
        attendEventViewModel.firePropertyChanged();
        // viewManagerModel.setActiveView(attendEventViewModel.getViewName());
        viewManagerModel.firePropertyChanged(error_message);
    }
}
