package data_access;

import java.util.List;

import entity.Event;
import entity.EventList;
import entity.User;
import use_case.attend_event.AttendEventDataAccessInterface;
import use_case.create_event_list.CreateEventListDataAccessInterface;
import use_case.delete_event_list.DeleteEventListDataAccessInterface;
import use_case.display_event.DisplayEventDataAccessInterface;
import use_case.display_event_lists.DisplayEventListsDataAccessInterface;
import use_case.display_notifications.DisplayNotificationsDataAccessInterface;
import use_case.login.LoginSessionDataAccessInterface;
import use_case.logout.LogoutSessionDataAccessInterface;
import use_case.remove_event_from_list.RemoveEventFromListDataAccessInterface;
import use_case.save_event_to_list.SaveEventToListDataAccessInterface;

public class InMemoryUserDataAccessObject implements
        LoginSessionDataAccessInterface,
        LogoutSessionDataAccessInterface,
        AttendEventDataAccessInterface,
        CreateEventListDataAccessInterface,
        DeleteEventListDataAccessInterface,
        DisplayEventListsDataAccessInterface,
        SaveEventToListDataAccessInterface,
        RemoveEventFromListDataAccessInterface,
        DisplayNotificationsDataAccessInterface,
        DisplayEventDataAccessInterface {

    private User currentUser;

    /**
     * A method that returns the current user.
     * @return the current user.
     */
    @Override
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * A method that sets the current user.
     * @param currentUser the user to set as the current user.
     */
    @Override
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * A method that clear up the InMemoryDataAccessObject.
     */
    @Override
    public void clearCurrentUser() {
        this.currentUser = null;
    }

    /**
     * A method that save target event to target eventList.
     *
     * @param event     the event to be added.
     * @param eventList the list where we add the event.
     */
    @Override
    public void saveEventToList(Event event, EventList eventList) {
        currentUser = getCurrentUser();
        final int currentEventListIndex = currentUser.getLists().indexOf(eventList);
        // for situation of non-exist: may not happen, since user cannot select eventList that is not created
        if (currentEventListIndex != -1) {
            final EventList currentEventList = currentUser.getLists().get(currentEventListIndex);
            currentEventList.addEvent(event);
        }
        // I omit the situation that eventList that will add the same event
    }

    /**
     * A method that adds event to MasterList of user.
     *
     * @param event the event to add
     */
    @Override
    public void saveEventToMasterList(Event event) {
        currentUser.getMasterList().addEvent(event);
    }

    /**
     * A method return a boolean value of whether event is already in MasterList.
     *
     * @param event the event to check
     * @return whether event is already in MasterList.
     */
    @Override
    public boolean alreadyAttends(Event event) {
        return currentUser.getMasterList().getEvents().contains(event);
    }

    /**
     * A method get MasterList of user.
     *
     * @return the MasterList of user.
     */
    @Override
    public List<Event> getMasterListEvents() {
        List<Event> result = null;
        if (currentUser != null) {
            result = currentUser.getMasterList().getEvents();
        }
        return result;
    }

    /**
     * A method check whether a list of event is existing by checking list name.
     *
     * @param listName name of the target list.
     * @return whether the list is existing.
     */
    @Override
    public boolean existsByName(String listName) {
        boolean result = false;
        final User user = getCurrentUser();
        if (user != null) {
            for (EventList list : user.getLists()) {
                if (list.getName().equalsIgnoreCase(listName)) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * A method that create a eventList into user.
     *
     * @param eventList the created eventList.
     */
    @Override
    public void createEventList(EventList eventList) {
        final User user = getCurrentUser();
        if (!createEventListHelper(eventList, user)) {
            user.getLists().add(eventList);
        }
    }

    /**
     * Helper method for createEventList.
     * @param eventList the eventList to be created.
     * @param user the user that will create the eventList.
     * @return whether the eventList is created successfully.
     */
    private static boolean createEventListHelper(EventList eventList, User user) {
        boolean result = user == null;
        if (eventList == null) {
            result = true;
        }
        if (user.getLists().contains(eventList)) {
            result = true;
        }
        return result;
    }

    /**
     * To determine whether the MasterList or eventList is existing by using id of the user.
     * @param listId the id of target eventList/MasterList
     * @return whether the MasterList/EventList existing
     */
    @Override
    public boolean existsById(String listId) {
        final User current = getCurrentUser();
        boolean result = false;
        result = existsByIdHelper(listId, current);
        return result;
    }

    /**
     * Criteria of whether the eventList/MasterList is existing.
     * @param listId id of eventList.
     * @param current user that contain target eventList.
     * @return whether the target eventList is existing.
     */
    private static boolean existsByIdHelper(String listId, User current) {
        boolean result = false;
        if (current == null) {
            result = false;
        }
        if (current.getMasterList().getId().equals(listId)) {
            result = true;
        }
        for (EventList list : current.getLists()) {
            if (list.getId().equals(listId)) {
                result = true;
            }
        }
        return result;
    }

    /**
     * To check whether the MasterList's id is same as provided.
     * @param listId target id of MasterList
     * @return whether the id of MasterList has same id as provided.
     */
    @Override
    public boolean isMasterList(String listId) {
        boolean result = false;
        final User user = getCurrentUser();
        if (user != null) {
            result = user.getMasterList().getId().equals(listId);
        }
        return result;
    }

    /**
     * Delete a eventList by given listId.
     * @param listId target eventList id.
     */
    @Override
    public void deleteById(String listId) {
        final User user = getCurrentUser();
        if (user != null) {
            user.removeListById(listId);
        }
    }

    /**
     * A method that get all existing eventLists in user.
     * @return the eventLists of user.
     */
    @Override
    public List<EventList> getEventLists() {
        List<EventList> result = null;
        final User user = getCurrentUser();
        if (user != null) {
            result = user.getLists();
        }
        return result;
    }

    /**
     * A method that will remove the removedEvent from targetEventList.
     * @param removedEvent target event that user want to remove.
     * @param targetEventList target event that user plan to remove event from.
     */
    @Override
    public void removeEventFromList(Event removedEvent, EventList targetEventList) {
        final int currentEventListIndex = currentUser.getLists().indexOf(targetEventList);
        // for situation of non-exist: may not happen, since user cannot select eventList that is not created
        if (currentEventListIndex != -1) {
            final EventList currentEventList = currentUser.getLists().get(currentEventListIndex);
            currentEventList.removeEvent(removedEvent);
        }
    }
}
