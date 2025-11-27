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
import use_case.display_event_list.DisplayEventListInputBoundary;
import use_case.display_event_list.DisplayEventListInteractor;
import use_case.display_event_list.DisplayEventListOutputBoundary;
import use_case.display_event_lists.DisplayEventListsInteractor;
import use_case.display_notifications.DisplayNotificationsInputBoundary;
import use_case.display_notifications.DisplayNotificationsInteractor;
import use_case.display_notifications.DisplayNotificationsOutputBoundary;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.logout.LogoutInteractor;
import use_case.remove_event_from_list.RemoveEventFromListInteractor;
import use_case.save_event_to_list.SaveEventToListInteractor;
import use_case.search_event.SearchEventDataAccessInterface;
import use_case.search_event.SearchEventInputBoundary;
import use_case.search_event.SearchEventInteractor;
import use_case.search_event.SearchEventOutputBoundary;
import use_case.signup.SignupInteractor;
import use_case.sort_events.SortEventsInteractor;
import view.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AppBuilder {
    private final JFrame application = new JFrame("Event Search Application");

    private final JPanel views = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    final ViewManagerModel viewManagerModel = new ViewManagerModel();
    ViewManager viewManager = new ViewManager(views, cardLayout, viewManagerModel);

    // Data access setup
    final InMemoryUserDataAccessObject sessionDao = new InMemoryUserDataAccessObject();
    final SearchEventDataAccessInterface searchDao = new DBDataAccessObject();
    final FileUserDataAccessObject fileUserDataAccessObject = new FileUserDataAccessObject("users.json", sessionDao);

    // view models
    private final SearchEventViewModel searchViewModel = new SearchEventViewModel();
    private final DisplaySearchResultsViewModel resultViewModel = new DisplaySearchResultsViewModel();
    private final DisplayEventViewModel eventViewModel = new DisplayEventViewModel();
    private final LoginViewModel loginViewModel = new LoginViewModel();
    private final SignupViewModel signupViewModel = new SignupViewModel();
    private final CreateEventListViewModel createEventListViewModel = new CreateEventListViewModel();
    private final DisplayEventListViewModel displayEventListViewModel = new DisplayEventListViewModel();
    private final DeleteEventListViewModel deleteEventListViewModel = new DeleteEventListViewModel();
    private final SaveEventToListViewModel saveEventToListViewModel = new SaveEventToListViewModel();
    private final DisplayNotificationsViewModel displayNotificationsViewModel = new DisplayNotificationsViewModel();
    private final RemoveEventFromListViewModel removeEventFromListViewModel = new RemoveEventFromListViewModel();


    // Login
    private LoginView loginView = new LoginView(loginViewModel, viewManagerModel);

    // Sign up
    private SignupView signupView = new SignupView(signupViewModel, loginViewModel ,viewManagerModel);

    // Search
    private SearchView searchView = new SearchView(searchViewModel, viewManagerModel);

    // Display Search Result
    private SearchResultView displaySearchResultsView = new SearchResultView(resultViewModel, viewManagerModel);

    // Display Notifications
    private DisplayNotificationView displayNotificationsView = new DisplayNotificationView(displayNotificationsViewModel);

    // Event List
    private EventListView eventListView = new EventListView(displayEventListViewModel, viewManagerModel);

    // All Event Lists
    private AllEventListsView displayAllEventListsView = new AllEventListsView(createEventListViewModel, viewManagerModel);

    // Event View
    private EventView eventView = new EventView(application, eventViewModel, saveEventToListViewModel);



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
        views.add(eventListView, eventListView.getViewName());
        return this;
    }

    public AppBuilder addDisplayAllEventListsView() {
        views.add(displayAllEventListsView, displayAllEventListsView.getViewName());
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
        signupView.setSignupController(signupController);
        return this;
    }

    public AppBuilder addLoginUseCase() {
        LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel, loginViewModel, searchViewModel);
        LoginInputBoundary loginInteractor = new LoginInteractor(fileUserDataAccessObject, loginOutputBoundary, sessionDao);
        LoginController loginController = new LoginController(loginInteractor);
        loginView.setLoginController(loginController);
        return this;
    }

    public AppBuilder addSearchUseCase() {
        SearchEventOutputBoundary searchPresenter = new SearchEventPresenter(searchViewModel, viewManagerModel, resultViewModel);
        SearchEventInputBoundary searchInteractor = new SearchEventInteractor(searchDao, searchPresenter);
        SearchEventController searchController = new SearchEventController(searchInteractor);
        searchView.setController(searchController);
        return this;
    }

    public AppBuilder addLogoutUseCase() {
        LogoutPresenter logoutPresenter = new LogoutPresenter(viewManagerModel, loginViewModel, searchViewModel);
        LogoutInteractor logoutInteractor = new LogoutInteractor(sessionDao, fileUserDataAccessObject , logoutPresenter);
        LogoutController logoutController = new LogoutController(logoutInteractor);
        searchView.setLogoutController(logoutController);
        return this;
    }

    public AppBuilder addSortUseCase() {
        SortEventsPresenter sortPresenter = new SortEventsPresenter(resultViewModel);
        SortEventsInteractor sortInteractor = new SortEventsInteractor(sortPresenter);
        SortEventsController sortEventsController = new SortEventsController(sortInteractor);
        displaySearchResultsView.setSortEventsController(sortEventsController);
        return this;
    }

    public AppBuilder addDisplayEventUseCase() {
        DisplayEventPresenter eventPresenter = new DisplayEventPresenter(eventViewModel, viewManagerModel);
        DisplayEventInteractor eventInteractor = new DisplayEventInteractor(eventPresenter, sessionDao);
        DisplayEventController eventController = new DisplayEventController(eventInteractor);
        displaySearchResultsView.setDisplayEventController(eventController);
        eventListView.setDisplayEventController(eventController);
        return this;
    }

    public AppBuilder addCreateEventListUseCase() {
        CreateEventListPresenter createEventListPresenter = new CreateEventListPresenter(createEventListViewModel);
        CreateEventListInteractor createEventListInteractor = new CreateEventListInteractor(sessionDao, createEventListPresenter);
        CreateEventListController createEventListController = new CreateEventListController(createEventListInteractor);
        displayAllEventListsView.setCreateEventListController(createEventListController);
        return this;
    }

    public AppBuilder addDisplayEventListUseCase() {
        DisplayEventListOutputBoundary displayEventListPresenter = new DisplayEventListPresenter(displayEventListViewModel, viewManagerModel);
        DisplayEventListInputBoundary displayEventListInteractor = new DisplayEventListInteractor(sessionDao, displayEventListPresenter);
        DisplayEventListController displayEventListController = new DisplayEventListController(displayEventListInteractor);
        eventListView.setDisplayEventListController(displayEventListController);
        return this;
    }

    public AppBuilder addDeleteEventListUseCase() {
        DeleteEventListPresenter deleteEventListPresenter = new DeleteEventListPresenter(deleteEventListViewModel, createEventListViewModel);
        DeleteEventListInteractor deleteEventListInteractor = new DeleteEventListInteractor(sessionDao, deleteEventListPresenter);
        DeleteEventListController deleteEventListController = new DeleteEventListController(deleteEventListInteractor);
        displayAllEventListsView.setDeleteEventListController(deleteEventListController);
        return this;
    }

    public AppBuilder addDisplayEventListsUseCase() {
        DisplayEventListsPresenter displayEventListsPresenter = new DisplayEventListsPresenter(viewManagerModel, createEventListViewModel);
        DisplayEventListsInteractor displayEventListsInteractor = new DisplayEventListsInteractor(sessionDao, displayEventListsPresenter);
        DisplayEventListsController displayEventListsController = new DisplayEventListsController(displayEventListsInteractor);
        searchView.setDisplayEventListsController(displayEventListsController);
        return this;
    }

    public AppBuilder addSaveEventToListUseCase() {
        SaveEventToListPresenter savePresenter = new SaveEventToListPresenter(saveEventToListViewModel);
        SaveEventToListInteractor saveInteractor = new SaveEventToListInteractor(sessionDao, savePresenter);
        SaveEventToListController saveEventToListController = new SaveEventToListController(saveInteractor);
        eventView.setSaveEventToListController(saveEventToListController);
        return this;
    }


    public AppBuilder addDisplayNotificationUseCase() {
        DisplayNotificationsOutputBoundary displayNotificationsPresenter = new DisplayNotificationsPresenter(displayNotificationsViewModel);
        DisplayNotificationsInputBoundary displayNotificationsInteractor = new DisplayNotificationsInteractor(sessionDao, displayNotificationsPresenter);
        DisplayNotificationsController displayNotificationsController = new DisplayNotificationsController(displayNotificationsInteractor);
        loginView.setDisplayNotificationsController(displayNotificationsController);
        return this;
    }

    public AppBuilder addAttendEventUseCase() {
        AttendEventPresenter attendPresenter = new AttendEventPresenter(eventViewModel, viewManagerModel);
        AttendEventInteractor attendInteractor = new AttendEventInteractor(sessionDao, attendPresenter);
        AttendEventController attendEventController = new AttendEventController(attendInteractor);
        eventView.setAttendEventController(attendEventController);
        return this;
    }

    public AppBuilder addRemoveEventFromListUseCase() {
        RemoveEventFromListPresenter removePresenter = new RemoveEventFromListPresenter(removeEventFromListViewModel, displayEventListController);
        RemoveEventFromListInteractor removeInteractor = new RemoveEventFromListInteractor(sessionDao, removePresenter);
        RemoveEventFromListController removeEventFromListController = new RemoveEventFromListController(removeInteractor);
        eventListView.setRemoveEventFromListController(removeEventFromListController);
        return this;
    }



    public JFrame build() {
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
