// file: src/main/java/view/ReminderDemo.java
package view;

import javax.swing.*;
import java.time.LocalDate;
import java.util.List;

// Local demo to run the popup UI without touching app/Main or ViewManager. //
public final class ReminderDemo {
    private ReminderDemo() {}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}

            JFrame host = new JFrame("Reminder Demo Host");
            host.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            host.setSize(800, 500);
            host.setLocationRelativeTo(null);
            host.setVisible(true);

            List<UpcomingEventsPopup.ReminderItem> mock = List.of(
                    new UpcomingEventsPopup.ReminderItem("Coldplay Toronto", LocalDate.now().plusDays(2)),
                    new UpcomingEventsPopup.ReminderItem("Jazz Night", LocalDate.now().plusDays(5))
            );

            UpcomingEventsPopup.showIfAny(host, mock);
        });
    }
}
