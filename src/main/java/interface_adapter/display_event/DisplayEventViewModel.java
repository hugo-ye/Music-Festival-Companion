package interface_adapter.display_event;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;

public class DisplayEventViewModel extends ViewModel<Object> {

    public DisplayEventViewModel(String viewName) {
        super(viewName);
    }

    @Override
    public void firePropertyChanged() {

    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {

    }
}
