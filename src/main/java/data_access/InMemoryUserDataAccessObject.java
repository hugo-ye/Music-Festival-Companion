package data_access;

import entity.Event;
import entity.EventList;
import entity.User;
import use_case.attend_event.AttendEventDataAccessInterface;
import use_case.display_notifications.DisplayNotificationsDataAccessInterface;
import use_case.login.LoginSessionDataAccessInterface;
import use_case.logout.LogoutSessionDataAccessInterface;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class InMemoryUserDataAccessObject implements LoginSessionDataAccessInterface, LogoutSessionDataAccessInterface,
        AttendEventDataAccessInterface, DisplayNotificationsDataAccessInterface {

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

    public void saveEvent(Event event){
        currentUser.getMasterList().addEvent(event);
    }

    public boolean alreadyAttends(Event event){
        return currentUser.getMasterList().getEvents().contains(event);
    }

    @Override
    public List<Event> getNotificationBasedOnData(LocalDate localDate) {
        return currentUser.getMasterList().getAllEvents();
    }
}
