package view;

import interface_adapter.display_notifications.DisplayNotificationsController;
import interface_adapter.display_notifications.DisplayNotificationsViewModel;

import javax.swing.*;
import java.time.LocalDate;

public class DisplayNotificationView extends JPanel {
    private final DisplayNotificationsViewModel viewModel;
    private final DisplayNotificationsController controller;


    public DisplayNotificationView(DisplayNotificationsViewModel viewModel, DisplayNotificationsController controller) {
        this.viewModel = viewModel;
        this.controller = controller;
    }

    public void createUI(){
        JFrame frame = new JFrame("Notifications");
        LocalDate currDate = LocalDate.now();
        controller.execute(currDate);
        JOptionPane.showMessageDialog(frame, viewModel.getMessage());
    }
}
