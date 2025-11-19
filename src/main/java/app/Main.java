package app;

import data_access.DBDataAccessObject;
import interface_adapter.search_event.SearchEventController;
import interface_adapter.search_event.SearchEventPresenter;
import interface_adapter.search_event.SearchEventViewModel;
import use_case.search_event.SearchEventDataAccessInterface;
import use_case.search_event.SearchEventInteractor;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        SearchEventDataAccessInterface dao = new DBDataAccessObject();
        SearchEventInteractor interactor = new SearchEventInteractor(dao, new SearchEventPresenter(new SearchEventViewModel()));
        SearchEventController controller = new SearchEventController(interactor);
        List genres = List.of();
        controller.execute("", "", "", "", "", "", genres);

    }
}
