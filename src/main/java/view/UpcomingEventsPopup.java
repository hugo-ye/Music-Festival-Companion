package view;

import interface_adapter.reminder.ReminderEntry;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/*
 Modal dialog that lists upcoming events the user plans to attend.
 Pure UI code: no data access / no business logic.
 */
public class UpcomingEventsPopup extends JDialog {

    /*
      Build the popup UI.
      @param owner parent window to center on
      @param items list of events to show (name + date); if null/empty, the dialog will not open via showIfAny(...)
     */
    public UpcomingEventsPopup(Frame owner, List<ReminderEntry> items) {
        super(owner, "Upcoming events this week", true); // modal dialog
        setLayout(new BorderLayout());

        // Vertical list container
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        if (items == null || items.isEmpty()) {
            listPanel.add(new JLabel("No upcoming events."));
        } else {
            for (ReminderEntry item : items) {
                String text = item.getName() + " — " + item.getDate();
                listPanel.add(new JLabel(text));
            }
        }

        // Scrollable list
        add(new JScrollPane(listPanel), BorderLayout.CENTER);

        // Bottom row with a single OK button
        JButton closeBtn = new JButton("OK");
        closeBtn.addActionListener(e -> dispose());
        JPanel bottom = new JPanel();
        bottom.add(closeBtn);
        add(bottom, BorderLayout.SOUTH);

        setSize(300, 200);
        setLocationRelativeTo(owner); // center on parent
    }

    /*
     Convenience: only show the dialog if there is at least one item.
     */
    public static void showIfAny(JFrame owner, List<ReminderEntry> items) {
        if (items == null || items.isEmpty()) return;
        UpcomingEventsPopup dlg = new UpcomingEventsPopup(owner, items);
        dlg.setVisible(true); // modal; returns after the user closes it
    }
}
