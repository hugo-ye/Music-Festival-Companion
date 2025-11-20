package interface_adapter.attend_event;

import interface_adapter.ViewManagerModel;
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
        // DisplayEventViewModel attendEventState = attendEventViewModel.getState();
        /*
        attendEventState.setUsername(attendEventOutputData.getUsername());
        attendEventState.setEventName(attendEventOutputData.getEventName());
        attendEventState.setMessage(attendEventOutputData.getAdded());

         */
        // attendEventViewModel.setState(attendEventViewModel);
        // attendEventViewModel.firePropertyChanged();
        viewManagerModel.setActiveView(attendEventViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
    @Override
    public void prepareFailView(AttendEventOutputData attendEventOutputData){
        // DisplayEventViewModel attendEventState = attendEventViewModel.getState();
        /*
        attendEventState.setUsername(attendEventOutputData.getUsername());
        attendEventState.setEventName(attendEventOutputData.getEventName());
        attendEventState.setMessage(attendEventOutputData.getCantAdd());

         */
        // attendEventViewModel.setState(attendEventState);
        // attendEventViewModel.firePropertyChanged();
        viewManagerModel.setActiveView(attendEventViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
