package app;

import data_access.DBDataAccessObject;
import interface_adapter.ViewManagerModel;
import interface_adapter.display_event.DisplayEventController;
import interface_adapter.display_event.DisplayEventPresenter;
import interface_adapter.display_event.DisplayEventViewModel;
import interface_adapter.display_search_results.DisplaySearchResultsViewModel;
import interface_adapter.search_event.SearchEventController;
import interface_adapter.search_event.SearchEventPresenter;
import interface_adapter.search_event.SearchEventViewModel;
import interface_adapter.sort_events.SortEventsController;
import interface_adapter.sort_events.SortEventsPresenter;
import use_case.display_event.DisplayEventInteractor;
import use_case.search_event.SearchEventDataAccessInterface;
import use_case.search_event.SearchEventInteractor;
import use_case.sort_events.SortEventsInteractor;
import view.EventView;
import view.SearchResultView;
import view.SearchView;
import view.ViewManager;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        JFrame application = new JFrame("Event Search Application");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        SearchEventViewModel searchViewModel = new SearchEventViewModel();
        SearchEventDataAccessInterface dao = new DBDataAccessObject();
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        DisplaySearchResultsViewModel resultViewModel = new DisplaySearchResultsViewModel();
        DisplayEventViewModel eventViewModel = new DisplayEventViewModel();

        CardLayout layout = new CardLayout();
        JPanel views = new JPanel(layout);
        ViewManager viewManager = new ViewManager(views, layout, viewManagerModel);
        application.add(views);

        SearchEventPresenter searchPresenter = new SearchEventPresenter(searchViewModel, viewManagerModel, resultViewModel);
        SearchEventInteractor searchInteractor = new SearchEventInteractor(dao, searchPresenter);
        SearchEventController searchController = new SearchEventController(searchInteractor);

        SearchView searchView = new SearchView(searchViewModel, searchController, viewManagerModel);
        views.add(searchView, searchViewModel.getViewName());

        SortEventsPresenter sortPresenter = new SortEventsPresenter(resultViewModel);
        SortEventsInteractor sortInteractor = new SortEventsInteractor(sortPresenter);
        SortEventsController sortEventsController = new SortEventsController(sortInteractor);

        DisplayEventPresenter eventPresenter = new DisplayEventPresenter(eventViewModel, viewManagerModel);
        DisplayEventInteractor eventInteractor = new DisplayEventInteractor(eventPresenter);
        DisplayEventController eventController = new DisplayEventController(eventInteractor);

        SearchResultView resultView = new SearchResultView(resultViewModel, sortEventsController, eventController, viewManagerModel);
        views.add(resultView, resultViewModel.getViewName());

        // We do NOT add this to 'views' because it is a JDialog (floating window), not a JPanel.
        // The popup view listens to the ViewModel and reveals itself when data arrives.
        EventView eventPopup = new EventView(application, eventViewModel);

        viewManagerModel.setState(searchViewModel.getViewName());
        viewManagerModel.firePropertyChanged();

        application.pack();
        application.setVisible(true);
    }
}