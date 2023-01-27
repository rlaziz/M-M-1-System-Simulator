public class RequestGenerator {
    /*
    * This class contains all the information about a request of a given class. This class also has to generate all the requests for the given class
    * ClassName - X / Y
    * CurrentIndex - an index that a newly generated request will have
    * Lambda - parameter for Exponent.java that will be used to generate random service time
    */
    String className;
    int currentIndex;
    double lambda;

    public RequestGenerator(String name, double averageServiceTime){
        this.className = name;
        this.lambda = 1 / averageServiceTime;
        this.currentIndex = 0;
    }

    public Request generateNewRequest(double arrivalTime){
        double serviceTime = Exponent.exponent(this.lambda);
        Request newRequest = new Request(this.className, arrivalTime, serviceTime, this.currentIndex, 0);

        currentIndex++;
        return newRequest;
    }
    public Request generateNewLoopedRequest(double arrivalTime, Request toLoop){
        double serviceTime = Exponent.exponent(this.lambda);
        Request newRequest = new Request(this.className, arrivalTime, serviceTime , toLoop.index, toLoop.timesLooped + 1);
        newRequest.firstArrivalTime = toLoop.firstArrivalTime;
        return newRequest;
    }
}
