package interface_adapter.display_notifications;

import interface_adapter.ViewModel;

public class DisplayNotificationsViewModel extends ViewModel<DisplayNotificationsState> {

    public DisplayNotificationsViewModel() {
        super("notifications");
        setState(new DisplayNotificationsState());
    }
}
