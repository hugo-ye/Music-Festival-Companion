package app;

import data_access.DBDataAccessObject;
import data_access.FileListDataAccessObject;
import data_access.InMemoryUserDataAccessObject;
import entity.User;
import interface_adapter.ViewManagerModel;
import interface_adapter.create_event_list.CreateEventListController;
import interface_adapter.create_event_list.CreateEventListPresenter;
import interface_adapter.create_event_list.CreateEventListViewModel;
import interface_adapter.delete_event_list.DeleteEventListController;
import interface_adapter.delete_event_list.DeleteEventListPresenter;
import interface_adapter.delete_event_list.DeleteEventListViewModel;
import interface_adapter.display_event.DisplayEventController;
import interface_adapter.display_event.DisplayEventPresenter;
import interface_adapter.display_event.DisplayEventViewModel;
import interface_adapter.display_event_list.DisplayEventListController;
import interface_adapter.display_event_list.DisplayEventListPresenter;
import interface_adapter.display_event_list.DisplayEventListViewModel;
import interface_adapter.display_search_results.DisplaySearchResultsViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter; // Imported
import interface_adapter.login.LoginViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.logout.LogoutPresenter;
import interface_adapter.search_event.SearchEventController;
import interface_adapter.search_event.SearchEventPresenter;
import interface_adapter.search_event.SearchEventViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import interface_adapter.sort_events.SortEventsController;
import interface_adapter.sort_events.SortEventsPresenter;
import use_case.create_event_list.CreateEventListInteractor;
import use_case.delete_event_list.DeleteEventListInteractor;
import use_case.display_event.DisplayEventInteractor;
import use_case.display_event_list.DisplayEventListInteractor;
import use_case.login.LoginInputBoundary; // Imported
import use_case.login.LoginInteractor;     // Imported
import use_case.login.LoginOutputBoundary; // Imported
import use_case.logout.LogoutInteractor;
import use_case.search_event.SearchEventDataAccessInterface;
import use_case.search_event.SearchEventInteractor;
import use_case.signup.SignupInteractor;
import use_case.sort_events.SortEventsInteractor;
import view.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
    public static void main(String[] args) {

        // Window setup
        JFrame application = new JFrame("Event Search Application");
        application.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        application.setPreferredSize(new Dimension(1000, 800));

        // ViewModels
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        SearchEventViewModel searchViewModel = new SearchEventViewModel();
        DisplaySearchResultsViewModel resultViewModel = new DisplaySearchResultsViewModel();
        DisplayEventViewModel eventViewModel = new DisplayEventViewModel();
        LoginViewModel loginViewModel = new LoginViewModel();
        SignupViewModel signupViewModel = new SignupViewModel();

        // ViewManager and Layout
        CardLayout layout = new CardLayout();
        JPanel views = new JPanel(layout);
        application.add(views);
        new ViewManager(views, layout, viewManagerModel);

        // --- DATA ACCESS OBJECTS ---
        SearchEventDataAccessInterface searchDao = new DBDataAccessObject();

        // This manages the "Who is currently logged in" in memory
        InMemoryUserDataAccessObject sessionDao = new InMemoryUserDataAccessObject();

        // This manages saving users to the JSON file
        // We pass sessionDao because the FileDAO constructor asks for it
        FileListDataAccessObject fileUserDataAccessObject = new FileListDataAccessObject("./users.json", sessionDao);


        // --- USE CASES ---

        // 1. Login Use Case (FIXED) ------------------------------------------------
        // We inject SearchViewModel so the Presenter can switch to it upon success
        LoginOutputBoundary loginPresenter = new LoginPresenter(viewManagerModel, loginViewModel, searchViewModel);

        LoginInputBoundary loginInteractor = new LoginInteractor(
                fileUserDataAccessObject, // To check if user exists in file
                loginPresenter,           // To update the view
                sessionDao                // To save the user to the session (memory)
        );

        LoginController loginController = new LoginController(loginInteractor);
        // --------------------------------------------------------------------------


        // 2. Search Use Case
        SearchEventPresenter searchPresenter = new SearchEventPresenter(searchViewModel, viewManagerModel, resultViewModel);
        SearchEventInteractor searchInteractor = new SearchEventInteractor(searchDao, searchPresenter);
        SearchEventController searchController = new SearchEventController(searchInteractor);

        // 3. Signup Use Case
        SignupPresenter signupPresenter = new SignupPresenter(viewManagerModel, signupViewModel, loginViewModel);
        SignupInteractor signupInteractor = new SignupInteractor(fileUserDataAccessObject, signupPresenter);
        SignupController signupController = new SignupController(signupInteractor);

        // 4. Logout Use Case
        LogoutPresenter logoutPresenter = new LogoutPresenter(viewManagerModel, loginViewModel, searchViewModel);
        LogoutInteractor logoutInteractor = new LogoutInteractor(sessionDao, logoutPresenter);
        LogoutController logoutController = new LogoutController(logoutInteractor);

        // 5. Sort Use Case
        SortEventsPresenter sortPresenter = new SortEventsPresenter(resultViewModel);
        SortEventsInteractor sortInteractor = new SortEventsInteractor(sortPresenter);
        SortEventsController sortEventsController = new SortEventsController(sortInteractor);

        // 6. Display Event Use Case
        DisplayEventPresenter eventPresenter = new DisplayEventPresenter(eventViewModel, viewManagerModel);
        DisplayEventInteractor eventInteractor = new DisplayEventInteractor(eventPresenter);
        DisplayEventController eventController = new DisplayEventController(eventInteractor);

        // 7. Create Event List Use Case
        CreateEventListViewModel createEventListViewModel = new CreateEventListViewModel();
        CreateEventListPresenter createEventListPresenter = new CreateEventListPresenter(createEventListViewModel);
        CreateEventListInteractor createEventListInteractor = new CreateEventListInteractor(sessionDao, createEventListPresenter);
        CreateEventListController createEventListController = new CreateEventListController(createEventListInteractor);

        // 8. Display Event List Use Case
        DisplayEventListViewModel displayEventListViewModel = new DisplayEventListViewModel();
        DisplayEventListPresenter displayEventListPresenter = new DisplayEventListPresenter(displayEventListViewModel, viewManagerModel);
        DisplayEventListInteractor displayEventListInteractor = new DisplayEventListInteractor(sessionDao, displayEventListPresenter);
        DisplayEventListController displayEventListController = new DisplayEventListController(displayEventListInteractor);

        // 9. Delete Event List Use Case
        DeleteEventListViewModel deleteEventListViewModel = new DeleteEventListViewModel();
        DeleteEventListPresenter deleteEventListPresenter = new DeleteEventListPresenter(deleteEventListViewModel, createEventListViewModel);
        DeleteEventListInteractor deleteEventListInteractor = new DeleteEventListInteractor(sessionDao, deleteEventListPresenter);
        DeleteEventListController deleteEventListController = new DeleteEventListController(deleteEventListInteractor);


        // --- VIEWS ---

        // Login View (Now uses the valid loginController)
        LoginView loginView = new LoginView(loginViewModel, loginController, viewManagerModel);
        views.add(loginView, loginView.getViewName());

        // Signup View
        SignupView signupView = new SignupView(signupViewModel, signupController, loginViewModel, viewManagerModel);
        views.add(signupView, signupView.getViewName());

        // Search View
        SearchView searchView = new SearchView(searchViewModel, searchController, viewManagerModel, logoutController);
        views.add(searchView, searchViewModel.getViewName());

        // Result View
        SearchResultView resultView = new SearchResultView(resultViewModel, sortEventsController, eventController, viewManagerModel);
        views.add(resultView, resultViewModel.getViewName());

        // Create Event List View
        AllEventListsView allEventListsView = new AllEventListsView(createEventListViewModel, viewManagerModel, createEventListController, deleteEventListController , displayEventListController);
        views.add(allEventListsView, allEventListsView.getViewName());

        // Display Event List View
        EventListView eventListView = new EventListView(
                displayEventListViewModel,
                displayEventListController,
                eventController,
                viewManagerModel
        );
        views.add(eventListView, eventListView.getViewName());



        // Popup View
        new EventView(application, eventViewModel);

        // --- INITIAL STATE ---
        // Start at Login
        viewManagerModel.setState(loginView.getViewName());
        viewManagerModel.firePropertyChanged();

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
                                      }
        );

                application.pack();
        application.setVisible(true);
    }
}