package interface_adapter.sort_events;

import entity.Event;
import use_case.sort_events.*;

import java.util.List;

/**
 * Controller for the sort_events use case.
 */
public class SortEventsController {
    private final SortEventsInputBoundary sortEventsInteractor;

    public SortEventsController(SortEventsInputBoundary sortEventsInteractor) {
        this.sortEventsInteractor = sortEventsInteractor;
    }

    /**
     * Executes the sort events use case.
     * <p>
     *     This method creates an a {@link SortEventsInputData} object from the provided parameters. It then leaves the
     *     sorting to the interactor.
     * </p>
     * @param events the list of events to be sorted.
     * @param sortEventsCriteria the criteria used to sort the events e.g(name, price)
     * @param sortEventsOrder the order in which to sort the events (ascending or descending)
     */
    public void execute(List<Event> events, SortEventsCriteria sortEventsCriteria, SortEventsOrder sortEventsOrder) {
        final SortEventsInputData inputData = new SortEventsInputData(events, sortEventsCriteria, sortEventsOrder);
        sortEventsInteractor.execute(inputData);

    }
}
