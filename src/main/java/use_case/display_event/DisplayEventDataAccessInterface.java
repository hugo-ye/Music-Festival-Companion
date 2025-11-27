package use_case.display_event;

import entity.EventList;

import java.util.List;

public interface DisplayEventDataAccessInterface {
    List<EventList> getEventLists();
}