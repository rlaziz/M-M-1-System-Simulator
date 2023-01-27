import java.util.ArrayList;
import java.util.Objects;

public class Simulator {

    public static void main(String[] args){
        double maxTime = Double.parseDouble(args[0]);
        double requestArrivalRateX = Double.parseDouble(args[1]);
        double requestArrivalRateY = Double.parseDouble(args[2]);
        double averageServiceTimeX = Double.parseDouble(args[3]);
        double averageServiceTimeY = Double.parseDouble(args[4]);
        String policy = args[5];
        double probability = Double.parseDouble(args[6]);

        Simulator simulator = new Simulator(policy, requestArrivalRateX, requestArrivalRateY, averageServiceTimeX, averageServiceTimeY, probability);

        simulator.simulate(maxTime);


        double UTIL = simulator.busyTime / maxTime;
        double QAVG = ((double) simulator.totalQueueLengths) / ((double) simulator.monitors);
        double WAVG = ((double) simulator.waitingQueueLengths) / ((double) simulator.monitors);

        double TRESPX = simulator.responseTimeX / simulator.completedX;
        double TWAITX = computeAverage(simulator.waitTimesX);
        double TRESPY = simulator.responseTimeY / simulator.completedY;
        double TWAITY = computeAverage(simulator.waitTimesY);


        double RUNS = 1.0 + ((double)simulator.amountOfLoops) / ((double) simulator.completedX + simulator.completedY);

        System.out.println("UTIL: " + UTIL);
        System.out.println("QAVG: " + QAVG);
        System.out.println("WAVG: " + WAVG);
        System.out.println("TRESP X: " + TRESPX);
        System.out.println("TWAIT X: " + TWAITX);
        System.out.println("TRESP Y: " + TRESPY);
        System.out.println("TWAIT Y: " + TWAITY);
        System.out.println("RUNS: " + RUNS);
    }

    private static double computeAverage(ArrayList<Double> arr){
        double average = 0.0;
        int n = 0;
        for(Double a : arr){
            average += a;
            if (a == 0){
                continue;
            }
            n ++;
        }
        return average / n;
    }
    public void simulate(double maxTime){
        Event birthX = new Event("BIRTH", 0.0,"X");
        Event birthY = new Event("BIRTH", 0.0,"Y");

        Event monitorX = new Event("MONITOR",0.0,"X");
        Event monitorY = new Event("MONITOR",0.0,"Y");

        eventQueue.push(birthX);
        eventQueue.push(birthY);
        eventQueue.push(monitorX);
        eventQueue.push(monitorY);

        while (this.currentTime < maxTime){
            Event event = eventQueue.pop();
            this.currentTime = event.timestamp;

            switch (event.type) {
                case "BIRTH" -> this.birth(event);
                case "MONITOR" -> this.monitor(event);
                case "DEATH" -> this.death(event);
            }
        }
    }

    private void birth(Event event){
        Request generated;
        if (Objects.equals(event.className, "X")){
            generated = this.rgX.generateNewRequest(event.timestamp);
            this.waitTimesX.add((double) 0);
        }else {
            generated = this.rgY.generateNewRequest(event.timestamp);
            this.waitTimesY.add((double) 0);
        }

        generated.printArrivalInformation();
        server.add(generated);


        if(server.length() == 1){
            generated.startTime = event.timestamp;
            generated.finishTime = event.timestamp + generated.serviceTime;

            generated.printStartInformation();

            server.setBusy();

            Event newDeathEvent = new Event("DEATH", generated.finishTime, generated.className);
            this.eventQueue.push(newDeathEvent);
        }

        Event newBirthEvent;

        if (Objects.equals(event.className, "X")){
            newBirthEvent = new Event("BIRTH",(event.timestamp + Exponent.exponent(this.requestLambdaX)), "X");
        }else {
            newBirthEvent = new Event("BIRTH",(event.timestamp + Exponent.exponent(this.requestLambdaY)), "Y");
        }
        this.eventQueue.push(newBirthEvent);
    }
    private boolean willLoop(){
        return Math.random() > this.probability;
    }
    private void death(Event event){
        Request finished = server.remove();

        this.busyTime += finished.serviceTime;
        if (Objects.equals(event.className, "X")){
            double newWaitTime = this.waitTimesX.get(finished.index) + (finished.finishTime - finished.arrivalTime);
            this.waitTimesX.set(finished.index,newWaitTime);
        }else {
            double newWaitTime = this.waitTimesY.get(finished.index) + (finished.finishTime - finished.arrivalTime);
            this.waitTimesY.set(finished.index,newWaitTime);
        }
        if(this.willLoop()){
            finished.printLoopInformation();
            Request looped;
            if (Objects.equals(finished.className, "X")){
                looped = this.rgX.generateNewLoopedRequest(finished.arrivalTime, finished);
            }else {
                looped = this.rgY.generateNewLoopedRequest(finished.arrivalTime, finished);
            }
            server.add(looped);
        }else {
            finished.printDeathInformation();
            if (Objects.equals(finished.className, "X")){
                this.responseTimeX += (finished.finishTime - finished.firstArrivalTime);
                this.completedX += 1;
            }else {
                this.responseTimeY += (finished.finishTime - finished.firstArrivalTime);
                this.completedY += 1;
            }
            this.amountOfLoops += finished.timesLooped;
        }
        if (server.length() != 0){
            Request nextToStart = server.peek();
            nextToStart.startTime = event.timestamp;
            nextToStart.finishTime = event.timestamp + nextToStart.serviceTime;

            nextToStart.printStartInformation();

            server.setBusy();

            Event newDeathEvent = new Event("DEATH", nextToStart.finishTime, nextToStart.className);
            this.eventQueue.push(newDeathEvent);
        }


    }
    private void monitor(Event event){
        this.monitors ++;

        this.totalQueueLengths += server.length();
        int isBusy = server.isBusy() ? 1 : 0;
        this.waitingQueueLengths += ( server.length() - isBusy );

        double newMonitorEventTime = Objects.equals(event.className, "X") ? Exponent.exponent(requestLambdaX) : Exponent.exponent(requestLambdaY);
        newMonitorEventTime += event.timestamp;
        Event newMonitorEvent = new Event("MONITOR", newMonitorEventTime, event.className);
        this.eventQueue.push(newMonitorEvent);
    }

    private EventQueue eventQueue;
    private Server server;
    private RequestGenerator rgX;
    private RequestGenerator rgY;

    private int totalQueueLengths;
    private int waitingQueueLengths;

    private double currentTime;
    private double requestLambdaX;
    private double requestLambdaY;
    private double probability;

    private double busyTime;
    private double responseTimeX;
    private double responseTimeY;

    private ArrayList<Double> waitTimesX;
    private ArrayList<Double> waitTimesY;

    private int amountOfLoops;
    private int completedX;
    private int completedY;
    private int monitors;

    public Simulator(String policy, double rarX, double rarY, double astX, double astY, double p){
        this.requestLambdaX = rarX;
        this.requestLambdaY = rarY;
        this.probability = p;

        this.currentTime = 0.0;
        this.busyTime = 0.0;
        this.responseTimeX = 0.0;
        this.responseTimeY = 0.0;

        this.amountOfLoops = 0;
        this.monitors = 0;
        this.completedY = 0;
        this.completedX = 0;

        this.eventQueue = new EventQueue();
        this.server = new Server(policy);
        this.rgX = new RequestGenerator("X",astX);
        this.rgY = new RequestGenerator("Y",astY);
        this.waitTimesX = new ArrayList<>();
        this.waitTimesY = new ArrayList<>();

        this.totalQueueLengths = 0;
        this.waitingQueueLengths = 0;
    }



}
