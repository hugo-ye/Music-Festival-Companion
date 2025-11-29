package use_case.sort_events;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import entity.Event;

/**
 * Interactor for the Sort Events Use Case.
 * Handles the logic for sorting events based on given criteria and order
 * and provides the sorted data to the presenter.
 */
public class SortEventsInteractor implements SortEventsInputBoundary {
    private final SortEventsOutputBoundary presenter;

    public SortEventsInteractor(SortEventsOutputBoundary presenter) {
        this.presenter = presenter;
    }

    @Override
    public void execute(SortEventsInputData input) {
        final List<Event> events = new ArrayList<>(input.getEvents());

        Comparator<Event> strategy = SortEventsStrategyFactory.create(input.getSortEventsCriteria());

        strategy = input.getSortEventsOrder().apply(strategy);

        events.sort(strategy);

        final SortEventsOutputData outputData = new SortEventsOutputData(
                events,
                input.getSortEventsCriteria(),
                input.getSortEventsOrder()
        );

        presenter.prepareSuccessView(outputData);
    }
}
