package use_case.sort_events;

import entity.Event;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SortEventsInteractor implements SortEventsInputBoundary {
    private final SortEventsOutputBoundary presenter;

    public SortEventsInteractor(SortEventsOutputBoundary presenter){
        this.presenter = presenter;
    }

    @Override
    public void execute(SortEventsInputData input) {
        List<Event> events = new ArrayList<>(input.getEvents());

        Comparator<Event> strategy = SortEventsStrategyFactory.create(input.getSortEventsCriteria());

        strategy = input.getSortEventsOrder().apply(strategy);

        events.sort(strategy);

        SortEventsOutputData outputData = new SortEventsOutputData(
                events,
                input.getSortEventsCriteria(),
                input.getSortEventsOrder()
        );

        presenter.prepareSuccessView(outputData);
    }
}