public class EventQueue {
    /*
    * EventQueue - A Data-Structure that will change its behaviour based on Policy
    * FIFO(First In First Out) policy - Same as Queue Data Structure
    * SJN(Shorted Job Next) policy - Same as Binary Tree Data Structure
    * Type - Birt
    */
    private static class Node {
        private Node left;
        private Node right;
        private final Event event;

        Node(Event event){
            this.left = null;
            this.right = null;
            this.event = event;
        }
    }

    private Node root;

    EventQueue(){
        this.root = null;
    }

    public void push(Event event){
        if(this.root == null){
            this.root = new Node(event);
        }else{
            Node current = this.root;
            double newTimestamp = event.timestamp;
            while(true){
                double currentTimestamp = current.event.timestamp;

                if(currentTimestamp > newTimestamp){
                    if(current.left == null){
                        current.left = new Node(event);
                        return;
                    }else{
                        current = current.left;
                    }
                }else {
                    if(current.right == null){
                        current.right = new Node(event);
                        return;
                    }else{
                        current = current.right;
                    }
                }
            }
        }
    }
    public Event pop(){
        if(this.root == null){
            return null;
        }else{
            Node current = this.root;
            Node parent = null;
            while(current.left != null){
                parent = current;
                current = current.left;
            }
            if(parent == null){
                this.root = current.right;
            }else{
                parent.left = current.right;
            }
            return current.event;
        }
    }
}
