package messages;

import java.util.Objects;

public abstract class Message {
    String content;
    long sentTimestamp;
    String sender;

    public Message(String content, String sender) {
        this.content = content;
        this.sentTimestamp = System.currentTimeMillis();
        this.sender = sender;
    }


    public Message(String content, String sender, long timestamp) {
        this.content = content;
        this.sentTimestamp = timestamp;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return sentTimestamp == message.sentTimestamp && content.equals(message.content) && sender.equals(message.sender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, sentTimestamp, sender);
    }
}
