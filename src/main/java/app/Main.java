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

        ViewManagerModel viewManager = new ViewManagerModel();

        DisplaySearchResultsViewModel resutState = new DisplaySearchResultsViewModel();

        SearchEventPresenter presenter = new SearchEventPresenter(searchViewModel, viewManager, resutState);
        SearchEventInteractor interactor = new SearchEventInteractor(dao, presenter);
        SearchEventController controller = new SearchEventController(interactor);

        CardLayout layout = new CardLayout();
        JPanel views = new JPanel(layout);

        SearchView searchView = new SearchView(searchViewModel, controller, viewManager);
        views.add(searchView, searchViewModel.getViewName());

        SortEventsPresenter sortPresenter = new SortEventsPresenter(resutState);
        SortEventsInteractor sortInteractor = new SortEventsInteractor(sortPresenter);
        SortEventsController sortEventsController = new SortEventsController(sortInteractor);

        DisplayEventViewModel eventViewModel = new DisplayEventViewModel();
        DisplayEventPresenter eventPresenter = new DisplayEventPresenter(eventViewModel, viewManager);
        DisplayEventInteractor eventInteractor = new DisplayEventInteractor(eventPresenter);
        DisplayEventController eventController = new DisplayEventController(eventInteractor);

        SearchResultView resultView = new SearchResultView(resutState, sortEventsController, eventController, viewManager);
        views.add(resultView, resutState.getViewName());

        EventView eventView = new EventView(eventViewModel, viewManager);
        views.add(eventView, eventViewModel.getViewName());


        ViewManager manager = new ViewManager(views, layout, viewManager);

        application.add(views);

        viewManager.firePropertyChanged("view");


        application.pack();
        application.setVisible(true);
    }
}
