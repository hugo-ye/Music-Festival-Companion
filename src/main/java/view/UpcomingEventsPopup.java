package view;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

// Application entry point: launches the Swing frame and shows a mock reminder popup. //
public class UpcomingEventsPopup extends JDialog {

    // Minimal DTO for the reminder popup (event name + date), decoupled from the full Event model. //
    public static class ReminderItem {
        private final String name;      // event title, e.g. "Coldplay Toronto"
        private final LocalDate date;   // event date, e.g. 2025-11-15

        public ReminderItem(String name, LocalDate date) {
            this.name = name;
            this.date = date;
        }

        public String getName() { return name; }

        public LocalDate getDate() { return date; }
    }

    // Modal popup listing upcoming events and centered on the owner window. //
    public UpcomingEventsPopup(Frame owner, List<ReminderItem> items) {
        // true = modal dialog → user has to close it before going back to main window
        super(owner, "Upcoming events this week", true);

        // we want to place components in north/center/south, so BorderLayout is convenient
        setLayout(new BorderLayout());

        // this panel will hold every event label vertically
        JPanel listPanel = new JPanel();
        // BoxLayout with Y_AXIS = stack components from top to bottom
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        // if there is no upcoming event, show a friendly message
        if (items == null || items.isEmpty()) {
            listPanel.add(new JLabel("No upcoming events."));
        } else {
            // otherwise, create one JLabel per event: "Event name — 2025-11-15"
            for (ReminderItem item : items) {
                String text = item.getName() + " — " + item.getDate();
                listPanel.add(new JLabel(text));
            }
        }

        // in case the list gets long, we put it in a scroll pane
        JScrollPane scroll = new JScrollPane(listPanel);
        add(scroll, BorderLayout.CENTER);

        // bottom area with a single "OK" button to close the dialog
        JButton closeBtn = new JButton("OK");
        // when user clicks OK, we dispose the dialog (close and free resources)
        closeBtn.addActionListener(e -> dispose());
        JPanel bottom = new JPanel();   // simple flow layout is fine here
        bottom.add(closeBtn);
        add(bottom, BorderLayout.SOUTH);

        // set a reasonable default size; can be adjusted later
        setSize(300, 200);
        // center the dialog relative to the owner window
        setLocationRelativeTo(owner);
    }

    // Show the reminder dialog only if there is at least one item. //
    public static void showIfAny(JFrame owner, List<ReminderItem> items) {
        if (items == null || items.isEmpty()) return;
        UpcomingEventsPopup dlg = new UpcomingEventsPopup(owner, items);
        dlg.setVisible(true); // modal; returns after user closes
    }
}
