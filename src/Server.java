import java.util.Objects;

public class Server {
    /*
    * This class is the main Server, it is responsible for processing requests.
    * This class picks a request with currently the highest priority ( varies with policy )
    */

    private class Node {
        private Node left;
        private Node right;
        private Request request;

        Node(Request request){
            this.request = request;
        }
    }

    private Node root;
    private int length;
    private String policy;

    private int timesEnqueued;
    private int completed;
    private boolean isBusy;
    public Server(String policy){
        this.policy = policy;
        this.root = null;
        this.length = 0;
        this.timesEnqueued = 0;
        this.completed = 0;
    }
    public void add(Request request){
        if(Objects.equals(this.policy, "FIFO")){
            this.addFIFO(request);
        }else{
            this.addSJN(request);
        }
        this.length++;
        this.timesEnqueued++;
    }
    private void addFIFO(Request request){

        Node newNode = new Node(request);
        if (this.length() == 0){
            this.root = newNode;
            return;
        }

        Node last = this.root;

        while (last.right != null){
            last = last.right;
        }
        last.right = newNode;
    }
    private void addSJN(Request request){
        Node newNode = new Node(request);
        if (this.length() == 0){
            this.root = newNode;
            return;
        }
        Node current = this.root;

        double currentST;
        double newST = request.serviceTime;

        while (true){
            currentST = current.request.serviceTime;
            if (newST < currentST){
                if (current.left != null){
                    current = current.left;
                }else {
                    current.left = newNode;
                    break;
                }
            }else {
                if (current.right != null){
                    current = current.right;
                }else {
                    current.right = newNode;
                    break;
                }
            }
        }
    }
    public Request remove(){
        Request removed;
        if (Objects.equals(this.policy, "FIFO")){
            removed = this.removeFIFO();
        }else {
            removed = this.removeSJN();
        }

        this.length--;
        this.isBusy = false;
        this.completed++;
        return removed;
    }
    private Request removeFIFO(){
        Node removed = this.root;
        this.root = this.root.right;
        return removed.request;
    }
    private Request removeSJN(){
        Node current = this.root;
        Node parent = null;

        while (current.left != null){
            parent = current;
            current = current.left;
        }

        if (parent != null){
            if ( current.right != null){
                parent.left = current.right;
            }else {
                parent.left = null;
            }
        }else {
            this.root = this.root.right;
        }

        return current.request;
    }
    public Request peek(){
        if (Objects.equals(this.policy, "FIFO")){
            return this.peekFIFO();
        }else {
            return this.peekSJN();
        }
    }
    private Request peekFIFO(){
        return this.root.request;
    }
    private Request peekSJN(){
        Node next = this.root;
        while (next.left != null){
            next = next.left;
        }
        return next.request;
    }

    public int length(){
        return this.length;
    }

    public void setBusy(){
        this.isBusy = true;
    }
    public int getTimesEnqueued(){
        return this.timesEnqueued;
    }
    public int getCompleted(){
        return this.completed;
    }
    public boolean isBusy(){
        return this.isBusy;
    }
}