package interface_adapter.display_event;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class DisplayEventViewModel extends ViewModel<DisplayEventState> {
    public DisplayEventViewModel(String viewName) {
        super(viewName);
        setState(new DisplayEventState());
    }

    // private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    // private DisplayEventState state = new DisplayEventState();

    /*
    public DisplayEventState getState(){
        return state;
    }
    public void setState(DisplayEventState newState){
        DisplayEventState oldState = this.state;
        this.state = newState;
        support.firePropertyChange("state", oldState, newState);
    }

    @Override
    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
    *
     */
}
