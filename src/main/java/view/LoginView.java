package view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import interface_adapter.ViewManagerModel;
import interface_adapter.display_notifications.DisplayNotificationsController;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;

/**
 * The View for the Log in Use Case.
 */
public class LoginView extends JPanel implements PropertyChangeListener {
    private final String viewName = "log in";
    private final LoginViewModel loginViewModel;
    private LoginController loginController;
    private final ViewManagerModel viewManagerModel;
    private DisplayNotificationsController displayNotificationsController;

    // UI Components
    private final JTextField usernameField = new JTextField(15);
    private final JPasswordField passwordField = new JPasswordField(15);
    private final JButton loginButton = new JButton("Log in");
    private final JButton signUpButton = new JButton("Create Account");

    public LoginView(LoginViewModel loginViewModel, ViewManagerModel viewManagerModel) {
        this.loginViewModel = loginViewModel;
        this.viewManagerModel = viewManagerModel;
        this.loginViewModel.addPropertyChangeListener(this);

        this.setLayout(new GridBagLayout());
        this.setBackground(ViewStyle.WINDOW_BACKGROUND);

        final JPanel cardPanel = ViewStyle.createCardPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBorder(new EmptyBorder(40, 40, 40, 40));

        final JLabel titleLabel = new JLabel("Welcome");
        ViewStyle.applyTitleStyle(titleLabel);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JLabel subtitleLabel = new JLabel("Please log in to continue");
        ViewStyle.applySecondaryLabelStyle(subtitleLabel);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setBackground(ViewStyle.CARD_BACKGROUND);
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 5, 0);
        gbc.weightx = 1.0;
        gbc.gridx = 0;

        // Username
        final JLabel userLabel = new JLabel("Username");
        ViewStyle.applyLabelStyle(userLabel);
        ViewStyle.applyTextFieldStyle(usernameField);

        gbc.gridy = 0;
        fieldsPanel.add(userLabel, gbc);
        gbc.gridy = 1;
        fieldsPanel.add(usernameField, gbc);

        // Password
        final JLabel passLabel = new JLabel("Password");
        ViewStyle.applyLabelStyle(passLabel);
        ViewStyle.applyTextFieldStyle(passwordField);

        gbc.gridy = 2;
        gbc.insets = new Insets(15, 0, 5, 0);
        fieldsPanel.add(passLabel, gbc);
        gbc.gridy = 3;
        gbc.insets = new Insets(5, 0, 5, 0);
        fieldsPanel.add(passwordField, gbc);

        // Buttons
        final JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(ViewStyle.CARD_BACKGROUND);

        ViewStyle.applyPrimaryButtonStyle(loginButton);
        ViewStyle.applyButtonStyle(signUpButton);

        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        signUpButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        final Dimension buttonSize = new Dimension(250, 40);
        loginButton.setMaximumSize(buttonSize);
        signUpButton.setMaximumSize(buttonSize);

        buttonPanel.add(loginButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(signUpButton);

        // Add all to card
        cardPanel.add(titleLabel);
        cardPanel.add(Box.createVerticalStrut(5));
        cardPanel.add(subtitleLabel);
        cardPanel.add(Box.createVerticalStrut(30));
        cardPanel.add(fieldsPanel);
        cardPanel.add(Box.createVerticalStrut(30));
        cardPanel.add(buttonPanel);

        this.add(cardPanel);

        // Add Listeners
        setupListeners();
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    public void setDisplayNotificationsController(DisplayNotificationsController displayNotificationsController) {
        this.displayNotificationsController = displayNotificationsController;
    }

    private void setupListeners() {
        loginButton.addActionListener(evt -> {
            if (evt.getSource().equals(loginButton)) {
                final LoginState currentState = loginViewModel.getState();
                loginController.execute(
                        currentState.getUsername(),
                        currentState.getPassword()
                );
                displayNotificationsController.execute(LocalDate.now());
            }
        });

        signUpButton.addActionListener(evt -> {
            viewManagerModel.setState("sign up");
            viewManagerModel.firePropertyChanged();
        });

        usernameField.getDocument().addDocumentListener(new DocumentListener() {
            private void update() {
                final LoginState currentState = loginViewModel.getState();
                currentState.setUsername(usernameField.getText());
                loginViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                update();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                update();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                update();
            }
        });

        passwordField.getDocument().addDocumentListener(new DocumentListener() {
            private void update() {
                final LoginState currentState = loginViewModel.getState();
                currentState.setPassword(new String(passwordField.getPassword()));
                loginViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                update();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                update();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                update();
            }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final LoginState state = (LoginState) evt.getNewValue();
        if (state.getErrorMessage() != null && !state.getErrorMessage().isEmpty()) {
            JOptionPane.showMessageDialog(this, state.getErrorMessage());
            state.setErrorMessage(null);
        }

        if (state.getUsername() != null && !usernameField.getText().equals(state.getUsername())) {
            usernameField.setText(state.getUsername());
        }
        passwordField.setText("");

    }

    public String getViewName() {
        return viewName;
    }
}