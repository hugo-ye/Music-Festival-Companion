package interface_adapter.display_event;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class DisplayEventViewModel extends ViewModel<DisplayEventState> {
    public DisplayEventViewModel() {
        super("event details");
        setState(new DisplayEventState());
    }
}
