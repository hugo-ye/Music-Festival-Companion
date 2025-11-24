package view;

import entity.Event;
import entity.EventList;
import interface_adapter.ViewManagerModel;
import interface_adapter.display_event.DisplayEventController;
import interface_adapter.display_event_list.DisplayEventListController;
import interface_adapter.display_event_list.DisplayEventListState;
import interface_adapter.display_event_list.DisplayEventListViewModel;
import interface_adapter.remove_event_from_list.RemoveEventFromListController; // NEW IMPORT

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Objects;

public class EventListView extends JPanel implements PropertyChangeListener {
    private final String viewName = "event list";

    private final DisplayEventListViewModel displayEventListViewModel;
    private final DisplayEventListController displayEventListController;
    private final DisplayEventController displayEventController;
    private final ViewManagerModel viewManagerModel;
    private final RemoveEventFromListController removeEventFromListController; // NEW FIELD

    private final JPanel eventsPanel;
    private final JButton backButton;
    private final JLabel titleLabel;

    public EventListView(DisplayEventListViewModel displayEventViewModel,
                         DisplayEventListController displayEventListController,
                         DisplayEventController displayEventController,
                         ViewManagerModel viewManagerModel,
                         RemoveEventFromListController removeEventFromListController) { // NEW PARAMETER

        this.displayEventListViewModel = displayEventViewModel;
        this.displayEventListViewModel.addPropertyChangeListener(this);

        this.displayEventListController = displayEventListController;
        this.displayEventController = displayEventController;
        this.viewManagerModel = viewManagerModel;
        this.removeEventFromListController = removeEventFromListController; // ASSIGNMENT

        this.setLayout(new BorderLayout());
        this.setBackground(ViewStyle.WINDOW_BACKGROUND);

        // --- Top Panel ---
        JPanel topPanel = ViewStyle.createSectionPanel(new BorderLayout());
        titleLabel = new JLabel("Event List");
        ViewStyle.applyTitleStyle(titleLabel);
        topPanel.add(titleLabel, BorderLayout.WEST);
        add(topPanel, BorderLayout.NORTH);

        // --- Center Panel (Scrollable Events) ---
        eventsPanel = new JPanel();
        eventsPanel.setLayout(new BoxLayout(eventsPanel, BoxLayout.Y_AXIS));
        eventsPanel.setBackground(ViewStyle.WINDOW_BACKGROUND);
        eventsPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        JScrollPane scrollPane = new JScrollPane(eventsPanel);
        ViewStyle.applyScrollPaneStyle(scrollPane);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        // --- Bottom Panel ---
        JPanel bottomPanel = ViewStyle.createSectionPanel(new FlowLayout(FlowLayout.RIGHT));
        backButton = new JButton("Back");
        ViewStyle.applyButtonStyle(backButton);

        backButton.addActionListener(e -> {
            if (viewManagerModel != null) {
                viewManagerModel.setState("event lists");
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

            if (state.getEventList() != null) {
                // Update title
                titleLabel.setText(state.getEventList().getName());

                eventsPanel.removeAll();

                EventList currentList = state.getEventList();

                if (currentList.getEvents().isEmpty()) {
                    JLabel noEvents = new JLabel("No events in this list.");
                    ViewStyle.applyLabelStyle(noEvents);
                    eventsPanel.add(noEvents);
                } else {
                    for (Event event : currentList.getEvents()) {
                        // Pass the current list context to the row creation method
                        JPanel eventRow = createEventRow(event, currentList);
                        eventsPanel.add(eventRow);
                        eventsPanel.add(Box.createVerticalStrut(10));
                    }
                }

                eventsPanel.revalidate();
                eventsPanel.repaint();
            }
        }
    }

    /**
     * Creates a card panel for a single event, including name, date, and buttons.
     * @param event The Event entity to display.
     * @param listContext The EventList entity this event belongs to (for deletion context).
     * @return A JPanel representing the event row.
     */
    private JPanel createEventRow(Event event, EventList listContext) {
        // 1. Main Card Panel
        JPanel eventRow = ViewStyle.createCardPanel();
        eventRow.setLayout(new BorderLayout());
        eventRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        eventRow.setBorder(new EmptyBorder(10, 10, 10, 10));
        eventRow.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // 2. Left Panel (Name and Date)
        JPanel textInfoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        textInfoPanel.setOpaque(false);

        JLabel nameLabel = new JLabel(event.getName());
        ViewStyle.applyHeaderStyle(nameLabel);

        JLabel dateLabel = new JLabel("Date: " + (event.getDate() != null ? event.getDate().toString() : "TBD"));
        ViewStyle.applyLabelStyle(dateLabel);

        textInfoPanel.add(nameLabel);
        textInfoPanel.add(dateLabel);

        eventRow.add(textInfoPanel, BorderLayout.WEST);

        // 3. Right Panel (Buttons)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);

        // --- View Details Button ---
        JButton detailsButton = new JButton("Details");
        ViewStyle.applyButtonStyle(detailsButton);

        detailsButton.addActionListener(e -> {
            displayEventController.execute(event); // View Event Details Use Case
        });
        buttonPanel.add(detailsButton);


        // --- DELETE Button ---
        JButton deleteButton = new JButton("Delete");
        ViewStyle.applyButtonStyle(deleteButton);
        deleteButton.setForeground(ViewStyle.ERROR_COLOR);

        // Disable delete if the list is the Master List (Master List ID is hardcoded to "master_list")
        if (Objects.equals(listContext.getId(), "master_list")) {
            deleteButton.setEnabled(false);
            deleteButton.setText("Attended"); // Optional visual change
        } else {
            deleteButton.addActionListener(e -> {
                int confirm = JOptionPane.showConfirmDialog(
                        eventRow, // Parent component for centering dialog
                        "Remove '" + event.getName() + "' from " + listContext.getName() + "?",
                        "Confirm Removal",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION && removeEventFromListController != null) {
                    // Trigger the remove event Use Case
                    removeEventFromListController.removeEventFromList(event, listContext);
                    // The presenter for this Use Case should handle refreshing the EventListView afterward.
                }
            });
        }
        buttonPanel.add(deleteButton);

        eventRow.add(buttonPanel, BorderLayout.EAST);

        // Add click listener to the whole card for quick details view
        eventRow.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // If the click wasn't on a button, show details
                if (e.getSource().equals(eventRow)) {
                    displayEventController.execute(event);
                }
            }
        });


        return eventRow;
    }

    public String getViewName() {
        return this.viewName;
    }
}