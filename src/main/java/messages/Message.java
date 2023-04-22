package messages;

public abstract class Message {
    String content;
    long sentTimestamp;
    String sender;

    public Message(String content, String sender) {
        this.content = content;
        this.sentTimestamp = System.currentTimeMillis();
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public long getSentTimestamp() {
        return sentTimestamp;
    }

    public String getSender() {
        return sender;
    }
}
