package view;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import data_access.DBDataAccessObject;
import interface_adapter.ViewManagerModel;
import interface_adapter.search_event.SearchEventController;
import interface_adapter.search_event.SearchEventPresenter;
import interface_adapter.search_event.SearchEventState;
import interface_adapter.search_event.SearchEventViewModel;
import interface_adapter.search_event_result.SearchEventResultState;
import org.jdatepicker.impl.*;
import use_case.search_event.SearchEventDataAccessInterface;
import use_case.search_event.SearchEventInteractor;

import java.util.List;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;
import javax.swing.ListSelectionModel;


public class SearchView extends JPanel {
    // default information
    String[] genre = {
            "alternative"
            , "ballads/romantic"
            , "blues"
            , "chanson francaise"
            , "children's music"
            , "classical"
            , "country"
            , "dance/electronic"
            , "folk"
            , "hip-hop/rap"
            , "holiday"
            , "jazz"
            , "medieval/renaissance"
            , "metal"
            , "new age"
            , "other"
            , "pop"
            , "r&b"
            , "reggae"
            , "religious"};

    // CA Componenets
    private final SearchEventController controller;
    private final ViewManagerModel viewManagerModel;
    private final SearchEventViewModel searchViewModel;

    // view name
    private final String viewName = "search event";
    private final JLabel usernameLabel = new JLabel("Welcome, [USERNAME PLACEHOLDER]"); //TODO: Add username when the inmemmory is finished

    private final JButton logoutButton = new JButton("Logout");

    // search field panel
    private final JTextField searchField = new JTextField("Enter event keyword");

    // JTextBox location panel
    private final JLabel countriesLabel = new JLabel("Countries");
    private final JTextField countriesField = new JTextField();
    private final JLabel cityLabel = new JLabel("Town");
    private final JTextField cityField = new JTextField();

    // artist name panel
    private final JLabel artistLabel = new JLabel("Artist");
    private final JTextField artistField = new JTextField();

    //genre panel
    private final JLabel genreLabel = new JLabel("Genre");
    private final JList genreField = new JList(genre);

    // data picker panel
    private final JLabel dateLabel = new JLabel("Date");
    private final JDatePickerImpl startDatePicker = generateDataPicker();
    private final JDatePickerImpl endDatePicker = generateDataPicker();

    // find button field
    private final JButton findButton = new JButton("Find");

    // system information field
    private final JLabel systemInfoLabel = new JLabel();

    public SearchView(SearchEventViewModel searchViewModel, SearchEventController controller, ViewManagerModel viewManagerModel) {
        this.searchViewModel = searchViewModel;
        this.controller = controller;
        this.viewManagerModel = viewManagerModel;

        JFrame frame = new JFrame(viewName);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 600); // Increased height to accommodate new header

        // set general layout
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- ROW 0: HEADER (USERNAME & LOGOUT) ---
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.NORTHWEST;

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        headerPanel.add(usernameLabel);
        headerPanel.add(logoutButton);
        frame.add(headerPanel, gbc);
        // ---------------------------------------------

        // --- ROW 1: Search Keyword Field ---
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;

        frame.add(searchField, gbc);

        // --- ROW 2: Location (Countries/City) ---
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 1.0;
        gbc.gridwidth = 3;

        JPanel comboBoxPanel = new JPanel();
        comboBoxPanel.setLayout(new BoxLayout(comboBoxPanel, BoxLayout.X_AXIS));
        comboBoxPanel.add(countriesLabel);
        comboBoxPanel.add(countriesField);
        comboBoxPanel.add(cityLabel);
        comboBoxPanel.add(cityField);
        frame.add(comboBoxPanel, gbc);

        // --- ROW 3: Artist Name ---
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 1.0;
        gbc.gridwidth = 3;

        JPanel artistNamePanel = new JPanel();
        artistNamePanel.setLayout(new BoxLayout(artistNamePanel, BoxLayout.X_AXIS));
        artistNamePanel.add(artistLabel);
        artistNamePanel.add(artistField);
        frame.add(artistNamePanel, gbc);

        // --- ROW 4: Genre ---
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;
        gbc.gridwidth = 1;

        JPanel genrePanel = new JPanel();
        genrePanel.setLayout(new BoxLayout(genrePanel, BoxLayout.X_AXIS));
        JScrollPane genreScroll = new JScrollPane(genreField);
        genreField.setVisibleRowCount(5);
        genreField.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        genrePanel.add(genreLabel);
        genrePanel.add(genreScroll);
        frame.add(genrePanel, gbc);

        // --- ROW 5: Date Pickers ---
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0.5;
        gbc.gridwidth = 1;

        JPanel datePickerPanel = new JPanel();
        datePickerPanel.setLayout(new BoxLayout(datePickerPanel, BoxLayout.X_AXIS));
        datePickerPanel.add(dateLabel);
        datePickerPanel.add(startDatePicker);
        datePickerPanel.add(endDatePicker);
        frame.add(datePickerPanel, gbc);

        // --- ROW 6: Find Button ---
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 0.5;
        gbc.gridwidth = 2;

        JPanel findPanel = new JPanel();
        findPanel.setLayout(new BoxLayout(findPanel, BoxLayout.X_AXIS));
        findPanel.add(findButton);
        frame.add(findPanel, gbc);

        // --- ROW 7: System Info ---
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 1;
        gbc.gridwidth = 1;

        JPanel systemInfoPanel = new JPanel();
        systemInfoPanel.setLayout(new BoxLayout(systemInfoPanel, BoxLayout.X_AXIS));
        systemInfoPanel.add(systemInfoLabel);

        // --- Add Listeners ---
        addArtistListener();
        addCountriesListener();
        addCityListener();
        addKeywordListener();

        // Re-enabled listeners from previous fix
        genreField.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                List<String> selectedGenres = genreField.getSelectedValuesList();
                SearchEventState currentState = searchViewModel.getState();
                currentState.setGenre(selectedGenres);
                searchViewModel.setState(currentState);
            }
        });


        startDatePicker.addActionListener(e -> {
            SearchEventState currentState = searchViewModel.getState();
            currentState.setStartDate(startDatePicker.getJFormattedTextField().getText());
            System.out.println("in view the start date is: " + currentState.getStartDate());
            searchViewModel.setState(currentState);
        });

        endDatePicker.addActionListener(e -> {
            SearchEventState currentState = searchViewModel.getState();
            currentState.setEndDate(endDatePicker.getJFormattedTextField().getText());
            System.out.println("in view, end date is: " + currentState.getEndDate());
            searchViewModel.setState(currentState);
        });


        frame.add(systemInfoPanel, gbc);
        frame.pack();

        // --- Find Button Action Listener ---
        findButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getSource().equals(findButton)) {
                            SearchEventState currentState = searchViewModel.getState();

                            // Get selected genre list
                            List<String> selectedGenres = genreField.getSelectedValuesList();

                            controller.execute(
                                    currentState.getSearch_keyword(),
                                    currentState.getArtist(),
                                    currentState.getCountry(),
                                    currentState.getCity(),
                                    currentState.getStartDate(),
                                    currentState.getEndDate(),
                                    selectedGenres // Pass the selected list directly
                            );
                        }
                    }
                }
        );

        // --- LOGOUT Button Action Listener (New) ---
        logoutButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        searchViewModel.setState(new SearchEventState());
                        viewManagerModel.setState("logout");
                        viewManagerModel.firePropertyChanged();
                    }
                }
        );


        frame.setVisible(true);
    }

    static class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
        private final String datePattern = "yyyy-MM-dd";
        private final java.text.SimpleDateFormat dateFormatter = new java.text.SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws java.text.ParseException {
            return dateFormatter.parse(text);
        }

        @Override
        public String valueToString(Object value) {
            if (value != null) {
                java.util.Calendar cal = (java.util.Calendar) value;
                return dateFormatter.format(cal.getTime());
            }
            return "";
        }
    }

    private JDatePickerImpl generateDataPicker() {
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");

        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        return new JDatePickerImpl(datePanel, new DateLabelFormatter());
    }

    private void addCountriesListener() {
        countriesField.getDocument().addDocumentListener(
                new DocumentListener() {
                    private void documentListenerHelper() {
                        SearchEventState currentState = searchViewModel.getState();
                        currentState.setCountry(countriesField.getText());
                        searchViewModel.setState(currentState);
                    }

                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        documentListenerHelper();
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        documentListenerHelper();
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        documentListenerHelper();
                    }
                }
        );
    }

    private void addCityListener() {
        cityField.getDocument().addDocumentListener(
                new DocumentListener() {
                    private void documentListenerHelper() {
                        SearchEventState currentState = searchViewModel.getState();
                        currentState.setCity(cityField.getText());
                        searchViewModel.setState(currentState);
                    }

                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        documentListenerHelper();
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        documentListenerHelper();
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        documentListenerHelper();
                    }
                }
        );
    }

    private void addKeywordListener() {
        searchField.getDocument().addDocumentListener(
                new DocumentListener() {
                    private void documentListenerHelper() {
                        SearchEventState currentState = searchViewModel.getState();
                        currentState.setSearch_keyword(searchField.getText());
                        searchViewModel.setState(currentState);
                    }

                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        documentListenerHelper();
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        documentListenerHelper();
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        documentListenerHelper();
                    }
                }
        );
    }

    private void addArtistListener() {
        artistField.getDocument().addDocumentListener(
                new DocumentListener() {
                    private void documentListenerHelper() {
                        SearchEventState currentState = searchViewModel.getState();
                        currentState.setArtist(artistField.getText());
                        searchViewModel.setState(currentState);
                    }

                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        documentListenerHelper();
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        documentListenerHelper();
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        documentListenerHelper();
                    }
                }
        );
    }


    public static void main(String[] args) {
        // Dummy setup for demonstration
        SearchEventViewModel searchViewModel = new SearchEventViewModel();
        SearchEventDataAccessInterface dao = new DBDataAccessObject();
        ViewManagerModel viewManager = new ViewManagerModel();
        SearchEventResultState resutState = new SearchEventResultState();
        SearchEventPresenter presenter = new SearchEventPresenter(searchViewModel, viewManager, resutState);
        SearchEventInteractor interactor = new SearchEventInteractor(dao, presenter);
        SearchEventController controller = new SearchEventController(interactor);
        SearchView searchView = new SearchView(searchViewModel, controller, viewManager);
    }
}