package use_case.sort_events;

public interface SortEventsInputBoundary {
    /**
     * Executes the Sort Events use case.
     * @param sortEventsInputData the input data
     */
    void execute(SortEventsInputData sortEventsInputData);
}
