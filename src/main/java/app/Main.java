package app;

import data_access.DBDataAccessObject;
import data_access.InMemoryUserDataAccessObject;
import interface_adapter.ViewManagerModel;
import interface_adapter.display_event.DisplayEventController;
import interface_adapter.display_event.DisplayEventPresenter;
import interface_adapter.display_event.DisplayEventViewModel;
import interface_adapter.display_search_results.DisplaySearchResultsViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.logout.LogoutPresenter;
import interface_adapter.search_event.SearchEventController;
import interface_adapter.search_event.SearchEventPresenter;
import interface_adapter.search_event.SearchEventViewModel;
import interface_adapter.signup.SignupController; // Import this
import interface_adapter.signup.SignupViewModel; // Import this
import interface_adapter.sort_events.SortEventsController;
import interface_adapter.sort_events.SortEventsPresenter;
import use_case.display_event.DisplayEventInteractor;
import use_case.logout.LogoutInteractor;
import use_case.search_event.SearchEventDataAccessInterface;
import use_case.search_event.SearchEventInteractor;
import use_case.sort_events.SortEventsInteractor;
import view.*;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        // Window setup
        JFrame application = new JFrame("Event Search Application");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        application.setPreferredSize(new Dimension(1000, 800));

        // DAO
        SearchEventDataAccessInterface searchDao = new DBDataAccessObject();
        InMemoryUserDataAccessObject sessionDao = new InMemoryUserDataAccessObject();

        // ViewModels
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        SearchEventViewModel searchViewModel = new SearchEventViewModel();
        DisplaySearchResultsViewModel resultViewModel = new DisplaySearchResultsViewModel();
        DisplayEventViewModel eventViewModel = new DisplayEventViewModel();
        LoginViewModel loginViewModel = new LoginViewModel();
        SignupViewModel signupViewModel = new SignupViewModel(); // 1. Create SignupViewModel

        // ViewManager and Layout
        CardLayout layout = new CardLayout();
        JPanel views = new JPanel(layout);
        application.add(views);
        new ViewManager(views, layout, viewManagerModel);

        // Search Use Case
        SearchEventPresenter searchPresenter = new SearchEventPresenter(searchViewModel, viewManagerModel, resultViewModel);
        SearchEventInteractor searchInteractor = new SearchEventInteractor(searchDao, searchPresenter);
        SearchEventController searchController = new SearchEventController(searchInteractor);

        // Logout Use Case (nav only)
        LogoutPresenter logoutPresenter = new LogoutPresenter(viewManagerModel, loginViewModel, searchViewModel);
        LogoutInteractor logoutInteractor = new LogoutInteractor(sessionDao, logoutPresenter);
        LogoutController logoutController = new LogoutController(logoutInteractor);

        // Login Use Case
        LoginController loginController = null;

        // Signup Use Case (This handles Login -> Signup navigation logic if needed)
        SignupController signupController = null;

        // Sort Use Case
        SortEventsPresenter sortPresenter = new SortEventsPresenter(resultViewModel);
        SortEventsInteractor sortInteractor = new SortEventsInteractor(sortPresenter);
        SortEventsController sortEventsController = new SortEventsController(sortInteractor);

        // Display Event Use Case
        DisplayEventPresenter eventPresenter = new DisplayEventPresenter(eventViewModel, viewManagerModel);
        DisplayEventInteractor eventInteractor = new DisplayEventInteractor(eventPresenter);
        DisplayEventController eventController = new DisplayEventController(eventInteractor);

        // Instantiate Views
        LoginView loginView = new LoginView(loginViewModel, loginController, viewManagerModel);
        views.add(loginView, loginView.getViewName());

        SignupView signupView = new SignupView(signupViewModel, signupController, loginViewModel, viewManagerModel);
        views.add(signupView, signupView.getViewName());

        SearchView searchView = new SearchView(searchViewModel, searchController, viewManagerModel, logoutController);
        views.add(searchView, searchViewModel.getViewName());

        SearchResultView resultView = new SearchResultView(resultViewModel, sortEventsController, eventController, viewManagerModel);
        views.add(resultView, resultViewModel.getViewName());

        // Popup View not in cardlayout (keep this here. this will be refreshed everytime you check a different event)
        new EventView(application, eventViewModel);

        // Start at search
        viewManagerModel.setState(searchViewModel.getViewName());
        viewManagerModel.firePropertyChanged();

        application.pack();
        application.setVisible(true);
    }
}