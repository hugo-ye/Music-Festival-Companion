package interface_adapter.logout;

import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.search_event.SearchEventState;
import interface_adapter.search_event.SearchEventViewModel;
import use_case.logout.LogoutOutputBoundary;
import use_case.logout.LogoutOutputData;

public class LogoutPresenter implements LogoutOutputBoundary {
    private final LoginViewModel loginViewModel;
    private final ViewManagerModel viewManagerModel;
    private final SearchEventViewModel searchEventViewModel;
    public LogoutPresenter(ViewManagerModel viewManagerModel, LoginViewModel loginViewModel, SearchEventViewModel searchEventViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.loginViewModel = loginViewModel;
        this.searchEventViewModel = searchEventViewModel;
    }


    @Override
    public void prepareSuccessView(LogoutOutputData outputData) {
        // clear the username field in search
        searchEventViewModel.setState(new SearchEventState());
        searchEventViewModel.firePropertyChanged();
        viewManagerModel.setState(loginViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        // We assume logout can not really fail, so we don't need to do anything here'
    }
}
