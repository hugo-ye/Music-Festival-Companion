package interface_adapter.logout;

import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.search_event.SearchEventState;
import interface_adapter.search_event.SearchEventViewModel;
import use_case.logout.LogoutOutputBoundary;
import use_case.logout.LogoutOutputData;

/**
 * Presenter class for the Logout use case.
 * The class implements {@link LogoutOutputBoundary} and is responsible for updating the viewModel after the logout
 * use case has been executed.
 */
public class LogoutPresenter implements LogoutOutputBoundary {
    private final LoginViewModel loginViewModel;
    private final ViewManagerModel viewManagerModel;
    private final SearchEventViewModel searchEventViewModel;

    public LogoutPresenter(ViewManagerModel viewManagerModel, LoginViewModel loginViewModel,
                           SearchEventViewModel searchEventViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.loginViewModel = loginViewModel;
        this.searchEventViewModel = searchEventViewModel;
    }

    /**
     * Prepares the view for a successful logout.
     *
     * @param outputData the output data from the logout interactor, which is empty.
     */
    @Override
    public void prepareSuccessView(LogoutOutputData outputData) {
        // clear the username field in search
        searchEventViewModel.setState(new SearchEventState());
        searchEventViewModel.firePropertyChanged();
        viewManagerModel.setState(loginViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Prepares the view for a failed logout.
     * Logout is assumed to always succeed, therefore this method's body is empty.
     * @param errorMessage The error message describing why logout failed.
     */
    @Override
    public void prepareFailView(String errorMessage) {
        // We assume logout can not really fail, so we don't need to do anything here'
    }
}
