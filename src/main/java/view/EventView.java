package view;

import interface_adapter.display_event.DisplayEventState;
import interface_adapter.display_event.DisplayEventViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

public class EventView extends JDialog implements PropertyChangeListener {
    public final String viewName = "event details";

    // UI Components
    private final JLabel imageLabel;

    // JTextArea allows for text wrapping, JLabel does not. Change.
    private final JTextArea eventNameValue;
    private final JTextArea artistValue;
    private final JTextArea venueValue;
    private final JTextArea locationValue;
    private final JTextArea dateValue;
    private final JTextArea genresValue;
    private final JTextArea priceRangeValue;

    // Buttons
    private final JButton buyButton;
    private final JButton attendButton;
    private final JButton saveButton;
    private final JButton closeButton;

    // Data
    private String ticketUrl;

    // CA Components
    private final DisplayEventViewModel displayEventViewModel;

    // Constants
    private static final Dimension IMAGE_SIZE = new Dimension(300, 300);
    private static final Font LABEL_FONT = new Font("SansSerif", Font.BOLD, 12);
    private static final Font VALUE_FONT = new Font("SansSerif", Font.PLAIN, 12);

    private static final int TEXT_AREA_COLUMNS = 25;

    public EventView(Frame owner, DisplayEventViewModel displayEventViewModel) {
        super(owner, "Event Details", true);
        this.displayEventViewModel = displayEventViewModel;
        this.displayEventViewModel.addPropertyChangeListener(this);

        this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setResizable(false);

        // Initialize fields using the helper method
        eventNameValue = createValueArea();
        artistValue = createValueArea();
        venueValue = createValueArea();
        locationValue = createValueArea();
        dateValue = createValueArea();
        genresValue = createValueArea();
        priceRangeValue = createValueArea();

        // Info panel for details
        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        GridBagConstraints gbc = new GridBagConstraints();

        // Image Setup
        imageLabel = new JLabel("Loading...", SwingConstants.CENTER);
        imageLabel.setPreferredSize(IMAGE_SIZE);
        imageLabel.setMinimumSize(IMAGE_SIZE);
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 15, 0);
        gbc.fill = GridBagConstraints.CENTER;
        infoPanel.add(imageLabel, gbc);

        // 2. Data Fields Setup
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        int row = 1;

        addLabelAndValue(infoPanel, "Event Name:", eventNameValue, gbc, row++);
        addLabelAndValue(infoPanel, "Artist:", artistValue, gbc, row++);
        addLabelAndValue(infoPanel, "Venue:", venueValue, gbc, row++);
        addLabelAndValue(infoPanel, "Location:", locationValue, gbc, row++);
        addLabelAndValue(infoPanel, "Date:", dateValue, gbc, row++);
        addLabelAndValue(infoPanel, "Genres:", genresValue, gbc, row++);
        addLabelAndValue(infoPanel, "Price Range:", priceRangeValue, gbc, row++);

        add(infoPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        buyButton = new JButton("Buy Tickets");
        buyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buyButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        buyButton.setOpaque(true);
        buyButton.addActionListener(this::handleBuyTickets);

        JPanel secondaryActions = new JPanel(new FlowLayout(FlowLayout.CENTER));
        attendButton = new JButton("Attend");
        saveButton = new JButton("Save");
        closeButton = new JButton("Close");

        // Button listeners...
        attendButton.addActionListener(evt -> System.out.println("Attend pressed"));
        saveButton.addActionListener(evt -> System.out.println("Save pressed"));
        closeButton.addActionListener(evt -> this.setVisible(false));

        secondaryActions.add(attendButton);
        secondaryActions.add(saveButton);
        secondaryActions.add(closeButton);

        buttonPanel.add(buyButton);
        buttonPanel.add(Box.createVerticalStrut(5));
        buttonPanel.add(secondaryActions);

        add(buttonPanel, BorderLayout.SOUTH);

        this.pack();
        this.setLocationRelativeTo(owner);
    }

    /**
     * Creates a JTextArea configured to look like a multiline Label.
     */
    private JTextArea createValueArea() {
        JTextArea area = new JTextArea();
        area.setFont(VALUE_FONT);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setEditable(false);
        area.setOpaque(false);
        area.setColumns(TEXT_AREA_COLUMNS);
        return area;
    }

    private void addLabelAndValue(JPanel panel, String labelText, JTextArea valueArea, GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.0;
        gbc.insets = new Insets(0, 0, 5, 10);
        JLabel staticLabel = new JLabel(labelText);
        staticLabel.setFont(LABEL_FONT);

        gbc.anchor = GridBagConstraints.NORTHWEST;
        panel.add(staticLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 0, 5, 0);
        panel.add(valueArea, gbc);
    }

    private void handleBuyTickets(java.awt.event.ActionEvent evt) {
        if (ticketUrl != null && !ticketUrl.isEmpty()) {
            try {
                Desktop.getDesktop().browse(new URI(ticketUrl));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Could not open link.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "No ticket URL available.");
        }
    }

    public void setEventImage(String urlString) {
        SwingUtilities.invokeLater(() -> {
            try {
                if (urlString != null && !urlString.isEmpty()) {
                    URL url = new URL(urlString);
                    BufferedImage image = ImageIO.read(url);
                    Image scaledImage = image.getScaledInstance(IMAGE_SIZE.width, IMAGE_SIZE.height, Image.SCALE_SMOOTH);
                    imageLabel.setIcon(new ImageIcon(scaledImage));
                    imageLabel.setText("");
                } else {
                    imageLabel.setIcon(null);
                    imageLabel.setText("No Image Available");
                }
            } catch (IOException e) {
                imageLabel.setIcon(null);
                imageLabel.setText("Image Error");
            }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("refresh".equals(evt.getPropertyName())) {
            DisplayEventState state = (DisplayEventState) evt.getNewValue();
            if (state != null) {
                eventNameValue.setText(state.getEventName());
                artistValue.setText(state.getArtists());
                venueValue.setText(state.getVenue());
                locationValue.setText(state.getLocation());
                dateValue.setText(state.getDate());
                genresValue.setText(state.getGenres());
                priceRangeValue.setText(state.getPrice());
                ticketUrl = state.getTicketUrl();

                setEventImage(state.getImageUrl());

                this.pack();
                this.setVisible(true);
            }
        }
    }
}