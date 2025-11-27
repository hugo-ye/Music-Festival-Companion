package interface_adapter.login;

import interface_adapter.ViewManagerModel;
import interface_adapter.search_event.SearchEventState;
import interface_adapter.search_event.SearchEventViewModel;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;

/**
 * Presenter for the Login use case
 * <p>
 *     The {@link LoginPresenter} translates the output data to updates the appropriate view models.
 * </p>
 */
public class LoginPresenter implements LoginOutputBoundary {
    private final LoginViewModel loginViewModel;
    private final ViewManagerModel viewManagerModel;
    private final SearchEventViewModel searchEventViewModel;

    public LoginPresenter(ViewManagerModel viewManagerModel, LoginViewModel loginViewModel , SearchEventViewModel searchEventViewModel) {
        this.loginViewModel = loginViewModel;
        this.viewManagerModel = viewManagerModel;
        this.searchEventViewModel = searchEventViewModel;
    }

    /**
     * Prepares the success view after a successful login attempt.
     * This method updates the LoginViewModel and SearchEventViewModel
     * @param response the output data containing the username of the logged in user.
     */
    public void prepareSuccessView(LoginOutputData response) {
        LoginState loginState = loginViewModel.getState();
        loginState.setUsername(response.getUsername());
        this.loginViewModel.setState(loginState);
        this.loginViewModel.firePropertyChanged();

        SearchEventState searchState = searchEventViewModel.getState();
        searchState.setUsername(response.getUsername());
        this.searchEventViewModel.setState(searchState);
        this.searchEventViewModel.firePropertyChanged();

        viewManagerModel.setState(searchEventViewModel.getViewName());
        viewManagerModel.firePropertyChanged();


    }

    /**
     * Prepares the fail view after an unsuccessful login attempt.
     * <p>
     *     This method sets an error message in the {@link LoginViewModel}.
     * </p>
     *
     * @param error the error message describing why the login attempt failed.
     */
    public void prepareFailView(String error) {
        LoginState loginState = loginViewModel.getState();
        loginState.setErrorMessage(error);
        loginViewModel.setState(loginState);

        loginViewModel.firePropertyChanged();
    }

}
