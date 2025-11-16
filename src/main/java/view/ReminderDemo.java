package view;

import interface_adapter.reminder.ReminderEntry;

import javax.swing.*;
import java.time.LocalDate;
import java.util.List;

/*
  Local demo to run the popup UI without touching app/Main or ViewManager.
  Create a host frame + mock data, then show the popup.
 */
public final class ReminderDemo {
    private ReminderDemo() {}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
            catch (Exception ignored) {}

            JFrame host = new JFrame("Reminder Demo Host");
            host.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            host.setSize(800, 500);
            host.setLocationRelativeTo(null);
            host.setVisible(true);

            // Mock data until data_access/use_case layers are ready
            List<ReminderEntry> mock = List.of(
                    new ReminderEntry("Coldplay Toronto", LocalDate.now().plusDays(2)),
                    new ReminderEntry("Jazz Night", LocalDate.now().plusDays(5))
            );

            UpcomingEventsPopup.showIfAny(host, mock);
        });
    }
}
