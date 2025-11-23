package view;

import interface_adapter.display_event.DisplayEventState;
import interface_adapter.display_event.DisplayEventViewModel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

public class EventView extends JDialog implements PropertyChangeListener {
    public final String viewName = "event details";

    private final JLabel imageLabel;
    private final JLabel titleLabel;

    // Components
    private final JTextArea artistValue, venueValue, locationValue, dateValue, genresValue, priceRangeValue;
    private final JButton buyButton, attendButton, saveButton, closeButton;

    private String ticketUrl;
    private final DisplayEventViewModel displayEventViewModel;

    public EventView(Frame owner, DisplayEventViewModel displayEventViewModel) {
        super(owner, "Event Details", true);
        this.displayEventViewModel = displayEventViewModel;
        this.displayEventViewModel.addPropertyChangeListener(this);

        this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setResizable(false);
        this.getContentPane().setBackground(ViewStyle.WINDOW_BACKGROUND);

        // Title
        titleLabel = new JLabel("Event Name");
        ViewStyle.applyTitleStyle(titleLabel);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Text areas
        artistValue = new JTextArea();
        venueValue = new JTextArea();
        locationValue = new JTextArea();
        dateValue = new JTextArea();
        genresValue = new JTextArea();
        priceRangeValue = new JTextArea();

        // Apply Styles
        ViewStyle.applyValueStyle(artistValue);
        ViewStyle.applyValueStyle(venueValue);
        ViewStyle.applyValueStyle(locationValue);
        ViewStyle.applyValueStyle(dateValue);
        ViewStyle.applyValueStyle(genresValue);
        ViewStyle.applyPriceStyle(priceRangeValue); // Green text

        // Main Panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(ViewStyle.WINDOW_BACKGROUND);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();

        // Image
        imageLabel = new JLabel("Loading...", SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(300, 300));
        ViewStyle.applyImageContainerStyle(imageLabel);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 15, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(imageLabel, gbc);

        // Title
        gbc.gridy = 1;
        mainPanel.add(titleLabel, gbc);

        // Details
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        int row = 2;

        addLabelAndValue(mainPanel, "Artist:", artistValue, gbc, row++);
        addLabelAndValue(mainPanel, "Venue:", venueValue, gbc, row++);
        addLabelAndValue(mainPanel, "Location:", locationValue, gbc, row++);
        addLabelAndValue(mainPanel, "Date:", dateValue, gbc, row++);
        addLabelAndValue(mainPanel, "Genres:", genresValue, gbc, row++);
        addLabelAndValue(mainPanel, "Price Range:", priceRangeValue, gbc, row++);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        ViewStyle.applyScrollPaneStyle(scrollPane);
        add(scrollPane, BorderLayout.CENTER);

        // --- BUTTONS ---
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(ViewStyle.WINDOW_BACKGROUND);
        buttonPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, ViewStyle.BORDER_SUBTLE),
                BorderFactory.createEmptyBorder(15, 10, 15, 10)
        ));

        buyButton = new JButton("Buy Tickets");
        ViewStyle.applyPrimaryButtonStyle(buyButton);
        buyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buyButton.addActionListener(this::handleBuyTickets);

        JPanel secondaryActions = new JPanel(new FlowLayout(FlowLayout.CENTER));
        secondaryActions.setBackground(ViewStyle.WINDOW_BACKGROUND);

        attendButton = new JButton("Attend");
        saveButton = new JButton("Save");
        closeButton = new JButton("Close");

        ViewStyle.applyButtonStyle(attendButton);
        ViewStyle.applyButtonStyle(saveButton);
        ViewStyle.applyButtonStyle(closeButton);

        attendButton.addActionListener(evt -> System.out.println("Attend pressed"));
        saveButton.addActionListener(evt -> System.out.println("Save pressed"));
        closeButton.addActionListener(evt -> this.setVisible(false));

        secondaryActions.add(attendButton);
        secondaryActions.add(saveButton);
        secondaryActions.add(closeButton);

        buttonPanel.add(buyButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(secondaryActions);

        add(buttonPanel, BorderLayout.SOUTH);
        this.pack();
        this.setLocationRelativeTo(owner);
    }

    private void addLabelAndValue(JPanel panel, String labelText, JTextArea valueArea,
                                  GridBagConstraints gbc, int row) {
        // Static label
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.0;
        gbc.insets = new Insets(0, 0, 8, 15);
        gbc.anchor = GridBagConstraints.NORTHWEST;

        JLabel staticLabel = new JLabel(labelText);
        ViewStyle.applyLabelStyle(staticLabel); // Uses grey text
        panel.add(staticLabel, gbc);

        // Value area
        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 0, 8, 0);
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
                    BufferedImage originalImage = ImageIO.read(url);

                    // Target dimensions
                    int targetWidth = 300;
                    int targetHeight = 300;

                    // Calculate the Scaling Factor
                    double widthRatio = (double) targetWidth / originalImage.getWidth();
                    double heightRatio = (double) targetHeight / originalImage.getHeight();
                    double scaleFactor = Math.max(widthRatio, heightRatio);

                    // Calculate new scaled dimensions (will be >= 300)
                    int scaledWidth = (int) (originalImage.getWidth() * scaleFactor);
                    int scaledHeight = (int) (originalImage.getHeight() * scaleFactor);

                    // Create the intermediate scaled image
                    Image scaledImage = originalImage.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);

                    // Create a specific 300x300 "Crop Box"
                    BufferedImage croppedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g2 = croppedImage.createGraphics();

                    // Rendering settings
                    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

                    // Calculate coordinates to center the image
                    int x = (targetWidth - scaledWidth) / 2;
                    int y = (targetHeight - scaledHeight) / 2;

                    // Draw the image onto the 300x300 canvas
                    g2.drawImage(scaledImage, x, y, null);
                    g2.dispose();

                    imageLabel.setIcon(new ImageIcon(croppedImage));
                    imageLabel.setText("");
                } else {
                    imageLabel.setIcon(null);
                    imageLabel.setText("No Image Available");
                }
            } catch (IOException e) {
                imageLabel.setIcon(null);
                imageLabel.setText("Image Error");
                e.printStackTrace();
            }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("refresh".equals(evt.getPropertyName())) {
            DisplayEventState state = (DisplayEventState) evt.getNewValue();
            if (state != null) {
                titleLabel.setText(state.getEventName());
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
    public String getViewName(){return viewName;}
}