package view;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import entity.Event;
import interface_adapter.ViewManagerModel;
import interface_adapter.display_event.DisplayEventController;
import interface_adapter.display_search_results.DisplaySearchResultsState;
import interface_adapter.display_search_results.DisplaySearchResultsViewModel;
import interface_adapter.sort_events.SortEventsController;
import use_case.sort_events.SortEventsCriteria;
import use_case.sort_events.SortEventsOrder;

/**
 * The View for the Display Search Results Use Case.
 */
public class SearchResultView extends JPanel implements PropertyChangeListener {
    private final String viewName = "search results";

    private final DisplaySearchResultsViewModel displaySearchResultsViewModel;
    private SortEventsController sortEventsController;
    private DisplayEventController displayEventController;
    private final ViewManagerModel viewManagerModel;

    private final JComboBox<SortEventsCriteria> sortCriteriaComboBox;
    private final JComboBox<SortEventsOrder> sortOrderComboBox;
    private final JButton sortButton;
    private final JPanel eventsPanel;
    private final JButton backButton;

    public SearchResultView(DisplaySearchResultsViewModel displaySearchResultsViewModel,
                            ViewManagerModel viewManagerModel) {

        this.displaySearchResultsViewModel = displaySearchResultsViewModel;
        this.viewManagerModel = viewManagerModel;
        this.displaySearchResultsViewModel.addPropertyChangeListener(this);

        this.setLayout(new BorderLayout());
        this.setBackground(ViewStyle.WINDOW_BACKGROUND);

        // Top panel
        final JPanel topPanel = ViewStyle.createSectionPanel(new FlowLayout(FlowLayout.LEFT));
        final JLabel sortLabel = new JLabel("Sort by:");
        ViewStyle.applySecondaryLabelStyle(sortLabel);

        sortCriteriaComboBox = new JComboBox<>(SortEventsCriteria.values());
        sortOrderComboBox = new JComboBox<>(SortEventsOrder.values());
        sortButton = new JButton("Sort");

        ViewStyle.applyComboBoxStyle(sortCriteriaComboBox);
        ViewStyle.applyComboBoxStyle(sortOrderComboBox);
        ViewStyle.applyButtonStyle(sortButton);

        sortButton.addActionListener(evt -> {
            final DisplaySearchResultsState currentState = displaySearchResultsViewModel.getState();
            final List<Event> currentEvents = currentState.getEvents();
            final SortEventsCriteria selectedCriteria = (SortEventsCriteria) sortCriteriaComboBox.getSelectedItem();
            final SortEventsOrder selectedOrder = (SortEventsOrder) sortOrderComboBox.getSelectedItem();
            if (currentEvents != null) {
                sortEventsController.execute(currentEvents, selectedCriteria, selectedOrder);
            }
        });

        topPanel.add(sortLabel);
        topPanel.add(sortCriteriaComboBox);
        topPanel.add(sortOrderComboBox);
        topPanel.add(sortButton);
        add(topPanel, BorderLayout.NORTH);

        // Event list panel
        eventsPanel = new JPanel();
        eventsPanel.setLayout(new GridBagLayout());
        eventsPanel.setBackground(ViewStyle.WINDOW_BACKGROUND);

        final JScrollPane scrollPane = new JScrollPane(eventsPanel);
        ViewStyle.applyScrollPaneStyle(scrollPane);
        add(scrollPane, BorderLayout.CENTER);

        // Bottom panel
        final JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.setBackground(ViewStyle.WINDOW_BACKGROUND);
        bottomPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, ViewStyle.BORDER_SUBTLE),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        backButton = new JButton("Back");
        ViewStyle.applyButtonStyle(backButton);
        backButton.addActionListener(evt -> {
            viewManagerModel.setState("search event");
            viewManagerModel.firePropertyChanged();
        });
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void setSortEventsController(SortEventsController sortEventsController) {
        this.sortEventsController = sortEventsController;
    }

    public void setDisplayEventController(DisplayEventController displayEventController) {
        this.displayEventController = displayEventController;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("refresh".equals(evt.getPropertyName())) {
            final DisplaySearchResultsState state = (DisplaySearchResultsState) evt.getNewValue();
            final List<Event> events = state.getEvents();

            eventsPanel.removeAll();
            if (events != null) {
                final GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridwidth = GridBagConstraints.REMAINDER;
                gbc.weightx = 1;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.insets = new Insets(5, 10, 5, 10);

                for (Event event : events) {
                    final JPanel eventRow = createEventRow(event);
                    eventsPanel.add(eventRow, gbc);
                }

                final GridBagConstraints fillerGbc = new GridBagConstraints();
                fillerGbc.weighty = 1;
                eventsPanel.add(Box.createGlue(), fillerGbc);
            }
            eventsPanel.revalidate();
            eventsPanel.repaint();
        }
    }

    /**
     * A method to create a row of event card.
     * @param event the event to be added.
     * @return the created row of event card.
     */
    private JPanel createEventRow(Event event) {
        final String priceString = (event.getPriceMin() == -1)
                ? "Price unavailable"
                : String.format("Min: %d, Max: %d", event.getPriceMin(), event.getPriceMax());

        final JPanel eventRow = ViewStyle.createCardPanel();
        eventRow.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        final JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);

        // Event Name
        final JLabel nameLabel = new JLabel(event.getName());
        ViewStyle.applyHeaderStyle(nameLabel);

        // Date
        final JLabel dateLabel = new JLabel("Date: " + (event.getDate() == null ? "TBD" : event.getDate()));
        ViewStyle.applySecondaryLabelStyle(dateLabel);

        // Venue
        final JLabel venueLabel = new JLabel("Venue: " + event.getVenue());
        ViewStyle.applySecondaryLabelStyle(venueLabel);

        // Price (Green Text)
        final JLabel priceLabel = new JLabel("Price: " + priceString);
        priceLabel.setFont(ViewStyle.BODY_FONT_BOLD);
        priceLabel.setForeground(ViewStyle.TEXT_PRICE);

        // Artists (Meta Text)
        final JLabel artistLabel = new JLabel("Artists: " + event.getArtists());
        ViewStyle.applyMetaLabelStyle(artistLabel);

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
                eventRow.setBackground(ViewStyle.HEADER_BACKGROUND);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                eventRow.setBackground(ViewStyle.CARD_BACKGROUND);
            }
        });

        eventRow.setPreferredSize(new Dimension(400, 150));
        eventRow.setMaximumSize(new Dimension(500, 150));
        return eventRow;
    }

    /**
     * Get the name of this view.
     * @return the name of this view.
     */
    public String getViewName() {
        return viewName;
    }
}
