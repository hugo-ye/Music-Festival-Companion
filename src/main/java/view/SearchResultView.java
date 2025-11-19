package view;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDate.*;
import java.util.*;
import java.util.List;

import entity.Event;

public class SearchResultView extends JPanel{

    // main frame
    private final JFrame frame = new JFrame("Search Results");

    // panels
    private final JPanel resultPanel = new JPanel();
    private final JPanel sortingPanel = new JPanel();

    // button
    private final JButton sortButton = new JButton("Sort");

    // additional
    JScrollPane scrollPane = new JScrollPane(resultPanel);

    // results of the search
    private final List<Event> results;

    // options to choose from to sort
    private final String[] options = {"Sort by Name", "Sort by Artist"};
    JComboBox<String> sortingOptions = new JComboBox<>(options);


    public SearchResultView(List<Event> results) {

        this.results = results;
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // display a result that is not sorted at the beginning
        displayEvent(results);

        //sort panel
        sortingPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        sortingPanel.add(sortingOptions);
        sortingPanel.add(sortButton);

        // to enable the user to scroll.
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(100, 100));

        // adding the panels to the main panels
        add(sortingPanel);
        add(scrollPane);

        // adding the main panel to the frame
        frame.setLayout(new BorderLayout());
        frame.add(this.sortingPanel, BorderLayout.NORTH);
        frame.add(this.scrollPane, BorderLayout.CENTER);
    }
    /**
     *     * displays the result of the search.
     *     * @param events a list of Events the yser searched for
     *
     */
    private void displayEvent(List<Event> events) {
        resultPanel.removeAll();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        for (Event event : events) {
            JPanel panel = new JPanel();
            panel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
            JLabel eventLabel = new JLabel(event.getName());
            JLabel artistLabel = new JLabel(event.getArtists().toString());
            JLabel spacer = new JLabel(" ".repeat(200));
            JLabel spacer2 = new JLabel(" ".repeat(200));
            JButton detailsButton = new JButton(event.getName() + " details");
            panel.add(eventLabel);
            panel.add(spacer);
            panel.add(artistLabel);
            panel.add(spacer2);
            panel.add(detailsButton);
            resultPanel.add(panel);
        }
        resultPanel.revalidate();
        resultPanel.repaint();
    }

    public static void main(String[] args) {
        List<Event> lst = new ArrayList<>();
        lst.add(new Event("Event2", "Event2",List.of("Artist3"), "venue",
                "toronto", "canada", LocalDate.of(2020,5,1), 10, 20, "ticketurl", List.of("rock"), "dd"));
        lst.add(new Event("Event1", "Event1",List.of("Artist2", "Artist 3"), "venue", "toronto",
                "canada", LocalDate.of(2020,5,1), 10, 20, "ticketurl", List.of("rock"), "ww"));
        lst.add(new Event("Event3", "Event3",List.of("Artist1"), "venue", "toronto",
                "canada", LocalDate.now(), 10, 20, "ticketurl", List.of("rock"), "wg"));
        SearchResultView searchResultView = new SearchResultView(lst);
        searchResultView.frame.setVisible(true);
    }

}
