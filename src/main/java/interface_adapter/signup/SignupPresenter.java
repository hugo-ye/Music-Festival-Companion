package interface_adapter.signup;

import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginViewModel; // Assuming this exists
import interface_adapter.login.LoginState;     // Assuming this exists
import use_case.signup.SignupOutputBoundary;
import use_case.signup.SignupOutputData;

/**
 *  The Presenter for the Signup use case
 * This class implements {@link SignupOutputBoundary} and is responsible for updating the appropriate viewModel
 * based on whether the signup passes or fails.
 *
 * If success, it updates the {@link LoginViewModel} with the new user's information and notifies the
 * {@link ViewManagerModel} to switch to the login view.
 * If failed it updates the {@link SignupViewModel} with an error message.
 */
public class SignupPresenter implements SignupOutputBoundary {

    private final SignupViewModel signupViewModel;
    private final LoginViewModel loginViewModel;
    private final ViewManagerModel viewManagerModel;

    public SignupPresenter(ViewManagerModel viewManagerModel,
                           SignupViewModel signupViewModel,
                           LoginViewModel loginViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.signupViewModel = signupViewModel;
        this.loginViewModel = loginViewModel;
    }

    /**
     * Prepares the success view after a successful signup operation.
     *
     * @param response the output data from the signup interactor containing the new User's information
     */
    @Override
    public void prepareSuccessView(SignupOutputData response) {

        final LoginState loginState = loginViewModel.getState();
        loginState.setUsername(response.getUsername());
        this.loginViewModel.setState(loginState);
        loginViewModel.firePropertyChanged();

        viewManagerModel.setState(loginViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Prepares the failure view after a failed signup operation.
     * @param error the error message describing why the signup failed.
     */
    @Override
    public void prepareFailView(String error) {
        SignupState signupState = signupViewModel.getState();
        signupState.setErrorMessage(error);
        signupViewModel.firePropertyChanged();
    }
}
