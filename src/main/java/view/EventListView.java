package view;

import entity.Event;
import interface_adapter.ViewManagerModel;
import interface_adapter.display_event.DisplayEventController; // 1. Ensure this is imported
import interface_adapter.display_event_list.DisplayEventListController;
import interface_adapter.display_event_list.DisplayEventListState;
import interface_adapter.display_event_list.DisplayEventListViewModel;

import javax.swing.*;
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

    public EventListView(DisplayEventListViewModel displayEventViewModel,
                         DisplayEventListController displayEventListController,
                         DisplayEventController displayEventController, // 3. Inject it here
                         ViewManagerModel viewManagerModel) {

        this.displayEventListViewModel = displayEventViewModel;
        this.displayEventListViewModel.addPropertyChangeListener(this);

        this.displayEventListController = displayEventListController;
        this.displayEventController = displayEventController; // 4. Assign it
        this.viewManagerModel = viewManagerModel;

        this.setLayout(new BorderLayout());
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        add(topPanel, BorderLayout.NORTH);

        eventsPanel = new JPanel();
        eventsPanel.setLayout(new BoxLayout(eventsPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(eventsPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        backButton = new JButton("Back");

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
                eventsPanel.removeAll();
                for (Event event : state.getEventList().getEvents()) {
                    JPanel eventRow = createEventRow(event);
                    eventsPanel.add(eventRow);
                    eventsPanel.add(Box.createVerticalStrut(10));
                }
                eventsPanel.revalidate();
                eventsPanel.repaint();
            }
        }
    }

    private JPanel createEventRow(Event event) {
        JPanel eventRow = ViewStyle.createCardPanel();
        eventRow.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                displayEventController.execute(event);
            }
        });

        return eventRow;
    }

    public String getViewName() {
        return this.viewName;
    }
}