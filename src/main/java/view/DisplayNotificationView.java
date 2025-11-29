package view;

import java.awt.Frame;
import java.awt.Window;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import interface_adapter.display_notifications.DisplayNotificationsViewModel;

/**
 * The View for the Display Notifications Use Case.
 */
public class DisplayNotificationView extends JPanel implements PropertyChangeListener {
    private final DisplayNotificationsViewModel viewModel;
    private final String viewName = "notifications";

    public DisplayNotificationView(DisplayNotificationsViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);
        this.setOpaque(false);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("message".equals(evt.getPropertyName())) {
            final String message = viewModel.getState().getMessage();
            viewModel.getState().setMessage("");

            if (message != null && !message.trim().isEmpty()) {
                showNotification(message);
            }
        }
    }

    private void showNotification(String message) {
        SwingUtilities.invokeLater(() -> {
            Window owner = SwingUtilities.getWindowAncestor(this);

            if (owner == null) {
                final Frame[] frames = Frame.getFrames();
                for (Frame f : frames) {
                    if (f.isVisible() && f.isActive()) {
                        owner = f;
                        break;
                    }
                }
                if (owner == null && frames.length > 0) {
                    owner = frames[0];
                }
            }

            final NotificationDialog dialog = new NotificationDialog(owner, message);
            dialog.setVisible(true);
        });
    }

    public String getViewName() {
        return viewName;
    }
}