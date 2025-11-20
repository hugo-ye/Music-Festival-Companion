package interface_adapter.attend_event;

import interface_adapter.ViewManagerModel;
import use_case.attend_event.AttendEventOutputBoundary;
import use_case.attend_event.AttendEventOutputData;

public class AttendEventPresenter implements AttendEventOutputBoundary {
    private final AttendEventViewModel attendEventViewModel;
    private final ViewManagerModel viewManagerModel;

    public AttendEventPresenter(AttendEventViewModel attendEventViewModel,
                                ViewManagerModel viewManagerModel){
        this.attendEventViewModel = attendEventViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(AttendEventOutputData attendEventOutputData){
        AttendEventState attendEventState = attendEventViewModel.getState();
        attendEventState.setUsername(attendEventOutputData.getUsername());
        attendEventState.setEventName(attendEventOutputData.getEventName());
        attendEventState.setMessage(attendEventOutputData.getAdded());
        attendEventViewModel.setState(attendEventState);
        attendEventViewModel.firePropertyChanged();
        viewManagerModel.setActiveView(attendEventViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
    @Override
    public void prepareFailView(AttendEventOutputData attendEventOutputData){
        AttendEventState attendEventState = attendEventViewModel.getState();
        attendEventState.setUsername(attendEventOutputData.getUsername());
        attendEventState.setEventName(attendEventOutputData.getEventName());
        attendEventState.setMessage(attendEventOutputData.getCantAdd());
        attendEventViewModel.setState(attendEventState);
        attendEventViewModel.firePropertyChanged();
        viewManagerModel.setActiveView(attendEventViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
