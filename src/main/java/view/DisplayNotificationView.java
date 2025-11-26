package view;

import interface_adapter.display_notifications.DisplayNotificationsViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class DisplayNotificationView extends JPanel implements PropertyChangeListener {
    private final DisplayNotificationsViewModel viewModel;
    private final Component parentComponent;
    private final String viewName = "notifications";


    public DisplayNotificationView(DisplayNotificationsViewModel viewModel, Component parentComponent) {
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);
        this.parentComponent = parentComponent;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("message".equals(evt.getPropertyName())) {
            String message = (String) viewModel.getMessage();
            if (message != null && !message.trim().isEmpty() && !message.equals("recent events:\n")) {
                JOptionPane.showMessageDialog(parentComponent, message, "Upcoming Events", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    public String getViewName() {
        return viewName;
    }
}