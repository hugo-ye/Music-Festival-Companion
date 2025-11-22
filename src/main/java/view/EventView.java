package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.display_event.DisplayEventState;
import interface_adapter.display_event.DisplayEventViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class EventView extends JPanel implements PropertyChangeListener {
    public final String viewName = "event details";
    // Add ViewModels and Controllers here

    // Text fields and Labels
    private final JLabel imageLabel;
    private final JLabel eventNameLabel;
    private final JLabel artistLabel;
    private final JLabel venueLabel;
    private final JLabel locationLabel;
    private final JLabel dateLabel;
    private final JLabel genresLabel;
    private final JLabel priceRangeLabel;

    // Buttons
    private final JButton buyButton;
    private final JButton attendButton;
    private final JButton saveButton;
    private final JButton closeButton;

    // Data
    private String ticketUrl;

    // CA components
    private final DisplayEventViewModel displayEventViewModel;
    private final ViewManagerModel viewManagerModel;


    public EventView(DisplayEventViewModel displayEventViewModel, ViewManagerModel viewManagerModel) {
        this.displayEventViewModel = displayEventViewModel;
        this.viewManagerModel = viewManagerModel;

        this.displayEventViewModel.addPropertyChangeListener(this);



        this.setSize(400, 600);
        this.setLayout(new BorderLayout());

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        imageLabel = new JLabel("Event Image Placeholder");
        infoPanel.add(imageLabel);

        eventNameLabel = new JLabel("Event Name");
        infoPanel.add(eventNameLabel);

        artistLabel = new JLabel("Artist Name");
        infoPanel.add(artistLabel);

        venueLabel = new JLabel("Venue Name");
        infoPanel.add(venueLabel);

        locationLabel = new JLabel("Location");
        infoPanel.add(locationLabel);

        dateLabel = new JLabel("Date");
        infoPanel.add(dateLabel);

        genresLabel = new JLabel("Genres");
        infoPanel.add(genresLabel);

        priceRangeLabel = new JLabel("Price Range");
        infoPanel.add(priceRangeLabel);

        add(infoPanel, BorderLayout.CENTER);

        JPanel mainButtonPanel = new JPanel();
        mainButtonPanel.setLayout(new BoxLayout(mainButtonPanel, BoxLayout.Y_AXIS));

        JPanel buyPanel = new JPanel();
        buyButton = new JButton("Buy Tickets");
        buyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                System.out.println("Buy button pressed");
            }
        });
        buyPanel.add(buyButton);
        mainButtonPanel.add(buyPanel);

        JPanel actionPanel = new JPanel();
        attendButton = new JButton("Attend");
        attendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                System.out.println("Attend button pressed");
            }
        });
        actionPanel.add(attendButton);

        saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                System.out.println("Save button pressed");
            }
        });
        actionPanel.add(saveButton);
        mainButtonPanel.add(actionPanel);

        JPanel closePanel = new JPanel();
        closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                System.out.println("Close button pressed");
            }
        });
        closePanel.add(closeButton);
        mainButtonPanel.add(closePanel);

        add(mainButtonPanel, BorderLayout.SOUTH);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("property changed in EventView with evt: " + evt.getPropertyName());
        if (evt.getPropertyName().equals("refresh")) {
            DisplayEventState state = (DisplayEventState) evt.getNewValue();

            if (state != null) {
                imageLabel.setText(imageLabel.getText() + state.getImageUrl());
                eventNameLabel.setText(eventNameLabel.getText() +  state.getEventName());
                artistLabel.setText(artistLabel.getText() + state.getArtists());
                venueLabel.setText(venueLabel.getText() +  state.getVenue());
                locationLabel.setText(locationLabel.getText() + state.getLocation());
                dateLabel.setText(dateLabel.getText() + state.getDate());
                genresLabel.setText(genresLabel.getText() + state.getGenres());
                priceRangeLabel.setText(priceRangeLabel.getText() + state.getPrice());
                ticketUrl = state.getTicketUrl();
            }
            revalidate();
            repaint();

        }
    }


}