package view;

import entity.Event;
import entity.EventList;
import interface_adapter.attend_event.AttendEventController;
import interface_adapter.display_event.DisplayEventState;
import interface_adapter.display_event.DisplayEventViewModel;
import interface_adapter.save_event_to_list.SaveEventToListController;
import interface_adapter.save_event_to_list.SaveEventToListState; // Import added
import interface_adapter.save_event_to_list.SaveEventToListViewModel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class EventView extends JDialog implements PropertyChangeListener {
    public final String viewName = "event details";

    private final JLabel imageLabel;
    private final JLabel titleLabel;

    // Components
    private final JTextArea artistValue, venueValue, locationValue, dateValue, genresValue, priceRangeValue;
    private final JButton buyButton, attendButton, saveButton, closeButton;

    private String ticketUrl;

    // Dependencies
    private final DisplayEventViewModel displayEventViewModel;
    private final SaveEventToListViewModel saveEventToListViewModel;
    private final AttendEventController attendEventController;
    private final SaveEventToListController saveEventToListController;

    // State
    private Event currentEvent;

    public EventView(Frame owner,
                     DisplayEventViewModel displayEventViewModel,
                     SaveEventToListViewModel saveEventToListViewModel,
                     AttendEventController attendEventController,
                     SaveEventToListController saveEventToListController) {
        super(owner, "Event Details", true);

        this.displayEventViewModel = displayEventViewModel;
        this.saveEventToListViewModel = saveEventToListViewModel;
        this.attendEventController = attendEventController;
        this.saveEventToListController = saveEventToListController;

        // Register as listener to BOTH view models
        this.displayEventViewModel.addPropertyChangeListener(this);
        this.saveEventToListViewModel.addPropertyChangeListener(this);

        this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setResizable(false);
        this.getContentPane().setBackground(ViewStyle.WINDOW_BACKGROUND);

        // --- UI CONSTRUCTION ---

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
        ViewStyle.applyPriceStyle(priceRangeValue);

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

        // --- LISTENERS ---
        attendButton.addActionListener(evt -> handleAttendEvent());
        saveButton.addActionListener(evt -> handleSaveEvent());
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

    // --- LOGIC METHODS ---

    private void handleAttendEvent() {
        if (currentEvent != null) {
            attendEventController.execute(currentEvent);
            JOptionPane.showMessageDialog(this, "Request to attend sent!");
        }
    }

    private void handleSaveEvent() {
        if (currentEvent == null) return;

        // 1. Get available lists from the DisplayEventState
        DisplayEventState state = displayEventViewModel.getState();
        List<EventList> availableLists = state.getAvailableLists();

        if (availableLists == null || availableLists.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No lists available to save to.");
            return;
        }

        // 2. Filter out the Master List
        List<EventList> userLists = new ArrayList<>();
        for (EventList list : availableLists) {
            // Adjust to match your Master List logic
            if (!list.getName().equalsIgnoreCase("Master List")) {
                userLists.add(list);
            }
        }

        if (userLists.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Create a custom list first.");
            return;
        }

        // 3. Open the Dialog
        ChooseListDialog dialog = new ChooseListDialog((Frame) SwingUtilities.getWindowAncestor(this), userLists);
        dialog.setVisible(true);

        // 4. If user clicked Save in the dialog
        if (dialog.isSaved()) {
            List<EventList> selectedLists = dialog.getSelectedLists();
            if (!selectedLists.isEmpty()) {
                EventList[] listsArray = selectedLists.toArray(new EventList[0]);
                saveEventToListController.SaveEventToList(currentEvent, listsArray);
            } else {
                JOptionPane.showMessageDialog(this, "No lists selected.");
            }
        }
    }

    private void addLabelAndValue(JPanel panel, String labelText, JTextArea valueArea,
                                  GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.0;
        gbc.insets = new Insets(0, 0, 8, 15);
        gbc.anchor = GridBagConstraints.NORTHWEST;

        JLabel staticLabel = new JLabel(labelText);
        ViewStyle.applyLabelStyle(staticLabel);
        panel.add(staticLabel, gbc);

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
        SwingWorker<ImageIcon, Void> worker = new SwingWorker<>() {
            @Override
            protected ImageIcon doInBackground() throws Exception {
                if (urlString == null || urlString.isEmpty()) return null;
                URL url = new URL(urlString);
                BufferedImage originalImage = ImageIO.read(url);

                int targetWidth = 300;
                int targetHeight = 300;
                double widthRatio = (double) targetWidth / originalImage.getWidth();
                double heightRatio = (double) targetHeight / originalImage.getHeight();
                double scaleFactor = Math.max(widthRatio, heightRatio);
                int scaledWidth = (int) (originalImage.getWidth() * scaleFactor);
                int scaledHeight = (int) (originalImage.getHeight() * scaleFactor);
                Image scaledImage = originalImage.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
                BufferedImage croppedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2 = croppedImage.createGraphics();
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                int x = (targetWidth - scaledWidth) / 2;
                int y = (targetHeight - scaledHeight) / 2;
                g2.drawImage(scaledImage, x, y, null);
                g2.dispose();
                return new ImageIcon(croppedImage);
            }

            @Override
            protected void done() {
                try {
                    ImageIcon icon = get();
                    if (icon != null) {
                        imageLabel.setIcon(icon);
                        imageLabel.setText("");
                    } else {
                        imageLabel.setIcon(null);
                        imageLabel.setText("No Image Available");
                    }
                } catch (Exception e) {
                    imageLabel.setIcon(null);
                    imageLabel.setText("Image Error");
                    e.printStackTrace();
                }
            }
        };
        worker.execute();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // 1. UPDATE VIEW WITH EVENT DETAILS
        if ("refresh".equals(evt.getPropertyName())) {
            DisplayEventState state = (DisplayEventState) evt.getNewValue();
            if (state != null) {
                this.currentEvent = state.getEvent(); // CAPTURE EVENT

                titleLabel.setText(state.getEventName());
                artistValue.setText(state.getArtists());
                venueValue.setText(state.getVenue());
                locationValue.setText(state.getLocation());
                dateValue.setText(state.getDate());
                genresValue.setText(state.getGenres());
                priceRangeValue.setText(state.getPrice());
                ticketUrl = state.getTicketUrl();

                imageLabel.setIcon(null);
                imageLabel.setText("Loading...");
                setEventImage(state.getImageUrl());

                this.pack();
                this.setVisible(true);
            }
        }
        else if ("message".equals(evt.getPropertyName())) {
            // Retrieve state from the ViewModel
            SaveEventToListState state = saveEventToListViewModel.getState();
            JOptionPane.showMessageDialog(this, state.getMessage());
        }
    }

    public String getViewName(){return viewName;}
}