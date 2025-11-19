package interface_adapter.sort_events;

import entity.Event;
import use_case.sort_events.*;

import java.util.List;

public class SortEventsController {
    private final SortEventsInputBoundary sortEventsInteractor;

    public SortEventsController(SortEventsInputBoundary sortEventsInteractor) {
        this.sortEventsInteractor = sortEventsInteractor;
    }

    public void execute(List<Event> events, SortEventsMethod sortMethod, SortEventsOrder sortOrder) {
        final SortEventsInputData inputData = new SortEventsInputData(events, sortMethod, sortOrder);
        sortEventsInteractor.execute(inputData);

    }
}
