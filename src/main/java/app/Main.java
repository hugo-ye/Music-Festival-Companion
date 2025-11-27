package app;

import data_access.DBDataAccessObject;
import data_access.FileUserDataAccessObject;
import data_access.InMemoryUserDataAccessObject;
import entity.User;
import interface_adapter.ViewManagerModel;
import interface_adapter.attend_event.AttendEventController;
import interface_adapter.attend_event.AttendEventPresenter;
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
import interface_adapter.display_event_lists.DisplayEventListsController;
import interface_adapter.display_event_lists.DisplayEventListsPresenter;
import interface_adapter.display_notifications.DisplayNotificationsController;
import interface_adapter.display_notifications.DisplayNotificationsPresenter;
import interface_adapter.display_notifications.DisplayNotificationsViewModel;
import interface_adapter.display_search_results.DisplaySearchResultsViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.logout.LogoutPresenter;
import interface_adapter.remove_event_from_list.RemoveEventFromListController;
import interface_adapter.remove_event_from_list.RemoveEventFromListPresenter;
import interface_adapter.remove_event_from_list.RemoveEventFromListViewModel;
import interface_adapter.save_event_to_list.SaveEventToListController;
import interface_adapter.save_event_to_list.SaveEventToListPresenter;
import interface_adapter.save_event_to_list.SaveEventToListViewModel;
import interface_adapter.search_event.SearchEventController;
import interface_adapter.search_event.SearchEventPresenter;
import interface_adapter.search_event.SearchEventViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import interface_adapter.sort_events.SortEventsController;
import interface_adapter.sort_events.SortEventsPresenter;
import use_case.attend_event.AttendEventInteractor;
import use_case.create_event_list.CreateEventListInteractor;
import use_case.delete_event_list.DeleteEventListInteractor;
import use_case.display_event.DisplayEventInteractor;
import use_case.display_event_list.DisplayEventListInteractor;
import use_case.display_event_lists.DisplayEventListsInteractor;
import use_case.display_notifications.DisplayNotificationsInteractor;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.logout.LogoutInteractor;
import use_case.remove_event_from_list.RemoveEventFromListInteractor;
import use_case.save_event_to_list.SaveEventToListInteractor;
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

        // ADDED: Save Event To List ViewModel
        SaveEventToListViewModel saveEventToListViewModel = new SaveEventToListViewModel();

        // ViewManager and Layout
        CardLayout layout = new CardLayout();
        JPanel views = new JPanel(layout);
        application.add(views);
        new ViewManager(views, layout, viewManagerModel);

        // --- DATA ACCESS OBJECTS ---
        SearchEventDataAccessInterface searchDao = new DBDataAccessObject();
        InMemoryUserDataAccessObject sessionDao = new InMemoryUserDataAccessObject();
        FileUserDataAccessObject fileUserDataAccessObject = new FileUserDataAccessObject("./users.json", sessionDao);


        // --- USE CASES ---

        // 0. Display Notifications
        DisplayNotificationsViewModel displayNotificationsViewModel = new DisplayNotificationsViewModel();
        DisplayNotificationsPresenter displayNotificationsPresenter = new DisplayNotificationsPresenter(displayNotificationsViewModel);
        DisplayNotificationsInteractor displayNotificationsInteractor = new DisplayNotificationsInteractor(sessionDao, displayNotificationsPresenter);
        DisplayNotificationsController displayNotificationsController = new DisplayNotificationsController(displayNotificationsInteractor);

        // 1. Login
        LoginOutputBoundary loginPresenter = new LoginPresenter(viewManagerModel, loginViewModel, searchViewModel);
        LoginInputBoundary loginInteractor = new LoginInteractor(fileUserDataAccessObject, loginPresenter, sessionDao);
        LoginController loginController = new LoginController(loginInteractor);

        // 2. Search
        SearchEventPresenter searchPresenter = new SearchEventPresenter(searchViewModel, viewManagerModel, resultViewModel);
        SearchEventInteractor searchInteractor = new SearchEventInteractor(searchDao, searchPresenter);
        SearchEventController searchController = new SearchEventController(searchInteractor);

        // 3. Signup
        SignupPresenter signupPresenter = new SignupPresenter(viewManagerModel, signupViewModel, loginViewModel);
        SignupInteractor signupInteractor = new SignupInteractor(fileUserDataAccessObject, signupPresenter);
        SignupController signupController = new SignupController(signupInteractor);

        // 4. Logout
        LogoutPresenter logoutPresenter = new LogoutPresenter(viewManagerModel, loginViewModel, searchViewModel);
        LogoutInteractor logoutInteractor = new LogoutInteractor(sessionDao, fileUserDataAccessObject , logoutPresenter);
        LogoutController logoutController = new LogoutController(logoutInteractor);

        // 5. Sort
        SortEventsPresenter sortPresenter = new SortEventsPresenter(resultViewModel);
        SortEventsInteractor sortInteractor = new SortEventsInteractor(sortPresenter);
        SortEventsController sortEventsController = new SortEventsController(sortInteractor);

        // 6. Display Event
        DisplayEventPresenter eventPresenter = new DisplayEventPresenter(eventViewModel, viewManagerModel);
        DisplayEventInteractor eventInteractor = new DisplayEventInteractor(eventPresenter, sessionDao);
        DisplayEventController eventController = new DisplayEventController(eventInteractor);

        // 7. Create Event List
        CreateEventListViewModel createEventListViewModel = new CreateEventListViewModel();
        CreateEventListPresenter createEventListPresenter = new CreateEventListPresenter(createEventListViewModel);
        CreateEventListInteractor createEventListInteractor = new CreateEventListInteractor(sessionDao, createEventListPresenter);
        CreateEventListController createEventListController = new CreateEventListController(createEventListInteractor);

        // 8. Display Event List
        DisplayEventListViewModel displayEventListViewModel = new DisplayEventListViewModel();
        DisplayEventListPresenter displayEventListPresenter = new DisplayEventListPresenter(displayEventListViewModel, viewManagerModel);
        DisplayEventListInteractor displayEventListInteractor = new DisplayEventListInteractor(sessionDao, displayEventListPresenter);
        DisplayEventListController displayEventListController = new DisplayEventListController(displayEventListInteractor);

        // 9. Delete Event List
        DeleteEventListViewModel deleteEventListViewModel = new DeleteEventListViewModel();
        DeleteEventListPresenter deleteEventListPresenter = new DeleteEventListPresenter(deleteEventListViewModel, createEventListViewModel);
        DeleteEventListInteractor deleteEventListInteractor = new DeleteEventListInteractor(sessionDao, deleteEventListPresenter);
        DeleteEventListController deleteEventListController = new DeleteEventListController(deleteEventListInteractor);

        // 10. Display Event Lists
        DisplayEventListsPresenter displayEventListsPresenter = new DisplayEventListsPresenter(viewManagerModel, createEventListViewModel);
        DisplayEventListsInteractor displayEventListsInteractor = new DisplayEventListsInteractor(sessionDao, displayEventListsPresenter);
        DisplayEventListsController displayEventListsController = new DisplayEventListsController(displayEventListsInteractor);

        // 11. ADDED: Save Event To List
        SaveEventToListPresenter savePresenter = new SaveEventToListPresenter(saveEventToListViewModel);
        SaveEventToListInteractor saveInteractor = new SaveEventToListInteractor(sessionDao, savePresenter);
        SaveEventToListController saveEventToListController = new SaveEventToListController(saveInteractor);

        // 12. ADDED: Attend Event (Assumes you have a ViewModel or ViewManager logic, but here's the base)
        // Since AttendEventPresenter wasn't fully detailed in the snippet, we assume a simple one or pass the displayViewModel
        AttendEventPresenter attendPresenter = new AttendEventPresenter(eventViewModel, viewManagerModel);
        AttendEventInteractor attendInteractor = new AttendEventInteractor(sessionDao, attendPresenter);
        AttendEventController attendEventController = new AttendEventController(attendInteractor);

        // 13. delete event from list
        RemoveEventFromListViewModel removeEventFromListViewModel = new RemoveEventFromListViewModel();
        RemoveEventFromListPresenter removePresenter = new RemoveEventFromListPresenter(removeEventFromListViewModel, displayEventListController);
        RemoveEventFromListInteractor removeInteractor = new RemoveEventFromListInteractor(sessionDao, removePresenter);
        RemoveEventFromListController removeEventFromListController = new RemoveEventFromListController(removeInteractor);



        // --- VIEWS ---

        // Login View
        LoginView loginView = new LoginView(loginViewModel, loginController, viewManagerModel, displayNotificationsController);
        views.add(loginView, loginView.getViewName());

        // Signup View
        SignupView signupView = new SignupView(signupViewModel, signupController, loginViewModel, viewManagerModel);
        views.add(signupView, signupView.getViewName());

        // Search View
        SearchView searchView = new SearchView(searchViewModel, searchController, viewManagerModel, logoutController, displayEventListsController);
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
                viewManagerModel,
                removeEventFromListController
        );
        views.add(eventListView, eventListView.getViewName());

        // Display Notification View
        DisplayNotificationView displayNotificationView = new DisplayNotificationView(displayNotificationsViewModel);
        views.add(displayNotificationView, displayNotificationView.getViewName());

        // Popup View (Instantiate it here so it starts listening, but it is a Dialog so it handles its own visibility)
        // CRITICAL FIX: Pass all required controllers and the correct ViewModel instance
        new EventView(
                application,
                eventViewModel,
                saveEventToListViewModel,
                attendEventController,
                saveEventToListController
        );



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

        application.pack();
        application.setVisible(true);
    }
}