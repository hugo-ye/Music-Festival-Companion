package interface_adapter.login;

import interface_adapter.ViewManagerModel;
import interface_adapter.display_notifications.DisplayNotificationsController;
import interface_adapter.search_event.SearchEventState;
import interface_adapter.search_event.SearchEventViewModel;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;

import java.time.LocalDate;

public class LoginPresenter implements LoginOutputBoundary {
    private final LoginViewModel loginViewModel;
    private final ViewManagerModel viewManagerModel;
    private final SearchEventViewModel searchEventViewModel;
    private final DisplayNotificationsController displayNotificationsController;

    public LoginPresenter(ViewManagerModel viewManagerModel, LoginViewModel loginViewModel ,
                          SearchEventViewModel searchEventViewModel,
                          DisplayNotificationsController displayNotificationsController) {
        this.loginViewModel = loginViewModel;
        this.viewManagerModel = viewManagerModel;
        this.searchEventViewModel = searchEventViewModel;
        this.displayNotificationsController = displayNotificationsController;
    }

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

        // calling notification view
        displayNotificationsController.execute(LocalDate.now());

    }
    public void prepareFailView(String error) {
        LoginState loginState = loginViewModel.getState();
        loginState.setErrorMessage(error);
        loginViewModel.setState(loginState);

        loginViewModel.firePropertyChanged();
    }

}
