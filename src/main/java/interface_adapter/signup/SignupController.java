package interface_adapter.signup;

import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInputData;

public class SignupController {
    private final SignupInputBoundary signupInteractor;

    public SignupController(SignupInputBoundary signupInteractor) {
        this.signupInteractor = signupInteractor;
    }

    public void execute(String username, String password, String repeatPassword){
        final SignupInputData inputData = new SignupInputData(username, password, repeatPassword);

        signupInteractor.execute(inputData);
    }
}
