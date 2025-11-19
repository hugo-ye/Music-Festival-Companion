package view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import entity.Event;
import interface_adapter.display_event.DisplayEventController;
import interface_adapter.display_search_results.DisplaySearchResultsController;
import interface_adapter.display_search_results.DisplaySearchResultsState;
import interface_adapter.display_search_results.DisplaySearchResultsViewModel;
import interface_adapter.sort_events.SortEventsController;
import use_case.sort_events.SortEventsMethod;
import use_case.sort_events.SortEventsOrder;

public class SearchResultView extends JPanel implements PropertyChangeListener {

    public final String viewName = "search result";
    // Models and Controllers
    private final DisplaySearchResultsViewModel displaySearchResultsViewModel;
    private final DisplaySearchResultsController displaySearchResultsController;
    private final SortEventsController sortEventsController;
    private final DisplayEventController displayEventController;


    // Swing components
    private final JComboBox<SortEventsMethod> sortMethodComboBox;
    private final JComboBox<SortEventsOrder> sortOrderComboBox;
    private final JButton sortButton;
    private final JPanel eventsPanel;
    private final JButton backButton;

    public SearchResultView(DisplaySearchResultsViewModel displaySearchResultsViewModel, DisplaySearchResultsController displaySearchResultsController, SortEventsController sortEventsController, DisplayEventController displayEventController) {
        this.displaySearchResultsViewModel = displaySearchResultsViewModel;
        this.displaySearchResultsController = displaySearchResultsController;
        this.sortEventsController = sortEventsController;
        this.displayEventController = displayEventController;

        this.displaySearchResultsViewModel.addPropertyChangeListener(this);
        this.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        sortMethodComboBox = new JComboBox<>(SortEventsMethod.values());
        sortOrderComboBox = new JComboBox<>(SortEventsOrder.values());
        sortButton = new JButton("Sort");

        sortButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (evt.getSource() == sortButton) {
                    DisplaySearchResultsState currentState = displaySearchResultsViewModel.getState();
                    List<Event> currentEvents = currentState.getEvents();
                    SortEventsMethod selectedMethod = (SortEventsMethod) sortMethodComboBox.getSelectedItem();
                    SortEventsOrder selectedOrder = (SortEventsOrder) sortOrderComboBox.getSelectedItem();

                    if (currentEvents != null) {
                        SearchResultView.this.sortEventsController.execute(currentEvents, selectedMethod, selectedOrder);
                    }
                }
            }
        });

        topPanel.add(new JLabel("Sort by"));
        topPanel.add(sortMethodComboBox);
        topPanel.add(sortOrderComboBox);
        topPanel.add(sortButton);
        add(topPanel, BorderLayout.NORTH);

        eventsPanel = new JPanel();
        eventsPanel.setLayout(new BoxLayout(eventsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(eventsPanel);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {

            }
        });
        bottomPanel.add(backButton);
        add(backButton, BorderLayout.SOUTH);

}

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            DisplaySearchResultsState state = (DisplaySearchResultsState) evt.getNewValue();
            List<Event> events = state.getEvents();
            eventsPanel.removeAll();

            if (events != null) {
                for (Event event : events) {
                    JPanel eventRow = new JPanel();
                    eventRow.setLayout( new FlowLayout(FlowLayout.LEFT));
                    JLabel nameLabel = new JLabel(event.getName());
                    eventRow.add(nameLabel);
                    JLabel artistLabel = new JLabel(event.getArtists().toString());
                    eventRow.add(artistLabel);
                    JButton viewDetailsButton = new JButton("Details");
                    eventRow.add(viewDetailsButton);
                    eventsPanel.add(eventRow);
                }
            }
            eventsPanel.revalidate();
            eventsPanel.repaint();
        }
    }


}
