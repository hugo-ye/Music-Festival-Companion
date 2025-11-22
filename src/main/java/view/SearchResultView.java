package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import entity.Event;
import interface_adapter.ViewManagerModel;
import interface_adapter.display_event.DisplayEventController;
import interface_adapter.display_event.DisplayEventState;
import interface_adapter.display_search_results.DisplaySearchResultsState;
import interface_adapter.display_search_results.DisplaySearchResultsViewModel;
import interface_adapter.sort_events.SortEventsController;
import use_case.sort_events.SortEventsCriteria;
import use_case.sort_events.SortEventsOrder;

public class SearchResultView extends JPanel implements PropertyChangeListener {
    public final String viewName = "search results";

    // Models and Controllers
    private final DisplaySearchResultsViewModel displaySearchResultsViewModel;
    private final SortEventsController sortEventsController;
    private final DisplayEventController displayEventController;
    private final ViewManagerModel viewManagerModel;

    // Swing components
    private final JComboBox<SortEventsCriteria> sortCriteriaComboBox;
    private final JComboBox<SortEventsOrder> sortOrderComboBox;
    private final JButton sortButton;
    private final JPanel eventsPanel;
    private final JButton backButton;

    public SearchResultView(DisplaySearchResultsViewModel displaySearchResultsViewModel, SortEventsController sortEventsController, DisplayEventController displayEventController, ViewManagerModel viewManagerModel) {
        this.displaySearchResultsViewModel = displaySearchResultsViewModel;
        this.sortEventsController = sortEventsController;
        this.displayEventController = displayEventController;
        this.viewManagerModel = viewManagerModel;

        this.displaySearchResultsViewModel.addPropertyChangeListener(this);
        this.setLayout(new BorderLayout());

        // Top panel for sorting
        JPanel topPanel = new JPanel();
        sortCriteriaComboBox = new JComboBox<>(SortEventsCriteria.values());
        sortOrderComboBox = new JComboBox<>(SortEventsOrder.values());
        sortButton = new JButton("Sort");

        sortButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (evt.getSource() == sortButton) {
                    DisplaySearchResultsState currentState = displaySearchResultsViewModel.getState();
                    List<Event> currentEvents = currentState.getEvents();
                    SortEventsCriteria selectedCriteria = (SortEventsCriteria) sortCriteriaComboBox.getSelectedItem();
                    SortEventsOrder selectedOrder = (SortEventsOrder) sortOrderComboBox.getSelectedItem();

                    if (currentEvents != null) {
                        SearchResultView.this.sortEventsController.execute(currentEvents, selectedCriteria, selectedOrder);
                    }
                }
            }
        });

        topPanel.add(new JLabel("Sort by"));
        topPanel.add(sortCriteriaComboBox);
        topPanel.add(sortOrderComboBox);
        topPanel.add(sortButton);
        add(topPanel, BorderLayout.NORTH);

        // Event lists panel
        eventsPanel = new JPanel();
        eventsPanel.setLayout(new GridBagLayout());

        JScrollPane scrollPane = new JScrollPane(eventsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);

        // Bottom panel for back button
        JPanel bottomPanel = new JPanel();
        backButton = new JButton("Back");
        backButton.addActionListener(evt -> {
            viewManagerModel.setState("search events");
            viewManagerModel.firePropertyChanged();
        });
        bottomPanel.add(backButton);
        add(backButton, BorderLayout.SOUTH);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("refresh")) {
            DisplaySearchResultsState state = (DisplaySearchResultsState) evt.getNewValue();
            List<Event> events = state.getEvents();

            eventsPanel.removeAll();

            if (events != null) {
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridwidth = GridBagConstraints.REMAINDER;
                gbc.weightx = 1;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.insets = new Insets(5, 10, 5, 10);

                for (Event event : events) {
                    JPanel eventRow = createEventRow(event);
                    eventsPanel.add(eventRow, gbc);
                }

                GridBagConstraints fillerGbc = new GridBagConstraints();
                fillerGbc.weighty = 1;
                eventsPanel.add(Box.createGlue(), fillerGbc);
            }
            eventsPanel.revalidate();
            eventsPanel.repaint();
        }
    }

    private JPanel createEventRow(Event event) {
        DisplayEventState state = new DisplayEventState();

        state.setEventName(event.getName());
        state.setDate(String.valueOf(event.getDate()));
        state.setVenue(String.valueOf(event.getVenue()));
        state.setArtists(String.valueOf(event.getArtists()));

        Integer min = event.getPriceMin();
        Integer max = event.getPriceMax();
        String priceString;

        if (min == -1 && max == -1) {
            priceString = "Price unavailable";
        } else {
            priceString = String.format("Min: %d, Max: %d", min, max);
        }

        state.setPrice(priceString);

        JPanel eventRow = new JPanel();
        eventRow.setLayout(new BorderLayout());
        eventRow.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                new EmptyBorder(10, 15, 10, 15)
        ));
        eventRow.setBackground(Color.WHITE);
        eventRow.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Text panel
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);

        // Event Name
        JLabel nameLabel = new JLabel(state.getEventName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Date
        JLabel dateLabel = new JLabel("Date: " + state.getDate());
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        dateLabel.setForeground(Color.DARK_GRAY);
        dateLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Venue
        JLabel venueLabel = new JLabel("Venue: " + state.getVenue());
        venueLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        venueLabel.setForeground(Color.DARK_GRAY);
        venueLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Price range
        JLabel priceLabel = new JLabel("Price: " + state.getPrice());
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        priceLabel.setForeground(new Color(0, 100, 0)); // green
        priceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Artist
        JLabel artistLabel = new JLabel("Artists: " + state.getArtists());
        artistLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        artistLabel.setForeground(Color.GRAY);
        artistLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        textPanel.add(nameLabel);
        textPanel.add(Box.createVerticalStrut(5));
        textPanel.add(dateLabel);
        textPanel.add(Box.createVerticalStrut(2));
        textPanel.add(venueLabel);
        textPanel.add(Box.createVerticalStrut(2));
        textPanel.add(priceLabel);
        textPanel.add(Box.createVerticalStrut(5));
        textPanel.add(artistLabel);

        eventRow.add(textPanel, BorderLayout.CENTER);

        eventRow.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                displayEventController.execute(event);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                eventRow.setBackground(new Color(245, 245, 245));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                eventRow.setBackground(Color.WHITE);
            }
        });

        eventRow.setPreferredSize(new Dimension(400, 140));
        eventRow.setMaximumSize(new Dimension(500, 140));

        return eventRow;
    }
}