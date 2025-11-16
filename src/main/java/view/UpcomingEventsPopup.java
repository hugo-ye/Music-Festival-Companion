package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/* Pure Swing view that renders a list of lines; no business logic here. */
public class UpcomingEventsPopup extends JDialog {

    public UpcomingEventsPopup(Frame owner, List<String> lines) {
        super(owner, "Upcoming events this week", true);
        setLayout(new BorderLayout());

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        if (lines == null || lines.isEmpty()) {
            listPanel.add(new JLabel("No upcoming events."));
        } else {
            for (String line : lines) {
                listPanel.add(new JLabel(line));
            }
        }

        add(new JScrollPane(listPanel), BorderLayout.CENTER);

        JButton ok = new JButton("OK");
        ok.addActionListener(e -> dispose());
        JPanel bottom = new JPanel();
        bottom.add(ok);
        add(bottom, BorderLayout.SOUTH);

        setSize(320, 220);
        setLocationRelativeTo(owner);
    }

    /* Only show when there are items. */
    public static void showIfAny(JFrame owner, List<String> lines) {
        if (lines == null || lines.isEmpty()) return;
        new UpcomingEventsPopup(owner, lines).setVisible(true);
    }
}

