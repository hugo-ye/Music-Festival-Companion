package interface_adapter.display_notifications;

import interface_adapter.ViewModel;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class DisplayNotificationsViewModel extends ViewModel<String> {

    private String message = "";

    public DisplayNotificationsViewModel() {
        super("notifications");
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void firePropertyChanged() {
        super.firePropertyChanged("message");
    }

    public void firePropertyChanged(String propertyName) {
        super.firePropertyChanged(propertyName);
    }
}