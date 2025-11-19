package use_case.sort_events;

import entity.Event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortEventsInteractor implements SortEventsInputBoundary{
    private final SortEventsOutputBoundary presenter;

    public SortEventsInteractor(SortEventsOutputBoundary presenter){
        this.presenter = presenter;
    }

    @Override
    public void execute(SortEventsInputData input) {
        final List<Event> events = new ArrayList<>(input.getEvents()); // mutable copy, show in testing
        final SortEventsMethod sortMethod = input.getSortMethod();
        final SortEventsOrder sortOrder = input.getSortOrder();
        
        Comparator<Event> comparator;
        
        if (sortMethod == SortEventsMethod.BY_NAME){
            comparator = Event.BY_NAME;
        }
        else if (sortMethod == SortEventsMethod.BY_DATE){
            comparator = Event.BY_DATE;
        }
        else if (sortMethod == SortEventsMethod.BY_VENUE){
            comparator = Event.BY_VENUE;
        }
        else if (sortMethod == SortEventsMethod.BY_TICKET_PRICE){
            comparator = Event.BY_PRICE;
        }
        else {
            throw new IllegalArgumentException("Invalid sort method");
        }
        
        events.sort(comparator);
        
        if (sortOrder == SortEventsOrder.DESCENDING) {
            Collections.reverse(events);
        }
        
        SortEventsOutputData outputData = new SortEventsOutputData(events, sortMethod, sortOrder);

        presenter.prepareSuccessView(outputData);
    }
}
