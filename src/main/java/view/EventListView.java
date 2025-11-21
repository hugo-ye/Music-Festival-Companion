package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class EventListView extends JPanel implements PropertyChangeListener {
    public final String viewName = "event list";
    // Add controllers and viewmodels here
    // Swing components
    private final JPanel eventsPanel;
    private final JButton backButton;

    public EventListView() {
        // add controller, viewmodel here
        this.setLayout(new BorderLayout());
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        add(topPanel, BorderLayout.NORTH);

        eventsPanel = new JPanel();
        eventsPanel.setLayout(new BoxLayout(eventsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(eventsPanel);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        backButton = new JButton("Back");
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
