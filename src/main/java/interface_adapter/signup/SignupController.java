package interface_adapter.signup;

import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInputData;

/**
 * Controller class for signup use case
 */
public class SignupController {
    private final SignupInputBoundary signupInteractor;

    public SignupController(SignupInputBoundary signupInteractor) {
        this.signupInteractor = signupInteractor;
    }

    /**
     * Executes the signup process with the provided username and password.
     * <p>
     *     This method creates a {@link SignupInputData} object and calls the interactor with it.
     * </p>
     * @param username the username entered by the user.
     * @param password the password entered by the user.
     * @param repeatPassword the repeated password entered by the user for confirmation.
     */
    public void execute(String username, String password, String repeatPassword){
        final SignupInputData inputData = new SignupInputData(username, password, repeatPassword);

        signupInteractor.execute(inputData);
    }
}
