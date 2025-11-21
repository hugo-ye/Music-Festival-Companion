package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupState;
import interface_adapter.signup.SignupViewModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class SignupView extends JPanel implements PropertyChangeListener {

    private final String viewName = "sign up";

    private final SignupViewModel signupViewModel;
    private final SignupController signupController;
    private final LoginViewModel loginViewModel;
    private final ViewManagerModel viewManagerModel;

    private final JTextField usernameInputField = new JTextField(15);
    private final JPasswordField passwordInputField = new JPasswordField(15);
    private final JPasswordField repeatPasswordInputField = new JPasswordField(15);

    private final JButton signUp;
    private final JButton cancel;

    public SignupView(SignupViewModel signupViewModel, SignupController signupController, LoginViewModel loginViewModel, ViewManagerModel viewManagerModel) {
        this.signupViewModel = signupViewModel;
        this.signupController = signupController;
        this.viewManagerModel = viewManagerModel;
        this.loginViewModel = loginViewModel;
        signupViewModel.addPropertyChangeListener(this);

        JLabel title = new JLabel("Sign up");
        title.setAlignmentX(CENTER_ALIGNMENT);

        JPanel usernamePanel = new JPanel();
        usernamePanel.add(new JLabel("Username:"));
        usernamePanel.add(usernameInputField);

        JPanel passwordPanel = new JPanel();
        passwordPanel.add(new JLabel("Password:"));
        passwordPanel.add(passwordInputField);

        JPanel repeatPasswordPanel = new JPanel();
        repeatPasswordPanel.add(new JLabel("Repeat password:"));
        repeatPasswordPanel.add(repeatPasswordInputField);

        JPanel buttonPanel = new JPanel();
        signUp = new JButton("Sign up");
        buttonPanel.add(signUp);
        cancel = new JButton("Cancel");
        buttonPanel.add(cancel);

        signUp.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(signUp)) {
                            SignupState currentState = signupViewModel.getState();
                            signupController.execute(
                                    currentState.getUsername(),
                                    currentState.getPassword(),
                                    currentState.getRepeatPassword()
                            );
                        }
                    }
                }
        );

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewManagerModel.setState(loginViewModel.getViewName());
                viewManagerModel.firePropertyChanged();
            }
        });

        addUsernameListener();
        addPasswordListener();
        addRepeatPasswordListener();


        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(usernamePanel);
        this.add(passwordPanel);
        this.add(repeatPasswordPanel);
        this.add(buttonPanel);
    }

    private void addUsernameListener(){
        usernameInputField.getDocument().addDocumentListener(new DocumentListener(){
            private void documentListenerHelper() {
                SignupState currentState = signupViewModel.getState();
                currentState.setUsername(usernameInputField.getText());
                signupViewModel.setState(currentState);
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
        });

    }

    private void addPasswordListener() {
        passwordInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                SignupState currentState = signupViewModel.getState();
                currentState.setPassword(new String(passwordInputField.getPassword()));
                signupViewModel.setState(currentState);
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
        });
    }

    private void addRepeatPasswordListener() {
        repeatPasswordInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                SignupState currentState = signupViewModel.getState();
                currentState.setRepeatPassword(new String(repeatPasswordInputField.getPassword()));
                signupViewModel.setState(currentState);
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
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        SignupState state = (SignupState) evt.getNewValue();
        if (state.getErrorMessage() != null) {
            JOptionPane.showMessageDialog(this, state.getErrorMessage());
        }
    }

}
