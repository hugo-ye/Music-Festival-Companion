package view;

import entity.Event;
import interface_adapter.ViewManagerModel;
import interface_adapter.display_event.DisplayEventController;
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
    // Add controllers and viewmodels here
    private final DisplayEventListViewModel displayEventListViewModel;
    private final DisplayEventListController displayEventListController;
    private final ViewManagerModel viewManagerModel;

    // Swing components
    private final JPanel eventsPanel;
    private final JButton backButton;

    public EventListView(DisplayEventListViewModel displayEventViewModel,
                         DisplayEventListController displayEventListController,
                         ViewManagerModel viewManagerModel) {
        // add controller, viewmodel here
        this.displayEventListViewModel = displayEventViewModel;
        this.displayEventListViewModel.addPropertyChangeListener(this);

        this.displayEventListController = displayEventListController;

        this.viewManagerModel = viewManagerModel;

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

        backButton.addActionListener(e -> {
            if (viewManagerModel == null) {
                return;
            }
            viewManagerModel.setState("event lists");
            viewManagerModel.firePropertyChanged();
        });
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("refresh")) {
            System.out.println("refresh called in EventListView");
            DisplayEventListState state = (DisplayEventListState) evt.getNewValue();
            if (state.getEventList() != null) {
                eventsPanel.removeAll();
                for (Event event : state.getEventList().getEvents()) {
                    JPanel eventRow = createEventRow(event);
                    eventsPanel.add(eventRow);
                }

            }
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
                displayEventListController.execute(event.getId());
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
        return this.viewName;
    }
}
