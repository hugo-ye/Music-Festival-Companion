package interface_adapter.sort_events;

import java.util.List;

import entity.Event;
import use_case.sort_events.SortEventsCriteria;
import use_case.sort_events.SortEventsInputBoundary;
import use_case.sort_events.SortEventsInputData;
import use_case.sort_events.SortEventsOrder;

/**
 * Controller for the Sort Events Use Case.
 */
public class SortEventsController {

    private final SortEventsInputBoundary sortEventsInteractor;

    public SortEventsController(SortEventsInputBoundary sortEventsInteractor) {
        this.sortEventsInteractor = sortEventsInteractor;
    }

    /**
     * Executes the Sort Events Use Case.
     *
     * @param unsortedList       The list to be sorted.
     * @param sortEventsCriteria The criteria to sort by.
     * @param sortEventsOrder    The direction of the sort.
     */
    public void execute(List<Event> unsortedList, SortEventsCriteria sortEventsCriteria, SortEventsOrder sortEventsOrder) {
        final SortEventsInputData inputData = new SortEventsInputData(unsortedList, sortEventsCriteria, sortEventsOrder);
        sortEventsInteractor.execute(inputData);
    }
}