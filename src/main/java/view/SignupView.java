package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupState;
import interface_adapter.signup.SignupViewModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class SignupView extends JPanel implements PropertyChangeListener {

    private final String viewName = "sign up";

    private final SignupViewModel signupViewModel;
    private SignupController signupController;
    private final LoginViewModel loginViewModel;
    private final ViewManagerModel viewManagerModel;

    // UI Components
    private final JTextField usernameInputField = new JTextField(15);
    private final JPasswordField passwordInputField = new JPasswordField(15);
    private final JPasswordField repeatPasswordInputField = new JPasswordField(15);

    private final JButton signUpButton = new JButton("Sign up");
    private final JButton cancelButton = new JButton("Cancel");

    public SignupView(SignupViewModel signupViewModel, LoginViewModel loginViewModel, ViewManagerModel viewManagerModel) {
        this.signupViewModel = signupViewModel;
        this.viewManagerModel = viewManagerModel;
        this.loginViewModel = loginViewModel;
        signupViewModel.addPropertyChangeListener(this);

        // Main Layout Setup
        this.setLayout(new GridBagLayout()); // Center the card
        this.setBackground(ViewStyle.WINDOW_BACKGROUND);

        // Create the Card
        JPanel cardPanel = ViewStyle.createCardPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBorder(new EmptyBorder(40, 40, 40, 40));

        // Title
        JLabel titleLabel = new JLabel("Create Account");
        ViewStyle.applyTitleStyle(titleLabel);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Inputs
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setBackground(ViewStyle.CARD_BACKGROUND);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 5, 0);
        gbc.weightx = 1.0;
        gbc.gridx = 0;

        // Username
        JLabel userLabel = new JLabel("Username");
        ViewStyle.applyLabelStyle(userLabel);
        ViewStyle.applyTextFieldStyle(usernameInputField);

        gbc.gridy = 0; fieldsPanel.add(userLabel, gbc);
        gbc.gridy = 1; fieldsPanel.add(usernameInputField, gbc);

        // Password
        JLabel passLabel = new JLabel("Password");
        ViewStyle.applyLabelStyle(passLabel);
        ViewStyle.applyTextFieldStyle(passwordInputField);

        gbc.gridy = 2; fieldsPanel.add(passLabel, gbc);
        gbc.gridy = 3; fieldsPanel.add(passwordInputField, gbc);

        // Repeat Password
        JLabel repeatLabel = new JLabel("Repeat Password");
        ViewStyle.applyLabelStyle(repeatLabel);
        ViewStyle.applyTextFieldStyle(repeatPasswordInputField);

        gbc.gridy = 4; fieldsPanel.add(repeatLabel, gbc);
        gbc.gridy = 5; fieldsPanel.add(repeatPasswordInputField, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(ViewStyle.CARD_BACKGROUND);

        ViewStyle.applyPrimaryButtonStyle(signUpButton);
        ViewStyle.applyButtonStyle(cancelButton);

        signUpButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        Dimension buttonSize = new Dimension(250, 40);
        signUpButton.setMaximumSize(buttonSize);
        cancelButton.setMaximumSize(buttonSize);

        buttonPanel.add(signUpButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(cancelButton);

        // add to card
        cardPanel.add(titleLabel);
        cardPanel.add(Box.createVerticalStrut(30));
        cardPanel.add(fieldsPanel);
        cardPanel.add(Box.createVerticalStrut(30));
        cardPanel.add(buttonPanel);

        this.add(cardPanel);

        // Listeners
        setupListeners();
    }
    
    public void setSignupController(SignupController controller) {
        this.signupController = controller;
    }

    private void setupListeners() {
        signUpButton.addActionListener(evt -> {
            if (evt.getSource().equals(signUpButton) && signupController != null) {
                SignupState currentState = signupViewModel.getState();
                signupController.execute(
                        currentState.getUsername(),
                        currentState.getPassword(),
                        currentState.getRepeatPassword()
                );
            }
        });

        cancelButton.addActionListener(e -> {
            viewManagerModel.setState(loginViewModel.getViewName());
            viewManagerModel.firePropertyChanged();
        });

        // Username Listener
        usernameInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void update() {
                SignupState currentState = signupViewModel.getState();
                currentState.setUsername(usernameInputField.getText());
                signupViewModel.setState(currentState);
            }
            @Override public void insertUpdate(DocumentEvent e) { update(); }
            @Override public void removeUpdate(DocumentEvent e) { update(); }
            @Override public void changedUpdate(DocumentEvent e) { update(); }
        });

        // Password Listener
        passwordInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void update() {
                SignupState currentState = signupViewModel.getState();
                currentState.setPassword(new String(passwordInputField.getPassword()));
                signupViewModel.setState(currentState);
            }
            @Override public void insertUpdate(DocumentEvent e) { update(); }
            @Override public void removeUpdate(DocumentEvent e) { update(); }
            @Override public void changedUpdate(DocumentEvent e) { update(); }
        });

        // Repeat Password Listener
        repeatPasswordInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void update() {
                SignupState currentState = signupViewModel.getState();
                currentState.setRepeatPassword(new String(repeatPasswordInputField.getPassword()));
                signupViewModel.setState(currentState);
            }
            @Override public void insertUpdate(DocumentEvent e) { update(); }
            @Override public void removeUpdate(DocumentEvent e) { update(); }
            @Override public void changedUpdate(DocumentEvent e) { update(); }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final SignupState state = (SignupState) evt.getNewValue();
        if (!state.getErrorMessage().isEmpty()) {
            JOptionPane.showMessageDialog(this, state.getErrorMessage());
        }
    }

    public String getViewName() {
        return this.viewName;
    }
}