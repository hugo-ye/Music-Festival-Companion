package app;

import data_access.DBDataAccessObject;
import data_access.FileUserDataAccessObject;
import data_access.InMemoryUserDataAccessObject;
import entity.User;
import interface_adapter.ViewManagerModel;
import interface_adapter.display_event.DisplayEventViewModel;
import interface_adapter.display_notifications.DisplayNotificationsController;
import interface_adapter.display_notifications.DisplayNotificationsPresenter;
import interface_adapter.display_notifications.DisplayNotificationsViewModel;
import interface_adapter.display_search_results.DisplaySearchResultsViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.save_event_to_list.SaveEventToListViewModel;
import interface_adapter.search_event.SearchEventViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import use_case.display_notifications.DisplayNotificationsInputBoundary;
import use_case.display_notifications.DisplayNotificationsInteractor;
import use_case.display_notifications.DisplayNotificationsOutputBoundary;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.search_event.SearchEventDataAccessInterface;
import use_case.signup.SignupInteractor;
import view.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AppBuilder {
    private final JPanel views = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    final ViewManagerModel viewManagerModel = new ViewManagerModel();
    ViewManager viewManager = new ViewManager(views, cardLayout, viewManagerModel);

    // Data access setup
    final InMemoryUserDataAccessObject sessionDao = new InMemoryUserDataAccessObject();
    final SearchEventDataAccessInterface searchDao = new DBDataAccessObject();
    final FileUserDataAccessObject fileUserDataAccessObject = new FileUserDataAccessObject("users.json", sessionDao);

    // Sign up
    private SignupView signupView;
    private SignupViewModel signupViewModel;

    // Login
    private LoginViewModel loginViewModel;
    private LoginView loginView;

    // Search
    private SearchEventViewModel searchViewModel;
    private SearchView searchView;

    // Display Event
    private DisplayEventViewModel displayEventViewModel;
    private EventView displayEventView;

    // Display Search Result
    private DisplaySearchResultsViewModel displaySearchResultsViewModel;
    private SearchResultView displaySearchResultsView;

    // Save event to list
    private SaveEventToListViewModel saveEventToListViewModel;
    private SaveEventToListView saveEventToListView;

    // Display Notifications
    private DisplayNotificationsViewModel displayNotificationsViewModel;
    private DisplayNotificationView displayNotificationsView;


    public AppBuilder() {
        views.setLayout(cardLayout);
    }

    public AppBuilder addSignupView() {
        views.add(signupView, signupView.getViewName());
        return this;
    }

    public AppBuilder addLoginView() {
        views.add(loginView, loginView.getViewName());
        return this;
    }

    public AppBuilder addSearchView() {
        views.add(searchView, searchView.getViewName());
        return this;
    }

    public AppBuilder addDisplayEventView() {
        views.add(displayEventView, displayEventView.getViewName());
        return this;
    }

    public AppBuilder addDisplaySearchResultsView() {
        views.add(displaySearchResultsView, displaySearchResultsView.getViewName());
        return this;
    }

    public AppBuilder addDisplayNotificationView() {
        views.add(displayNotificationsView, displayNotificationsView.getViewName());
        return this;
    }

    public AppBuilder addSignupUseCase() {
        SignupPresenter signupOutputBoundary = new SignupPresenter(viewManagerModel, signupViewModel, loginViewModel);
        SignupInteractor signupInteractor = new SignupInteractor(fileUserDataAccessObject, signupOutputBoundary);
        SignupController signupController = new SignupController(signupInteractor);
        return this;
    }

    public AppBuilder addLoginUseCase() {
        LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel, loginViewModel, searchViewModel, displayNotificationsController);
        LoginInputBoundary loginInteractor = new LoginInteractor(fileUserDataAccessObject, loginOutputBoundary, sessionDao);
        LoginController loginController = new LoginController(loginInteractor);
        return this;
    }

    public AppBuilder addDisplayNotificationUseCase() {
        DisplayNotificationsOutputBoundary displayNotificationsPresenter = new DisplayNotificationsPresenter(displayNotificationsViewModel);
        DisplayNotificationsInputBoundary displayNotificationsInteractor = new DisplayNotificationsInteractor(sessionDao, displayNotificationsPresenter);
        DisplayNotificationsController displayNotificationsController = new DisplayNotificationsController(displayNotificationsInteractor);
        return this;
    }

    public JFrame build() {
        JFrame application = new JFrame("Event Search Application");
        application.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        application.setPreferredSize(new Dimension(1000, 800));

        application.add(views);

        // Window listener
        application.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                User currentUser = sessionDao.getCurrentUser();
                if (currentUser != null) {
                    fileUserDataAccessObject.save(currentUser);
                    sessionDao.clearCurrentUser();
                    System.out.println("correctly saved user data to persistent storage");
                }
                application.dispose();
                System.exit(0);
            }
        });

        // --- INITIAL STATE ---
        viewManagerModel.setState(loginView.getViewName());
        viewManagerModel.firePropertyChanged();

        application.pack();
        application.setVisible(true);
        return application;
    }
}
