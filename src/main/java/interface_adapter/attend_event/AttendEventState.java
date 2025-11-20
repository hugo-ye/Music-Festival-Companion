package interface_adapter.attend_event;

public class AttendEventState {
    boolean successfullyAdded;
    String username;
    String eventName;
    String message;

    public void setUsername(String username){
        this.username = username;
    }
    public void setEventName(String eventName){
        this.eventName = eventName;
    }
    public void setMessage(String message){
        this.message = message;
    }
    public void setSuccessfullyAdded(boolean successfullyAdded){
        this.successfullyAdded = successfullyAdded;
    }

    public String getUsername(){
        return username;
    }

    public String getEventName(){
        return eventName;
    }
    public String getMessage(){
        return message;
    }
}
