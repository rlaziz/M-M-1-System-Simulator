public class Request {
    /*
    * This class contains all the data of a given request
    * ClassName - X / Y
    * Index - Index of a Request
    * ArrivalTime - timestamp of the arrival or return of the request IN the System
    * FirstArrivalTime - timestamp of the first arrival of the request IN the System
    * StartTime - timestamp at which request has started its execution
    * FinishTime - timestamp at which request has finished its execution
    * ServiceTime - the amount of time request needs in order to be executed
    * TimesLooped - number of times the request has returned into the system
    */
    String className;
    int index;
    double arrivalTime;
    double firstArrivalTime;
    double startTime;
    double finishTime;

    double serviceTime;

    int timesLooped;

    public Request(String className, double arrivalTime, double serviceTime,int index, int timesLooped){
        this.className = className;
        this.arrivalTime = arrivalTime;
        this.firstArrivalTime = arrivalTime;

        this.startTime = -1;
        this.finishTime = -1;

        this.serviceTime = serviceTime;
        this.index = index;
        this.timesLooped = timesLooped;
    }
    public void printArrivalInformation(){
        System.out.println(this.className + index + " ARR: " + arrivalTime + " LEN: " + serviceTime);
    }
    public void printStartInformation(){
        System.out.println(this.className + this.index + " START: " + startTime);
    }
    public void printLoopInformation(){
        System.out.println(this.className + this.index + " LOOP: " + finishTime);
    }
    public void printDeathInformation(){
        System.out.println(this.className + this.index + " DONE: " + finishTime);
    }

}
