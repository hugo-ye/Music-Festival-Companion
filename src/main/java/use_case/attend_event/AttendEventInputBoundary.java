package use_case.attend_event;

public interface AttendEventInputBoundary {
    /**
     * Executes the attend events use case.
     * @param inputData the input data
     */
    void execute(AttendEventInputData inputData);
}
