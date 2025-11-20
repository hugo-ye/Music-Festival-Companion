package interface_adapter.attend_event;

import interface_adapter.ViewModel;

public class AttendEventViewModel extends ViewModel<AttendEventState> {
    public AttendEventViewModel(String viewName){
        super(viewName);
        setState(new AttendEventState());
    }
}
