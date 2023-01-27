public class Event {
    /*
    * This class contains all the data of the given event.
    * Type - Birth / Death / Monitor
    * Timestamp - the timestamp at which the event will be executed
    * className - X / Y ( 2 classes for now )
    */
    String type;
    double timestamp;
    String className;

    public Event(String givenType, double givenTime, String givenName){
        this.type = givenType;
        this.timestamp = givenTime;
        this.className = givenName;
    }

}
