package view;

import entity.Event;
import interface_adapter.ViewManagerModel;
import interface_adapter.display_event.DisplayEventController;
import interface_adapter.display_event_list.DisplayEventListController;
import interface_adapter.display_event_list.DisplayEventListState;
import interface_adapter.display_event_list.DisplayEventListViewModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class EventListView extends JPanel implements PropertyChangeListener {
    private final String viewName = "event list";

    private final DisplayEventListViewModel displayEventListViewModel;
    private final DisplayEventListController displayEventListController;
    private final DisplayEventController displayEventController;
    private final ViewManagerModel viewManagerModel;

    private final JPanel eventsPanel;
    private final JButton backButton;
    private final JLabel titleLabel; // Added title label

    public EventListView(DisplayEventListViewModel displayEventViewModel,
                         DisplayEventListController displayEventListController,
                         DisplayEventController displayEventController,
                         ViewManagerModel viewManagerModel) {

        this.displayEventListViewModel = displayEventViewModel;
        this.displayEventListViewModel.addPropertyChangeListener(this);

        this.displayEventListController = displayEventListController;
        this.displayEventController = displayEventController;
        this.viewManagerModel = viewManagerModel;

        this.setLayout(new BorderLayout());
        this.setBackground(ViewStyle.WINDOW_BACKGROUND); // Ensure background matches

        // --- Top Panel ---
        JPanel topPanel = ViewStyle.createSectionPanel(new BorderLayout());

        // Add a title so we know which view we are on
        titleLabel = new JLabel("Event List");
        ViewStyle.applyTitleStyle(titleLabel);
        topPanel.add(titleLabel, BorderLayout.WEST);

        add(topPanel, BorderLayout.NORTH);

        // --- Center Panel (Scrollable Events) ---
        eventsPanel = new JPanel();
        eventsPanel.setLayout(new BoxLayout(eventsPanel, BoxLayout.Y_AXIS));
        eventsPanel.setBackground(ViewStyle.WINDOW_BACKGROUND);
        eventsPanel.setBorder(new EmptyBorder(10, 20, 10, 20)); // Padding

        JScrollPane scrollPane = new JScrollPane(eventsPanel);
        ViewStyle.applyScrollPaneStyle(scrollPane); // Apply style if you have it
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        // --- Bottom Panel ---
        JPanel bottomPanel = ViewStyle.createSectionPanel(new FlowLayout(FlowLayout.RIGHT));
        backButton = new JButton("Back");
        ViewStyle.applyButtonStyle(backButton);

        backButton.addActionListener(e -> {
            if (viewManagerModel != null) {
                viewManagerModel.setState("event lists"); // Make sure this matches AllEventListsView viewName
                viewManagerModel.firePropertyChanged();
            }
        });
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("refresh")) {
            DisplayEventListState state = (DisplayEventListState) evt.getNewValue();

            // Update title if the list has a name
            if (state.getEventList() != null) {
                titleLabel.setText(state.getEventList().getName());

                eventsPanel.removeAll();

                if (state.getEventList().getEvents().isEmpty()) {
                    JLabel noEvents = new JLabel("No events in this list.");
                    ViewStyle.applyLabelStyle(noEvents);
                    eventsPanel.add(noEvents);
                } else {
                    for (Event event : state.getEventList().getEvents()) {
                        JPanel eventRow = createEventRow(event);
                        eventsPanel.add(eventRow);
                        eventsPanel.add(Box.createVerticalStrut(10));
                    }
                }

                eventsPanel.revalidate();
                eventsPanel.repaint();
            }
        }
    }

    private JPanel createEventRow(Event event) {
        // 1. Create the card
        JPanel eventRow = ViewStyle.createCardPanel();
        eventRow.setLayout(new BorderLayout());
        eventRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80)); // Prevent it from stretching infinitely
        eventRow.setBorder(new EmptyBorder(10, 10, 10, 10));

        // 2. Add Content (This was missing!)
        JLabel nameLabel = new JLabel(event.getName());
        ViewStyle.applyHeaderStyle(nameLabel); // Use a visible font style
        eventRow.add(nameLabel, BorderLayout.CENTER);

        JLabel dateLabel = new JLabel(event.getDate() != null ? event.getDate().toString() : "No Date");
        ViewStyle.applyLabelStyle(dateLabel);
        eventRow.add(dateLabel, BorderLayout.EAST);

        // 3. Add Interaction
        eventRow.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                displayEventController.execute(event);
            }

            // Optional: Visual feedback on hover
            @Override
            public void mouseEntered(MouseEvent e) {
                eventRow.setCursor(new Cursor(Cursor.HAND_CURSOR));
                // You could change background color slightly here if desired
            }
        });

        return eventRow;
    }

    public String getViewName() {
        return this.viewName;
    }
}