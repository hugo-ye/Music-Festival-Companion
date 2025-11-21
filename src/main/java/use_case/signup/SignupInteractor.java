package use_case.signup;

import entity.User;

public class SignupInteractor implements SignupInputBoundary{
    public final SignupDataAccessInterface signUpDataAccessObject;
    private final SignupOutputBoundary signupPresenter;
    public SignupInteractor(SignupDataAccessInterface signupDataAccessInterface, SignupOutputBoundary signupOutputBoundary) {
        this.signUpDataAccessObject = signupDataAccessInterface;
        this.signupPresenter = signupOutputBoundary;

    }
    public void execute(SignupInputData signupInputData){
        if (signUpDataAccessObject.existsByUsername(signupInputData.getUsername())){
            signupPresenter.prepareFailView("User already exists.");
        }
        else if (!signupInputData.getPassword().equals(signupInputData.getRepeatPassword())){
            signupPresenter.prepareFailView("Passwords do not match.");
        }
        else if (signupInputData.getUsername().isEmpty() || signupInputData.getPassword().isEmpty()){
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
