package view;

import entity.Event;
import entity.EventList;
import interface_adapter.ViewManagerModel;
import interface_adapter.display_event.DisplayEventController;
import interface_adapter.display_event_list.DisplayEventListController;
import interface_adapter.display_event_list.DisplayEventListState;
import interface_adapter.display_event_list.DisplayEventListViewModel;
import interface_adapter.remove_event_from_list.RemoveEventFromListController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class EventListView extends JPanel implements PropertyChangeListener {
    private final String viewName = "event list";

    private final DisplayEventListViewModel displayEventListViewModel;
    private final DisplayEventListController displayEventListController;
    private final DisplayEventController displayEventController;
    private final ViewManagerModel viewManagerModel;
    private final RemoveEventFromListController removeEventFromListController;

    // Swing Components
    private final JPanel eventsPanel;
    private final JButton backButton;
    private final JLabel titleLabel;

    private EventList currentEventList;

    public EventListView(DisplayEventListViewModel displayEventViewModel,
                         DisplayEventListController displayEventListController,
                         DisplayEventController displayEventController,
                         ViewManagerModel viewManagerModel,
                         RemoveEventFromListController removeEventFromListController) {

        this.displayEventListViewModel = displayEventViewModel;
        this.displayEventListController = displayEventListController;
        this.displayEventController = displayEventController;
        this.viewManagerModel = viewManagerModel;
        this.removeEventFromListController = removeEventFromListController;

        this.displayEventListViewModel.addPropertyChangeListener(this);

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
        // Match padding from AllEventListsView
        eventsPanel.setBorder(new EmptyBorder(0, 20, 0, 20));

        JScrollPane scrollPane = new JScrollPane(eventsPanel);
        ViewStyle.applyScrollPaneStyle(scrollPane);
        add(scrollPane, BorderLayout.CENTER);

        // --- Bottom Panel ---
        JPanel bottomPanel = ViewStyle.createSectionPanel(new FlowLayout(FlowLayout.RIGHT));
        backButton = new JButton("Back to Lists");
        ViewStyle.applyButtonStyle(backButton);

        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // --- Listeners ---
        backButton.addActionListener(e -> {
            if (viewManagerModel != null) {
                viewManagerModel.setState("event lists");
                viewManagerModel.firePropertyChanged();
            }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("refresh") || evt.getPropertyName().equals("state")) {

            DisplayEventListState state;
            if (evt.getNewValue() instanceof DisplayEventListState) {
                state = (DisplayEventListState) evt.getNewValue();
            } else {
                state = displayEventListViewModel.getState();
            }

            this.currentEventList = state.getEventList();

            if (this.currentEventList != null) {
                titleLabel.setText(this.currentEventList.getName());
                rebuildEventRows();
            }
        }
    }

    private void rebuildEventRows() {
        eventsPanel.removeAll();

        // Add top spacing (Consistent with AllEventListsView)
        eventsPanel.add(Box.createVerticalStrut(10));

        if (this.currentEventList.getEvents().isEmpty()) {
            JLabel noEvents = new JLabel("No events in this list.");
            ViewStyle.applyLabelStyle(noEvents);
            noEvents.setAlignmentX(Component.LEFT_ALIGNMENT);
            // Add a small container for the label so it aligns nicely
            JPanel msgPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            msgPanel.setOpaque(false);
            msgPanel.add(noEvents);
            eventsPanel.add(msgPanel);
        } else {
            for (Event event : this.currentEventList.getEvents()) {
                JPanel eventRow = createEventCard(event);
                eventsPanel.add(eventRow);
                // Match spacing from AllEventListsView
                eventsPanel.add(Box.createVerticalStrut(15));
            }
        }

        eventsPanel.add(Box.createVerticalGlue());
        eventsPanel.revalidate();
        eventsPanel.repaint();
    }

    private JPanel createEventCard(Event event) {
        JPanel card = ViewStyle.createCardPanel();
        card.setLayout(new BorderLayout());
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        // Left: Event Name and Date
        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.setOpaque(false);

        JLabel nameLabel = new JLabel(event.getName());
        ViewStyle.applyHeaderStyle(nameLabel);

        JLabel dateLabel = new JLabel(event.getDate() != null ? event.getDate().toString() : "No Date");
        ViewStyle.applyMetaLabelStyle(dateLabel);

        infoPanel.add(nameLabel);
        infoPanel.add(dateLabel);

        card.add(infoPanel, BorderLayout.WEST);

        // Right: Buttons (View and Remove)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);

        // 1. View Button (Consistent with AllEventListsView)
        JButton viewButton = new JButton("View");
        ViewStyle.applyButtonStyle(viewButton);
        viewButton.addActionListener(e -> displayEventController.execute(event));
        buttonPanel.add(viewButton);

        // 2. Remove Button
        JButton removeButton = new JButton("Remove");
        ViewStyle.applyButtonStyle(removeButton);
        removeButton.setForeground(ViewStyle.ERROR_COLOR); // Red text for destructive action

        removeButton.addActionListener(e -> {
            if (currentEventList != null && removeEventFromListController != null) {
                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "Remove '" + event.getName() + "' from this list?",
                        "Remove Event",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    removeEventFromListController.removeEventFromList(event, currentEventList);
                }
            }
        });
        buttonPanel.add(removeButton);

        card.add(buttonPanel, BorderLayout.EAST);

        return card;
    }

    public String getViewName() {
        return this.viewName;
    }
}