package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.search_event.SearchEventController;
import interface_adapter.search_event.SearchEventState;
import interface_adapter.search_event.SearchEventViewModel;
import org.jdatepicker.impl.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class SearchView extends JPanel implements PropertyChangeListener {
    public final String viewName = "search event";

    String[] genre = {
            "alternative", "ballads/romantic", "blues", "chanson francaise",
            "children's music", "classical", "country", "dance/electronic",
            "folk", "hip-hop/rap", "holiday", "jazz", "medieval/renaissance",
            "metal", "new age", "other", "pop", "r&b", "reggae", "religious"
    };

    private final SearchEventController controller;
    private final ViewManagerModel viewManagerModel;
    private final SearchEventViewModel searchViewModel;

    // UI components
    private final JLabel usernameLabel = new JLabel("Welcome, [User]");
    private final JButton logoutButton = new JButton("Logout");

    private final JLabel searchLabel = new JLabel("Keyword:");
    private final JTextField searchField = new JTextField(20);

    private final JLabel countriesLabel = new JLabel("Country:");
    private final JTextField countriesField = new JTextField(15);

    private final JLabel cityLabel = new JLabel("City:");
    private final JTextField cityField = new JTextField(15);

    private final JLabel artistLabel = new JLabel("Artist:");
    private final JTextField artistField = new JTextField(20);

    private final JLabel genreLabel = new JLabel("Genre:");
    private final JList<String> genreField;

    private final JLabel dateLabel = new JLabel("Date Range:");

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final JDatePickerImpl startDatePicker = generateDataPicker();
    private final JDatePickerImpl endDatePicker = generateDataPicker();

    private final JButton findButton = new JButton("Find Events");
    private final JLabel systemInfoLabel = new JLabel();

    public SearchView(SearchEventViewModel searchViewModel,
                      SearchEventController controller,
                      ViewManagerModel viewManagerModel) {

        this.searchViewModel = searchViewModel;
        this.controller = controller;
        this.viewManagerModel = viewManagerModel;

        this.searchViewModel.addPropertyChangeListener(this);

        genreField = new JList<>(genre);

        this.setLayout(new BorderLayout());
        this.setBackground(ViewStyle.WINDOW_BACKGROUND);
        this.setPreferredSize(new Dimension(800, 700));

        // Header panel
        JPanel headerPanel = ViewStyle.createSectionPanel(new BorderLayout());

        ViewStyle.applyHeaderStyle(usernameLabel);
        ViewStyle.applyButtonStyle(logoutButton);

        headerPanel.add(usernameLabel, BorderLayout.WEST);
        headerPanel.add(logoutButton, BorderLayout.EAST);
        this.add(headerPanel, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(ViewStyle.WINDOW_BACKGROUND);
        formPanel.setBorder(new EmptyBorder(20, 40, 20, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = ViewStyle.STANDARD_PAD;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        styleComponents();

        // Row 1: Keyword
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        formPanel.add(searchLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.gridwidth = 3;
        formPanel.add(searchField, gbc);

        // Row 2: Location
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(countriesLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.5;
        formPanel.add(countriesField, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0;
        formPanel.add(cityLabel, gbc);

        gbc.gridx = 3;
        gbc.weightx = 0.5;
        formPanel.add(cityField, gbc);

        // Row 3: Artist
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.gridwidth = 1;
        formPanel.add(artistLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        formPanel.add(artistField, gbc);

        // Row 4: Genre
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        formPanel.add(genreLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;

        // Apply List Style
        ViewStyle.applyListStyle(genreField);
        genreField.setVisibleRowCount(4);
        genreField.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        JScrollPane genreScroll = new JScrollPane(genreField);
        ViewStyle.applyScrollPaneStyle(genreScroll);
        formPanel.add(genreScroll, gbc);

        // Row 5: Dates
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        formPanel.add(dateLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;

        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        datePanel.setBackground(ViewStyle.WINDOW_BACKGROUND);

        JLabel toLabel = new JLabel("  to  ");
        toLabel.setFont(ViewStyle.BODY_FONT);
        toLabel.setForeground(ViewStyle.TEXT_SECONDARY);

        datePanel.add(startDatePicker);
        datePanel.add(toLabel);
        datePanel.add(endDatePicker);
        formPanel.add(datePanel, gbc);

        JScrollPane mainScroll = new JScrollPane(formPanel);
        ViewStyle.applyScrollPaneStyle(mainScroll);
        this.add(mainScroll, BorderLayout.CENTER);

        // Bottom panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setBackground(ViewStyle.WINDOW_BACKGROUND);
        bottomPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        ViewStyle.applyPrimaryButtonStyle(findButton);
        findButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        systemInfoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        systemInfoLabel.setFont(ViewStyle.SMALL_FONT);
        systemInfoLabel.setForeground(ViewStyle.ERROR_COLOR);

        bottomPanel.add(findButton);
        bottomPanel.add(Box.createVerticalStrut(10));
        bottomPanel.add(systemInfoLabel);

        this.add(bottomPanel, BorderLayout.SOUTH);

        setupListeners();
    }

    private void styleComponents() {
        ViewStyle.applyLabelStyle(searchLabel);
        ViewStyle.applyLabelStyle(countriesLabel);
        ViewStyle.applyLabelStyle(cityLabel);
        ViewStyle.applyLabelStyle(artistLabel);
        ViewStyle.applyLabelStyle(genreLabel);
        ViewStyle.applyLabelStyle(dateLabel);

        ViewStyle.applyTextFieldStyle(searchField);
        ViewStyle.applyTextFieldStyle(countriesField);
        ViewStyle.applyTextFieldStyle(cityField);
        ViewStyle.applyTextFieldStyle(artistField);
    }

    private void setupListeners() {
        addArtistListener();
        addCountriesListener();
        addCityListener();
        addKeywordListener();
        addDateListeners();

        genreField.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                List<String> selectedGenres = genreField.getSelectedValuesList();
                SearchEventState currentState = searchViewModel.getState();
                currentState.setGenre(selectedGenres);
                searchViewModel.setState(currentState);
            }
        });

        findButton.addActionListener(e -> {
            SearchEventState currentState = searchViewModel.getState();

            controller.execute(
                    currentState.getSearch_keyword(),
                    currentState.getArtist(),
                    currentState.getCountry(),
                    currentState.getCity(),
                    currentState.getStartDate(),
                    currentState.getEndDate(),
                    currentState.getGenre()
            );
        });

        logoutButton.addActionListener(e -> {
            SearchEventState emptyState = new SearchEventState();
            searchViewModel.setState(emptyState);
            // Fire property change so the View updates (clears fields)
            searchViewModel.firePropertyChanged();

            viewManagerModel.setState("login");
            viewManagerModel.firePropertyChanged("view");
        });
    }

    static class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
        private final String datePattern = "yyyy-MM-dd";
        private final SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws java.text.ParseException {
            return dateFormatter.parse(text);
        }

        @Override
        public String valueToString(Object value) {
            if (value != null) {
                if (value instanceof Calendar) {
                    return dateFormatter.format(((Calendar) value).getTime());
                }
                if (value instanceof Date) {
                    return dateFormatter.format(value);
                }
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


    private void addDateListeners() {
        // Listener for Start Date
        startDatePicker.getModel().addChangeListener(e -> {
            Date selectedDate = (Date) startDatePicker.getModel().getValue();
            SearchEventState currentState = searchViewModel.getState();
            if (selectedDate != null) {
                currentState.setStartDate(dateFormat.format(selectedDate));
            } else {
                currentState.setStartDate("");
            }
            searchViewModel.setState(currentState);
        });

        // Listener for End Date
        endDatePicker.getModel().addChangeListener(e -> {
            Date selectedDate = (Date) endDatePicker.getModel().getValue();
            SearchEventState currentState = searchViewModel.getState();
            if (selectedDate != null) {
                currentState.setEndDate(dateFormat.format(selectedDate));
            } else {
                currentState.setEndDate("");
            }
            searchViewModel.setState(currentState);
        });
    }

    private void addCountriesListener() {
        countriesField.getDocument().addDocumentListener(new DocumentListener() {
            private void update() {
                SearchEventState currentState = searchViewModel.getState();
                currentState.setCountry(countriesField.getText());
                searchViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) { update(); }
            @Override
            public void removeUpdate(DocumentEvent e) { update(); }
            @Override
            public void changedUpdate(DocumentEvent e) { update(); }
        });
    }

    private void addCityListener() {
        cityField.getDocument().addDocumentListener(new DocumentListener() {
            private void update() {
                SearchEventState currentState = searchViewModel.getState();
                currentState.setCity(cityField.getText());
                searchViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) { update(); }
            @Override
            public void removeUpdate(DocumentEvent e) { update(); }
            @Override
            public void changedUpdate(DocumentEvent e) { update(); }
        });
    }

    private void addKeywordListener() {
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            private void update() {
                SearchEventState currentState = searchViewModel.getState();
                currentState.setSearch_keyword(searchField.getText());
                searchViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) { update(); }
            @Override
            public void removeUpdate(DocumentEvent e) { update(); }
            @Override
            public void changedUpdate(DocumentEvent e) { update(); }
        });
    }

    private void addArtistListener() {
        artistField.getDocument().addDocumentListener(new DocumentListener() {
            private void update() {
                SearchEventState currentState = searchViewModel.getState();
                currentState.setArtist(artistField.getText());
                searchViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) { update(); }
            @Override
            public void removeUpdate(DocumentEvent e) { update(); }
            @Override
            public void changedUpdate(DocumentEvent e) { update(); }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        SearchEventState state = (SearchEventState) evt.getNewValue();

        // Update text fields if they don't match state
        if (!searchField.getText().equals(state.getSearch_keyword())) {
            searchField.setText(state.getSearch_keyword());
        }
        if (!countriesField.getText().equals(state.getCountry())) {
            countriesField.setText(state.getCountry());
        }
        if (!cityField.getText().equals(state.getCity())) {
            cityField.setText(state.getCity());
        }
        if (!artistField.getText().equals(state.getArtist())) {
            artistField.setText(state.getArtist());
        }

        systemInfoLabel.setText(state.getErrorMessage());

        // If state dates are empty, clear the pickers
        if (state.getStartDate().isEmpty()) {
            startDatePicker.getModel().setValue(null);
        }
        if (state.getEndDate().isEmpty()) {
            endDatePicker.getModel().setValue(null);
        }
    }
    public String getViewName() {
        return viewName;
    }
}