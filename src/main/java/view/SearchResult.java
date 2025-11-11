package view;

import data_access.FileUserDataAccessObject;
import entity.Event;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SearchResult {
    private FileUserDataAccessObject DAO = new FileUserDataAccessObject(); //assuming that DAO takes in a String and returns a list of events;
    private JTextField searchField = new JTextField(20);
    private JButton searchButton = new JButton("search");
    // these are temp. for the final design we use JComboBox
    private JButton sortNameButton = new JButton("Sort by Name");
    private JButton sortArtistButton = new JButton("Sort by Artist");
    //to organize the result
    private JTable table;
    private DefaultTableModel tableModel;

    private List<Event> results = new ArrayList<>();
    private JFrame frame;

    public SearchResult(){
        frame = new JFrame("Search for an event");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        // to organize the frame
        frame.setLayout(new BorderLayout());

        //search button and text field
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("search: "));
        topPanel.add(searchField);
        topPanel.add(searchButton);
        frame.add(topPanel, BorderLayout.NORTH);
        // display the result.
        tableModel = new DefaultTableModel(new String[] {"Name", "Artists"}, 0 );
        table = new JTable(tableModel);
        //make the table scrollable if there is no space
        frame.add(new JScrollPane(table), BorderLayout.CENTER);

        // display the Sorted search
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(sortNameButton);
        bottomPanel.add(sortArtistButton);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Action listeners
        searchButton.addActionListener(e -> search());
        // temporary. In the final design we should add a new class that deals with sorting
        sortArtistButton.addActionListener(e -> sortByName());
        sortNameButton.addActionListener( e -> sortByArtist());
    }
    private void search(){
        String searchFor = searchField.getText().trim();
        // handles the case where the user doesn't write anything
        if (searchFor.isEmpty()){
            JOptionPane.showMessageDialog(frame, "Please enter a event name");
            return;
        }
        try{
            results = DAO.searchEvent(searchFor); // assuming the DAO has a searchEvent method.
            if (results == null || results.isEmpty()){
                JOptionPane.showMessageDialog(null, "no events found ");
                return;
            }
            updateTable();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error during search: " + ex.getMessage());
        }
    }
    // Clears the table and fills it with the tracks from the current search results.
    private void updateTable(){
        tableModel.setRowCount(0);
        for (Event event : results){
            tableModel.addRow(new Object[] {event.getName(), event.getArtists()});
        }
    }
    // temp.
    private void sortByName(){
        results.sort(Comparator.comparing(Event::getName, String.CASE_INSENSITIVE_ORDER));
        updateTable();
    }
    private void sortByArtist(){
        results.sort(Comparator.comparing(event -> String.join(", ", event.getArtists()), String.CASE_INSENSITIVE_ORDER));
        updateTable();
    }
}
