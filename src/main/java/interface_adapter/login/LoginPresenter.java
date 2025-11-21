package interface_adapter.login;

import interface_adapter.ViewManagerModel;
import interface_adapter.search_event.SearchEventViewModel;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;

public class LoginPresenter implements LoginOutputBoundary {
    private final LoginViewModel loginViewModel;
    private final ViewManagerModel viewManagerModel;

    public LoginPresenter(ViewManagerModel viewManagerModel, LoginViewModel loginViewModel) {
        this.loginViewModel = loginViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    public void prepareSuccessView(LoginOutputData response) {
        LoginState loginState = loginViewModel.getState();
        loginState.setUsername(response.getUsername());
        this.loginViewModel.setState(loginState);
        this.loginViewModel.firePropertyChanged();

        // viewManagerModel.setState(SearchEventViewModel.getViewName());// TODO, merge with Amir branch so this works
        viewManagerModel.firePropertyChanged();


    }
    public void prepareFailView(String error) {
        LoginState loginState = loginViewModel.getState();
        loginState.setErrorMessage(error);
        loginViewModel.firePropertyChanged();
    }

}
