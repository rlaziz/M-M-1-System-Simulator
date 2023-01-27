# M-M-1-System-Simulator

This is a simulator of M/M/1 System with 2 classes of requests.

In order to run a code, you have to execute this command in Terminal with the appropriate agruements

<hr>

***java Simulator*** *MAX_TIME, X_ARRIVAL_RATE, Y_ARRIVAL_RATE, X_AVERAGE_SERVICE_TIME, Y_AVERAGE_SERVICE_TIME, POLICY, PROBABILITY*

<hr>

**MAX_TIME**(Double) - the duration of the simulation in milliseconds

**X_ARRIVAL_RATE**(Double) - the arrival rate of requests of class X, i.e CLASS_X_ARRIVAL_RATE = 2 means that there are on average 2 requests from class X coming every second

**Y_ARRIVAL_RATE**(Double) - same as above, but for class Y

**X_AVERAGE_SERVICE_TIME**(Double) - average service time required for a request of class X

**Y_AVERAGE_SERVICE_TIME**(Double) - same as above, but for class Y

**POLICY**(String) - either FIFO or SJN, FIFO - First In First Out, same as Queue. SJN - Shortes Job Next, a request with shorter service time will be prioritized over the ones with requests with longer service time.

**PROBABILITY**(Double) - a probability of Request R leaving the system for good, i.e. Say PROBABILITY = 0.5, there is a 50% chance of a request leaving the system, and 50% chance of request coming back in the system with different service time.

<hr>

At the end of the execution of the Simulator, a few variables are printed

UTIL - The Utilization of the System

QAVG - Average amount of requests in the System Queue (including currently processed one)

WAVG - Average amouny of requests in the Wait Queue (excluding currently processed one)

TRESPX - Average response time for requests of class X

TWAITX - Average wait time for requests of class X

TRESPY - Average response time for requests of class Y

TWAITY - Average wait time for requests of class Y

<hr>
P.S.

Wait time - time between an arrival and the start of execution of a request

Response time - time between an arrival of a request and its departure from the system
