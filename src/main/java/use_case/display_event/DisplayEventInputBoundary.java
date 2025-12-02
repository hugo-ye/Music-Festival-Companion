package use_case.display_event;

public interface DisplayEventInputBoundary {
    /**
     * Extracts relevant data from an event and creates an outputData.
     *
     * @param input the input data containing the event to display.
     */
    void execute(DisplayEventInputData input);
}
