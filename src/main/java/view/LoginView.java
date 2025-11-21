package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LoginView extends JPanel implements PropertyChangeListener {
    private final String viewName = "log in";
    private final LoginViewModel loginViewModel;
    private final LoginController loginController;
    private final ViewManagerModel viewManagerModel;

    private final JTextField usernameField = new JTextField(15);
    private final JPasswordField passwordField = new JPasswordField(15);

    private final JButton login;
    private final JButton signUp;

    public LoginView(LoginViewModel loginViewModel, LoginController loginController, ViewManagerModel viewManagerModel) {
        this.loginViewModel = loginViewModel;
        this.loginController = loginController;
        this.viewManagerModel = viewManagerModel;
        loginViewModel.addPropertyChangeListener(this);

        JLabel title = new JLabel("Log in");
        title.setAlignmentX(CENTER_ALIGNMENT);

        JPanel usernamePanel = new JPanel();
        usernamePanel.add(new JLabel("Username:"));
        usernamePanel.add(usernameField);

        JPanel passwordPanel = new JPanel();
        passwordPanel.add(new JLabel("Password:"));
        passwordPanel.add(passwordField);

        JPanel buttonPanel = new JPanel();
        login = new JButton("Log in");
        buttonPanel.add(login);
        signUp = new JButton("Sign up");
        buttonPanel.add(signUp);

        login.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(login)) {
                            LoginState currentState = loginViewModel.getState();

                            loginController.execute(
                                    currentState.getUsername(),
                                    currentState.getPassword()
                            );
                        }
                    }
                }
        );

        signUp.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        viewManagerModel.setState("sign up");
                        viewManagerModel.firePropertyChanged();

                    }
                }
        );

        usernameField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                LoginState currentState = loginViewModel.getState();
                currentState.setUsername(usernameField.getText());
                loginViewModel.setState(currentState);
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

        passwordField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                LoginState currentState = loginViewModel.getState();
                currentState.setPassword(new String(passwordField.getPassword()));
                loginViewModel.setState(currentState);
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


        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(title);
        this.add(usernamePanel);
        this.add(passwordPanel);
        this.add(buttonPanel);
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        LoginState state = (LoginState) evt.getNewValue();
        if (state.getErrorMessage() != null) {
            JOptionPane.showMessageDialog(this, state.getErrorMessage());
        }
    }
}
