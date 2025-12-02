package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import interface_adapter.ViewManagerModel;
import interface_adapter.display_event_lists.DisplayEventListsController;
import interface_adapter.logout.LogoutController;
import interface_adapter.search_event.SearchEventController;
import interface_adapter.search_event.SearchEventState;
import interface_adapter.search_event.SearchEventViewModel;

/**
 * The View for the Search Use Case.
 */
public class SearchView extends JPanel implements PropertyChangeListener {
    private final String viewName = "search event";

    private final String[] genre = {"alternative", "ballads/romantic", "blues", "chanson francaise", "children's music", "classical", "country", "dance/electronic", "folk", "hip-hop/rap", "holiday", "jazz", "medieval/renaissance", "metal", "new age", "other", "pop", "r&b", "reggae", "religious"
    };

    private SearchEventController controller;
    private final ViewManagerModel viewManagerModel;
    private final SearchEventViewModel searchViewModel;
    private LogoutController logoutController;
    private DisplayEventListsController displayEventListsController;

    // UI components
    private final JLabel usernameLabel = new JLabel("Logged in as: [User]");
    private final JButton logoutButton = new JButton("Logout");
    private final JButton listsButton = new JButton("My Lists");

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

    /**
     * Constructs a new SearchView.
     *
     * @param searchViewModel  The view model for the search event use case.
     * @param viewManagerModel The view manager model.
     */
    public SearchView(final SearchEventViewModel searchViewModel,
                      final ViewManagerModel viewManagerModel) {

        this.searchViewModel = searchViewModel;
        this.viewManagerModel = viewManagerModel;

        this.searchViewModel.addPropertyChangeListener(this);

        this.genreField = new JList<>(this.genre);

        this.setLayout(new BorderLayout());
        this.setBackground(ViewStyle.WINDOW_BACKGROUND);
        this.setPreferredSize(new Dimension(800, 700));

        // Header panel
        final JPanel headerPanel = ViewStyle.createSectionPanel(new BorderLayout());

        ViewStyle.applyHeaderStyle(this.usernameLabel);
        ViewStyle.applyButtonStyle(this.logoutButton);
        ViewStyle.applyButtonStyle(this.listsButton);

        final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(this.listsButton);
        buttonPanel.add(this.logoutButton);

        headerPanel.add(this.usernameLabel, BorderLayout.WEST);
        headerPanel.add(buttonPanel, BorderLayout.EAST);
        this.add(headerPanel, BorderLayout.NORTH);

        // Form panel
        final JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(ViewStyle.WINDOW_BACKGROUND);
        formPanel.setBorder(new EmptyBorder(20, 40, 20, 40));

        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = ViewStyle.STANDARD_PAD;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        this.styleComponents();

        // Row 1: Keyword
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        formPanel.add(this.searchLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.gridwidth = 3;
        formPanel.add(this.searchField, gbc);

        // Row 2: Location
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(this.countriesLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.5;
        formPanel.add(this.countriesField, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0;
        formPanel.add(this.cityLabel, gbc);

        gbc.gridx = 3;
        gbc.weightx = 0.5;
        formPanel.add(this.cityField, gbc);

        // Row 3: Artist
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.gridwidth = 1;
        formPanel.add(this.artistLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        formPanel.add(this.artistField, gbc);

        // Row 4: Genre
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        formPanel.add(this.genreLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;

        // Apply List Style
        ViewStyle.applyListStyle(this.genreField);
        this.genreField.setVisibleRowCount(4);
        this.genreField.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        final JScrollPane genreScroll = new JScrollPane(this.genreField);
        ViewStyle.applyScrollPaneStyle(genreScroll);
        formPanel.add(genreScroll, gbc);

        // Row 5: Dates
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        formPanel.add(this.dateLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;

        final JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        datePanel.setBackground(ViewStyle.WINDOW_BACKGROUND);

        final JLabel toLabel = new JLabel("  to  ");
        toLabel.setFont(ViewStyle.BODY_FONT);
        toLabel.setForeground(ViewStyle.TEXT_SECONDARY);

        datePanel.add(this.startDatePicker);
        datePanel.add(toLabel);
        datePanel.add(this.endDatePicker);
        formPanel.add(datePanel, gbc);

        final JScrollPane mainScroll = new JScrollPane(formPanel);
        ViewStyle.applyScrollPaneStyle(mainScroll);
        this.add(mainScroll, BorderLayout.CENTER);

        // Bottom panel
        final JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setBackground(ViewStyle.WINDOW_BACKGROUND);
        bottomPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        ViewStyle.applyPrimaryButtonStyle(this.findButton);
        this.findButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.systemInfoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.systemInfoLabel.setFont(ViewStyle.SMALL_FONT);
        this.systemInfoLabel.setForeground(ViewStyle.ERROR_COLOR);

        bottomPanel.add(this.findButton);
        bottomPanel.add(Box.createVerticalStrut(10));
        bottomPanel.add(this.systemInfoLabel);

        this.add(bottomPanel, BorderLayout.SOUTH);

        this.setupListeners();
    }

    /**
     * Sets the controller for displaying event lists.
     *
     * @param displayEventListsController The controller to set.
     */
    public void setDisplayEventListsController(final DisplayEventListsController displayEventListsController) {
        this.displayEventListsController = displayEventListsController;
    }

    /**
     * Sets the controller for logging out.
     *
     * @param logoutController The controller to set.
     */
    public void setLogoutController(final LogoutController logoutController) {
        this.logoutController = logoutController;
    }

    /**
     * Sets the controller for searching events.
     *
     * @param controller The controller to set.
     */
    public void setController(final SearchEventController controller) {
        this.controller = controller;
    }

    private void styleComponents() {
        ViewStyle.applyLabelStyle(this.searchLabel);
        ViewStyle.applyLabelStyle(this.countriesLabel);
        ViewStyle.applyLabelStyle(this.cityLabel);
        ViewStyle.applyLabelStyle(this.artistLabel);
        ViewStyle.applyLabelStyle(this.genreLabel);
        ViewStyle.applyLabelStyle(this.dateLabel);

        ViewStyle.applyTextFieldStyle(this.searchField);
        ViewStyle.applyTextFieldStyle(this.countriesField);
        ViewStyle.applyTextFieldStyle(this.cityField);
        ViewStyle.applyTextFieldStyle(this.artistField);
    }

    private void setupListeners() {
        this.addArtistListener();
        this.addCountriesListener();
        this.addCityListener();
        this.addKeywordListener();
        this.addDateListeners();

        this.genreField.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                final List<String> selectedGenres = this.genreField.getSelectedValuesList();
                final SearchEventState currentState = this.searchViewModel.getState();
                currentState.setGenre(selectedGenres);
                this.searchViewModel.setState(currentState);
            }
        });

        this.findButton.addActionListener(e -> {
            final SearchEventState currentState = this.searchViewModel.getState();

            this.controller.execute(
                    currentState.getSearchKeyword(),
                    currentState.getArtist(),
                    currentState.getCountry(),
                    currentState.getCity(),
                    currentState.getStartDate(),
                    currentState.getEndDate(),
                    currentState.getGenre()
            );
        });

        this.logoutButton.addActionListener(e -> this.logoutController.execute());

        this.listsButton.addActionListener(e -> this.displayEventListsController.execute());
    }

    /**
     * A custom formatter for date fields.
     */
    static class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
        private final String datePattern = "yyyy-MM-dd";
        private final SimpleDateFormat dateFormatter = new SimpleDateFormat(this.datePattern);

        @Override
        public Object stringToValue(final String text) throws ParseException {
            return this.dateFormatter.parse(text);
        }

        @Override
        public String valueToString(final Object value) {
            if (value != null) {
                if (value instanceof Calendar) {
                    return this.dateFormatter.format(((Calendar) value).getTime());
                }
                if (value instanceof Date) {
                    return this.dateFormatter.format(value);
                }
            }
            return "";
        }
    }

    private JDatePickerImpl generateDataPicker() {
        final UtilDateModel model = new UtilDateModel();
        final Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        final JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        return new JDatePickerImpl(datePanel, new DateLabelFormatter());
    }


    private void addDateListeners() {
        this.startDatePicker.getModel().addChangeListener(e -> {
            final Date selectedDate = (Date) this.startDatePicker.getModel().getValue();
            final SearchEventState currentState = this.searchViewModel.getState();
            if (selectedDate != null) {
                currentState.setStartDate(this.dateFormat.format(selectedDate));
            } else {
                currentState.setStartDate("");
            }
            this.searchViewModel.setState(currentState);
        });

        // Listener for End Date
        this.endDatePicker.getModel().addChangeListener(e -> {
            final Date selectedDate = (Date) this.endDatePicker.getModel().getValue();
            final SearchEventState currentState = this.searchViewModel.getState();
            if (selectedDate != null) {
                currentState.setEndDate(this.dateFormat.format(selectedDate));
            } else {
                currentState.setEndDate("");
            }
            this.searchViewModel.setState(currentState);
        });
    }

    private void addCountriesListener() {
        this.countriesField.getDocument().addDocumentListener(new DocumentListener() {
            private void update() {
                final SearchEventState currentState = SearchView.this.searchViewModel.getState();
                currentState.setCountry(SearchView.this.countriesField.getText());
                SearchView.this.searchViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(final DocumentEvent e) {
                this.update();
            }

            @Override
            public void removeUpdate(final DocumentEvent e) {
                this.update();
            }

            @Override
            public void changedUpdate(final DocumentEvent e) {
                this.update();
            }
        });
    }

    private void addCityListener() {
        this.cityField.getDocument().addDocumentListener(new DocumentListener() {
            private void update() {
                final SearchEventState currentState = SearchView.this.searchViewModel.getState();
                currentState.setCity(SearchView.this.cityField.getText());
                SearchView.this.searchViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(final DocumentEvent e) {
                this.update();
            }

            @Override
            public void removeUpdate(final DocumentEvent e) {
                this.update();
            }

            @Override
            public void changedUpdate(final DocumentEvent e) {
                this.update();
            }
        });
    }

    private void addKeywordListener() {
        this.searchField.getDocument().addDocumentListener(new DocumentListener() {
            private void update() {
                final SearchEventState currentState = SearchView.this.searchViewModel.getState();
                currentState.setSearchKeyword(SearchView.this.searchField.getText());
                SearchView.this.searchViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(final DocumentEvent e) {
                this.update();
            }

            @Override
            public void removeUpdate(final DocumentEvent e) {
                this.update();
            }

            @Override
            public void changedUpdate(final DocumentEvent e) {
                this.update();
            }
        });
    }

    private void addArtistListener() {
        this.artistField.getDocument().addDocumentListener(new DocumentListener() {
            private void update() {
                final SearchEventState currentState = SearchView.this.searchViewModel.getState();
                currentState.setArtist(SearchView.this.artistField.getText());
                SearchView.this.searchViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(final DocumentEvent e) {
                this.update();
            }

            @Override
            public void removeUpdate(final DocumentEvent e) {
                this.update();
            }

            @Override
            public void changedUpdate(final DocumentEvent e) {
                this.update();
            }
        });
    }

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        final SearchEventState state = (SearchEventState) evt.getNewValue();

        if (state.getUsername() != null) {
            this.usernameLabel.setText("Logged in as: " + state.getUsername());
        }

        if (!this.searchField.getText().equals(state.getSearchKeyword())) {
            this.searchField.setText(state.getSearchKeyword());
        }
        if (!this.countriesField.getText().equals(state.getCountry())) {
            this.countriesField.setText(state.getCountry());
        }
        if (!this.cityField.getText().equals(state.getCity())) {
            this.cityField.setText(state.getCity());
        }
        if (!this.artistField.getText().equals(state.getArtist())) {
            this.artistField.setText(state.getArtist());
        }

        this.systemInfoLabel.setText(state.getErrorMessage());

        if (state.getStartDate() == null || state.getStartDate().isEmpty()) {
            this.startDatePicker.getModel().setValue(null);
        }
        if (state.getEndDate() == null || state.getEndDate().isEmpty()) {
            this.endDatePicker.getModel().setValue(null);
        }

        if (state.getGenre().isEmpty()) {
            this.genreField.clearSelection();
        }
    }

    /**
     * Gets the name of the view.
     *
     * @return The name of the view.
     */
    public String getViewName() {
        return this.viewName;
    }
}
