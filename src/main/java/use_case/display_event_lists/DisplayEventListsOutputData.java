package use_case.display_event_lists;

import entity.EventList;
import interface_adapter.create_event_list.EventListSummary;

import java.util.List;
import java.util.stream.Collectors;

public class DisplayEventListsOutputData {
    private final List<EventList> eventLists;

    public DisplayEventListsOutputData(List<EventList> eventLists) {
        this.eventLists = eventLists;
    }

    public List<EventListSummary> getEventLists() {
        return eventLists.stream()
                .map(eventList -> new EventListSummary(eventList.getId(), eventList.getName()))
                .collect(Collectors.toList());
    }
}
