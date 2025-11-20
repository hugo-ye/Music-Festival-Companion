package use_case.attend_event;

import entity.Event;

public class AttendEventInputData {
    private final String username;
    private final Event event;

    public AttendEventInputData(String username, Event event){
        this.username = username;
        this.event = event;
    }

    public String getUsername(){
        return username;
    }

    public Event getEvent(){
        return event;
    }
}
