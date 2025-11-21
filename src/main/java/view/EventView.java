package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.attend_event.AttendEventController;
import interface_adapter.attend_event.AttendEventViewModel;
import interface_adapter.display_event.DisplayEventController;
import interface_adapter.display_event.DisplayEventState;
import interface_adapter.display_event.DisplayEventViewModel;
import interface_adapter.save_event_to_list.SaveEventToListController;
import interface_adapter.save_event_to_list.SaveEventToListViewModel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class EventView extends JDialog {

    // image
    private final JLabel imageLabel = new JLabel();

    // text fields
    private final JLabel eventNameLabel = new JLabel("Default Event Name");
    private final JLabel artistLabel = new JLabel("Default Artist");
    private final JLabel venueLabel = new JLabel("Default Venue");
    private final JLabel locationLabel = new JLabel("Default city, Default country");   // city, country
    private final JLabel dateLabel = new JLabel("Default date");
    private final JLabel genresLabel = new JLabel("Default genre");
    private final JLabel priceRangeLabel = new JLabel("Pricing:");
    private final JLabel ticketPriceRangeLabel = new JLabel("Default minPrice MaxPrice");

    // buttons
    private final JButton buyButton = new JButton("Buy Tickets");
    private final JButton attendButton = new JButton("Attend Event");
    private final JButton saveButton = new JButton("Save Event");

    // Url
    private String ticketUrl;


    public EventView(JFrame parent) {
        super(parent, "Event Details", true);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(500, 400));
        setLocationRelativeTo(parent);

        imageLabel.setPreferredSize(new Dimension(400, 300)); // 4:3
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(480, 200));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        add(imageLabel, BorderLayout.NORTH);

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.add(eventNameLabel);
        detailsPanel.add(artistLabel);
        detailsPanel.add(venueLabel);
        detailsPanel.add(locationLabel);
        detailsPanel.add(dateLabel);
        detailsPanel.add(genresLabel);
        detailsPanel.add(priceRangeLabel);
        detailsPanel.add(ticketPriceRangeLabel);
        detailsPanel.add(buyButton);
        detailsPanel.add(attendButton);
        add(detailsPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(attendButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(buyButton);

        buyButton.addActionListener(e -> {
            if (ticketUrl != null) {
                try {
                    Desktop.getDesktop().browse(new java.net.URI(ticketUrl));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        add(buttonPanel, BorderLayout.SOUTH);

        pack();

    }
    public void setEventName(String name) {
        eventNameLabel.setText(name);
    }

    public void setArtists(java.util.List<String> artists) {
        artistLabel.setText(String.join(", ", artists));
    }

    public void setVenue(String venue) {
        venueLabel.setText(venue);
    }

    public void setLocation(String city, String country) {
        locationLabel.setText(city + ", " + country);
    }

    public void setDate(String dateText) {
        dateLabel.setText(dateText);
    }

    public void setGenres(java.util.List<String> genres) {
        genresLabel.setText(String.join(", ", genres));
    }

    public void setPriceRange(int min, int max) {
        ticketPriceRangeLabel.setText(min + " - " + max);
    }

    private ImageIcon scaleImage(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image scaled = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }


    public void setImageIcon(ImageIcon icon) {
        ImageIcon scaled = scaleImage(icon, 300, 200); // 3:2 ratio
        imageLabel.setIcon(scaled);
    }


    public void setTicketUrl(String ticketUrl) {
        this.ticketUrl = ticketUrl;

    }

    public static void main(String[] args) {
        JFrame parent = new JFrame();
        parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        EventView view = new EventView(parent);

        view.setEventName("Taylor Swift Concert");
        view.setArtists(java.util.List.of("Taylor Swift"));
        view.setVenue("Rogers Centre");
        view.setLocation("Toronto", "Canada");
        view.setDate("2025-07-18");
        view.setGenres(List.of("Pop", "Country"));
        view.setPriceRange(150, 900);

        ImageIcon sampleIcon = new ImageIcon(
                "/Users/hugoye/IdeaProjects/team-project/example_image.png" // sample.png does not exist on our repo. this will break. test with your own URL
        );
        view.setImageIcon(sampleIcon);

        view.setTicketUrl("https://www.ticketmaster.ca");
        view.setVisible(true);

    }

}
