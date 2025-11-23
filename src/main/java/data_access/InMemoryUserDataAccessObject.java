package data_access;

import entity.Event;
import entity.EventList;
import entity.User;
import use_case.login.LoginSessionDataAccessInterface;
import use_case.logout.LogoutSessionDataAccessInterface;
import use_case.save_event_to_list.SaveEventToListDataAccessInterface;

public class InMemoryUserDataAccessObject implements LoginSessionDataAccessInterface, LogoutSessionDataAccessInterface, SaveEventToListDataAccessInterface {

    private User currentUser;

    public User getCurrentUser() {
        return currentUser;
    }
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void clearCurrentUser() {
        this.currentUser = null;
    }

    @Override
    public void save(Event event, EventList eventList) {
        currentUser = getCurrentUser();
        int currentEventListIndex = currentUser.getEventLists().indexOf(eventList);
        // for situation of non-exist: may not happen, since user cannot select eventList that is not created
        if(currentEventListIndex == -1){
            System.out.println("no eventList currently");
            return;
        }
        EventList currentEventList = currentUser.getEventLists().get(currentEventListIndex);
        currentEventList.addEvent(event);
        // I omit the situation that eventList that will add the same event

        // for persistent storage, may need discuss later, currently I only deal with inMemory
    }
}
