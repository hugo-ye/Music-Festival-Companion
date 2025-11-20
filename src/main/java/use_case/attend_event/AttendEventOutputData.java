package use_case.attend_event;

import entity.Event;
import entity.User;

public class AttendEventOutputData {
    String username;
    String eventName;
    String added;
    String cantAdd;

    public AttendEventOutputData(User user, Event event ){
        username = user.getUsername();
        eventName = event.getName();
    }

    public String getUsername(){
        return username;
    }
    public String getEventName(){
        return eventName;
    }

    public String getAdded() {
        return added;
    }

    public String getCantAdd() {
        return cantAdd;
    }
}
