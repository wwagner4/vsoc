package vsoc.behaviour;

public class Message {
    
    private String content = null;
    
    private double direction = 0.0;

    public Message() {
        super();
    }

    public Message(String content, double direction) {
        super();
        this.content = content;
        this.direction = direction;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getDirection() {
        return this.direction;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

}
