package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import entity.Event;

public class SearchResultView extends JPanel {
    // panels
    private JPanel columnPanel = new JPanel();
    private JPanel resultPanel = new JPanel();
    private JPanel sortingPanel = new JPanel();
    JScrollPane scrollPane = new JScrollPane(resultPanel);
    private JButton sortButton = new JButton("Sort");
    private String[] options = {"Sort by Name", "Sort by Artist"};
    private JFrame frame = new JFrame("Search Results");
    // results of the search
    private List<Event> results = new ArrayList<>();
    JComboBox<String> sortingOptions = new JComboBox<>(options);

    public SearchResultView() {
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        columnPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

        // adding random events to test
        results.add(new Event("Event2", "Event2",List.of("Artist3"), "venue", "toronto",
                "canada", new Date(), 10, 20, "ticketurl", List.of("rock")));
        results.add(new Event("Event1", "Event1",List.of("Artist2"), "venue", "toronto",
                "canada", new Date(), 10, 20, "ticketurl", List.of("rock")));
        results.add(new Event("Event3", "Event3",List.of("Artist1"), "venue", "toronto",
                "canada", new Date(), 10, 20, "ticketurl", List.of("rock")));
        displayEvent(results);

        // columns
        JLabel eventLabel = new JLabel("Events");
        JLabel artistLabel = new JLabel("Artists");
        JLabel detailsLabel = new JLabel("view event details");
        JLabel spacer = new JLabel(" ".repeat(300));
        JLabel spacer2 = new JLabel(" ".repeat(280));

        columnPanel.add(eventLabel);
        columnPanel.add(spacer);
        columnPanel.add(artistLabel);
        columnPanel.add(spacer2);
        columnPanel.add(detailsLabel);
        sortingPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        sortingPanel.add(sortingOptions);
        sortingPanel.add(spacer);
        sortingPanel.add(sortButton);
        // to enable the user to scroll.
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(100, 100));
        sortButton.addActionListener(e -> {
            String selected = (String) sortingOptions.getSelectedItem();
            sorting(selected);
        });
        frame.setLayout(new BorderLayout());
        // adding the panels to the main panels
        add(columnPanel);
        add(sortingPanel);
        add(scrollPane);
        // adding the main panel to the JFrame
        frame.add(this.columnPanel, BorderLayout.CENTER);
        frame.add(this.sortingPanel, BorderLayout.NORTH);
        frame.add(this.scrollPane, BorderLayout.CENTER);

    }
    /**
    * displays the result of the search.
    * @param events a list of Events the yser searched for
     */
    private void displayEvent(List<Event> events){
        resultPanel.removeAll();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        for (Event event : events){
            JPanel panel = new JPanel();
            panel.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
            JLabel eventLabel = new JLabel(event.getName());
            JLabel artistLabel = new JLabel(event.getArtists().toString());
            JLabel spacer = new JLabel(" ".repeat(200));
            JLabel spacer2 = new JLabel(" ".repeat(200));
            JButton detailsButton = new JButton(event.getName()+" detail");
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

    /**
     * picks what "sorting" to use (e.g. sort by name or sort by artists)
     * @param sorting is an Object.
     */
    private void sorting(Object sorting){
        switch(sorting.toString()){
            case "Sort by Name":
                sortByName();
                break;
            case "Sort by Artist":
                sortByArtist();
                break;
        }
    }

    /**
     * sorts the name of events alphabetically and displays the results by calling the displayEvent method
     *
     */
    private void sortByName(){
        results.sort(Comparator.comparing(Event::getName, String.CASE_INSENSITIVE_ORDER));
        displayEvent(results);
    }

    /**
     * sorts the Artists name alphabetically and displays the results by calling the displayEvent method
     */
    private void sortByArtist(){
        results.sort(Comparator.comparing(event -> String.join(", ", event.getArtists()), String.CASE_INSENSITIVE_ORDER));
        displayEvent(results);
    }

}
