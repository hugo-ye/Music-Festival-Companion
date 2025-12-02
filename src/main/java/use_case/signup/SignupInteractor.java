package use_case.signup;

import entity.User;

/**
 * Interactor class for the signup use case.
 * The class checks:
 * whether the user already exists,
 * whether the provided password matched the repeated password,
 * and whether the username and password fields are non-empty
 * If all the checks fail, the interactor creates a new User object and saves it.
 */
public class SignupInteractor implements SignupInputBoundary {
    private final SignupDataAccessInterface signUpDataAccessObject;
    private final SignupOutputBoundary signupPresenter;

    public SignupInteractor(SignupDataAccessInterface signupDataAccessInterface,
                            SignupOutputBoundary signupOutputBoundary) {
        this.signUpDataAccessObject = signupDataAccessInterface;
        this.signupPresenter = signupOutputBoundary;

    }

    /**
     * Signup method similar as done in code example.
     * @param signupInputData inputData
     */
    public void execute(SignupInputData signupInputData) {
        if (signUpDataAccessObject.existsByUsername(signupInputData.getUsername())) {
            signupPresenter.prepareFailView("User already exists.");
        }
        else if (!signupInputData.getPassword().equals(signupInputData.getRepeatPassword())) {
            signupPresenter.prepareFailView("Passwords do not match.");
        }
        else if (signupInputData.getUsername().isEmpty() || signupInputData.getPassword().isEmpty()) {
            signupPresenter.prepareFailView("Please fill in a username and a password.");
        }
        else {
            final User user = new User(signupInputData.getUsername(), signupInputData.getPassword());
            signUpDataAccessObject.save(user);
            final SignupOutputData signupOutputData = new SignupOutputData(user.getUsername(), false);
            signupPresenter.prepareSuccessView(signupOutputData);
        }
    }
}
