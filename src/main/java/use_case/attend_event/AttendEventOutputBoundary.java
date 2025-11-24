package use_case.attend_event;

public interface AttendEventOutputBoundary {
    void prepareSuccessView(AttendEventOutputData attendEventOutputData);
    void prepareFailView(String error_message);
}
