package app;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

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
import view.AllEventListsView;
import view.DisplayNotificationView;
import view.EventListView;
import view.EventView;
import view.LoginView;
import view.SearchResultView;
import view.SearchView;
import view.SignupView;
import view.ViewManager;

/**
 * A central builder responsible for wiring together all UI views, ViewModels, controllers,
 * presenters, interactors, and data access objects of the Event Search Application.
 *
 */
public class AppBuilder {
    private final JFrame application = new JFrame("Event Search Application");

    private final JPanel views = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(views, cardLayout, viewManagerModel);

    // Data access setup
    private final InMemoryUserDataAccessObject sessionDao = new InMemoryUserDataAccessObject();
    private final SearchEventDataAccessInterface searchDao = new DBDataAccessObject();
    private final FileUserDataAccessObject fileUserDataAccessObject =
            new FileUserDataAccessObject("users.json", sessionDao);

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
    private final LoginView loginView = new LoginView(loginViewModel, viewManagerModel);

    // Sign up
    private final SignupView signupView = new SignupView(signupViewModel, loginViewModel, viewManagerModel);

    // Search
    private final SearchView searchView = new SearchView(searchViewModel, viewManagerModel);

    // Display Search Result
    private final SearchResultView displaySearchResultsView = new SearchResultView(resultViewModel, viewManagerModel);

    // Display Notifications
    private final DisplayNotificationView displayNotificationsView =
            new DisplayNotificationView(displayNotificationsViewModel);

    // Event List
    private final EventListView eventListView = new EventListView(displayEventListViewModel, viewManagerModel);

    // All Event Lists
    private final AllEventListsView displayAllEventListsView =
            new AllEventListsView(createEventListViewModel, viewManagerModel);

    // Event View
    private final EventView eventView = new EventView(application, eventViewModel, saveEventToListViewModel);

    public AppBuilder() {
        views.setLayout(cardLayout);
    }

    /**
     * Return this AppBuilder which views contains a SignupView.
     * @return this AppBuilder
     */
    public AppBuilder addSignupView() {
        views.add(signupView, signupView.getViewName());
        return this;
    }

    /**
     * Return this AppBuilder which views contains a LoginView.
     * @return this AppBuilder
     */
    public AppBuilder addLoginView() {
        views.add(loginView, loginView.getViewName());
        return this;
    }

    /**
     * Return this AppBuilder which views contains a SearchView.
     * @return this AppBuilder
     */
    public AppBuilder addSearchView() {
        views.add(searchView, searchView.getViewName());
        return this;
    }

    /**
     * Return this AppBuilder which views contains a DisplayEventView.
     * @return this AppBuilder
     */
    public AppBuilder addDisplayEventView() {
        views.add(eventListView, eventListView.getViewName());
        return this;
    }

    /**
     * Return this AppBuilder which views contains a DisplayAllEventView.
     * @return this AppBuilder
     */
    public AppBuilder addDisplayAllEventListsView() {
        views.add(displayAllEventListsView, displayAllEventListsView.getViewName());
        return this;
    }

    /**
     * Return this AppBuilder which views contains a DisplaySearchResultsView.
     * @return this AppBuilder
     */
    public AppBuilder addDisplaySearchResultsView() {
        views.add(displaySearchResultsView, displaySearchResultsView.getViewName());
        return this;
    }

    /**
     * Return this AppBuilder which views contains a DisplayNotificationView.
     * @return this AppBuilder
     */
    public AppBuilder addDisplayNotificationView() {
        views.add(displayNotificationsView, displayNotificationsView.getViewName());
        return this;
    }

    /**
     * Initialize Signup use case and return this AppBuilder.
     * @return this AppBuilder
     */
    public AppBuilder addSignupUseCase() {
        final SignupPresenter signupOutputBoundary =
                new SignupPresenter(viewManagerModel, signupViewModel, loginViewModel);
        final SignupInteractor signupInteractor = new SignupInteractor(fileUserDataAccessObject, signupOutputBoundary);
        final SignupController signupController = new SignupController(signupInteractor);
        signupView.setSignupController(signupController);
        return this;
    }

    /**
     * Initialize Login use case and return this AppBuilder.
     * @return this AppBuilder
     */
    public AppBuilder addLoginUseCase() {
        final LoginOutputBoundary loginOutputBoundary =
                new LoginPresenter(viewManagerModel, loginViewModel, searchViewModel);
        final LoginInputBoundary loginInteractor =
                new LoginInteractor(fileUserDataAccessObject, loginOutputBoundary, sessionDao);
        final LoginController loginController = new LoginController(loginInteractor);
        loginView.setLoginController(loginController);
        return this;
    }

    /**
     * Initialize Search use case and return this AppBuilder.
     * @return this AppBuilder
     */
    public AppBuilder addSearchUseCase() {
        final SearchEventOutputBoundary searchPresenter =
                new SearchEventPresenter(searchViewModel, viewManagerModel, resultViewModel);
        final SearchEventInputBoundary searchInteractor = new SearchEventInteractor(searchDao, searchPresenter);
        final SearchEventController searchController = new SearchEventController(searchInteractor);
        searchView.setController(searchController);
        return this;
    }

    /**
     * Initialize Logout use case and return this AppBuilder.
     * @return this AppBuilder
     */
    public AppBuilder addLogoutUseCase() {
        final LogoutPresenter logoutPresenter = new LogoutPresenter(viewManagerModel, loginViewModel, searchViewModel);
        final LogoutInteractor logoutInteractor =
                new LogoutInteractor(sessionDao, fileUserDataAccessObject, logoutPresenter);
        final LogoutController logoutController = new LogoutController(logoutInteractor);
        searchView.setLogoutController(logoutController);
        return this;
    }

    /**
     * Initialize Sort use case and return this AppBuilder.
     * @return this AppBuilder
     */
    public AppBuilder addSortUseCase() {
        final SortEventsPresenter sortPresenter = new SortEventsPresenter(resultViewModel);
        final SortEventsInteractor sortInteractor = new SortEventsInteractor(sortPresenter);
        final SortEventsController sortEventsController = new SortEventsController(sortInteractor);
        displaySearchResultsView.setSortEventsController(sortEventsController);
        return this;
    }

    /**
     * Initialize DisplayEvent use case and return this AppBuilder.
     * @return this AppBuilder
     */
    public AppBuilder addDisplayEventUseCase() {
        final DisplayEventPresenter eventPresenter = new DisplayEventPresenter(eventViewModel, viewManagerModel);
        final DisplayEventInteractor eventInteractor = new DisplayEventInteractor(eventPresenter, sessionDao);
        final DisplayEventController eventController = new DisplayEventController(eventInteractor);
        displaySearchResultsView.setDisplayEventController(eventController);
        eventListView.setDisplayEventController(eventController);
        return this;
    }

    /**
     * Initialize CreateEventList use case and return this AppBuilder.
     * @return this AppBuilder
     */
    public AppBuilder addCreateEventListUseCase() {
        final CreateEventListPresenter createEventListPresenter =
                new CreateEventListPresenter(createEventListViewModel);
        final CreateEventListInteractor createEventListInteractor =
                new CreateEventListInteractor(sessionDao, createEventListPresenter);
        final CreateEventListController createEventListController =
                new CreateEventListController(createEventListInteractor);
        displayAllEventListsView.setCreateEventListController(createEventListController);
        return this;
    }

    /**
     * Initialize DisplayEventList use case and return this AppBuilder.
     * @return this AppBuilder
     */
    public AppBuilder addDisplayEventListUseCase() {
        final DisplayEventListOutputBoundary displayEventListPresenter =
                new DisplayEventListPresenter(displayEventListViewModel, viewManagerModel);
        final DisplayEventListInputBoundary displayEventListInteractor =
                new DisplayEventListInteractor(sessionDao, displayEventListPresenter);
        final DisplayEventListController displayEventListController =
                new DisplayEventListController(displayEventListInteractor);
        eventListView.setDisplayEventListController(displayEventListController);
        displayAllEventListsView.setDisplayEventListController(displayEventListController);
        return this;
    }

    /**
     * Initialize DeleteEventList use case and return this AppBuilder.
     * @return this AppBuilder
     */
    public AppBuilder addDeleteEventListUseCase() {
        final DeleteEventListPresenter deleteEventListPresenter =
                new DeleteEventListPresenter(deleteEventListViewModel, createEventListViewModel);
        final DeleteEventListInteractor deleteEventListInteractor =
                new DeleteEventListInteractor(sessionDao, deleteEventListPresenter);
        final DeleteEventListController deleteEventListController =
                new DeleteEventListController(deleteEventListInteractor);
        displayAllEventListsView.setDeleteEventListController(deleteEventListController);
        return this;
    }

    /**
     * Initialize DisplayEventLists use case and return this AppBuilder.
     * @return this AppBuilder
     */
    public AppBuilder addDisplayEventListsUseCase() {
        final DisplayEventListsPresenter displayEventListsPresenter =
                new DisplayEventListsPresenter(viewManagerModel, createEventListViewModel);
        final DisplayEventListsInteractor displayEventListsInteractor =
                new DisplayEventListsInteractor(sessionDao, displayEventListsPresenter);
        final DisplayEventListsController displayEventListsController =
                new DisplayEventListsController(displayEventListsInteractor);
        searchView.setDisplayEventListsController(displayEventListsController);
        return this;
    }

    /**
     * Initialize SaveEventToList use case and return this AppBuilder.
     * @return this AppBuilder
     */
    public AppBuilder addSaveEventToListUseCase() {
        final SaveEventToListPresenter savePresenter = new SaveEventToListPresenter(saveEventToListViewModel);
        final SaveEventToListInteractor saveInteractor = new SaveEventToListInteractor(sessionDao, savePresenter);
        final SaveEventToListController saveEventToListController = new SaveEventToListController(saveInteractor);
        eventView.setSaveEventToListController(saveEventToListController);
        return this;
    }

    /**
     * Initialize DisplayNotification use case and return this AppBuilder.
     * @return this AppBuilder
     */
    public AppBuilder addDisplayNotificationUseCase() {
        final DisplayNotificationsOutputBoundary displayNotificationsPresenter =
                new DisplayNotificationsPresenter(displayNotificationsViewModel);
        final DisplayNotificationsInputBoundary displayNotificationsInteractor =
                new DisplayNotificationsInteractor(sessionDao, displayNotificationsPresenter);
        final DisplayNotificationsController displayNotificationsController =
                new DisplayNotificationsController(displayNotificationsInteractor);
        loginView.setDisplayNotificationsController(displayNotificationsController);
        return this;
    }

    /**
     * Initialize AttendEvent use case and return this AppBuilder.
     * @return this AppBuilder
     */
    public AppBuilder addAttendEventUseCase() {
        final AttendEventPresenter attendPresenter = new AttendEventPresenter(eventViewModel, viewManagerModel);
        final AttendEventInteractor attendInteractor = new AttendEventInteractor(sessionDao, attendPresenter);
        final AttendEventController attendEventController = new AttendEventController(attendInteractor);
        eventView.setAttendEventController(attendEventController);
        return this;
    }

    /**
     * Initialize RemoveEventFromList use case and return this AppBuilder.
     * @return this AppBuilder
     */
    public AppBuilder addRemoveEventFromListUseCase() {
        final RemoveEventFromListPresenter removePresenter =
                new RemoveEventFromListPresenter(removeEventFromListViewModel);
        final RemoveEventFromListInteractor removeInteractor =
                new RemoveEventFromListInteractor(sessionDao, removePresenter);
        final RemoveEventFromListController removeEventFromListController =
                new RemoveEventFromListController(removeInteractor);
        eventListView.setRemoveEventFromListController(removeEventFromListController);
        return this;
    }

    /**
     * Combine all part and return a JFrame.
     * @return a JFrame which contains views and use cases added
     */
    public JFrame build() {
        application.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        application.setPreferredSize(new Dimension(AppConstants.DEFAULT_WIDTH_SIZE, AppConstants.DEFAULT_HEIGHT_SIZE));

        application.add(views);

        // Window listener
        application.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                final User currentUser = sessionDao.getCurrentUser();
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

        return application;
    }
}
