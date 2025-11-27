package view;

import entity.Event;
import interface_adapter.ViewManagerModel;
import interface_adapter.display_event.DisplayEventController;
import interface_adapter.display_search_results.DisplaySearchResultsState;
import interface_adapter.display_search_results.DisplaySearchResultsViewModel;
import interface_adapter.sort_events.SortEventsController;
import use_case.sort_events.SortEventsCriteria;
import use_case.sort_events.SortEventsOrder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class SearchResultView extends JPanel implements PropertyChangeListener {
    public final String viewName = "search results";

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
        JPanel topPanel = ViewStyle.createSectionPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel sortLabel = new JLabel("Sort by:");
        ViewStyle.applySecondaryLabelStyle(sortLabel);

        sortCriteriaComboBox = new JComboBox<>(SortEventsCriteria.values());
        sortOrderComboBox = new JComboBox<>(SortEventsOrder.values());
        sortButton = new JButton("Sort");

        ViewStyle.applyComboBoxStyle(sortCriteriaComboBox);
        ViewStyle.applyComboBoxStyle(sortOrderComboBox);
        ViewStyle.applyButtonStyle(sortButton);

        sortButton.addActionListener(evt -> {
            DisplaySearchResultsState currentState = displaySearchResultsViewModel.getState();
            List<Event> currentEvents = currentState.getEvents();
            SortEventsCriteria selectedCriteria = (SortEventsCriteria) sortCriteriaComboBox.getSelectedItem();
            SortEventsOrder selectedOrder = (SortEventsOrder) sortOrderComboBox.getSelectedItem();
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

        JScrollPane scrollPane = new JScrollPane(eventsPanel);
        ViewStyle.applyScrollPaneStyle(scrollPane);
        add(scrollPane, BorderLayout.CENTER);

        // Bottom panel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.setBackground(ViewStyle.WINDOW_BACKGROUND);
        bottomPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, ViewStyle.BORDER_SUBTLE),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        backButton = new JButton("Back");
        ViewStyle.applyButtonStyle(backButton);
        backButton.addActionListener(evt -> {
            System.out.println("Back button pressed");
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
        String priceString = (event.getPriceMin() == -1)
                ? "Price unavailable"
                : String.format("Min: %d, Max: %d", event.getPriceMin(), event.getPriceMax());

        JPanel eventRow = ViewStyle.createCardPanel();
        eventRow.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);

        // Event Name
        JLabel nameLabel = new JLabel(event.getName());
        ViewStyle.applyHeaderStyle(nameLabel);

        // Date
        JLabel dateLabel = new JLabel("Date: " + (event.getDate() == null ? "TBD" : event.getDate()));
        ViewStyle.applySecondaryLabelStyle(dateLabel);

        // Venue

        JLabel venueLabel = new JLabel("Venue: " + event.getVenue());
        ViewStyle.applySecondaryLabelStyle(venueLabel);

        // Price (Green Text)
        JLabel priceLabel = new JLabel("Price: " + priceString);
        priceLabel.setFont(ViewStyle.BODY_FONT_BOLD);
        priceLabel.setForeground(ViewStyle.TEXT_PRICE);

        // Artists (Meta Text)
        JLabel artistLabel = new JLabel("Artists: " + event.getArtists());
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
    public String getViewName() {
        return viewName;
    }
}