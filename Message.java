import java.io.Serializable;

public class Message implements Serializable {
    public enum Type { ACTION, STATE, CHAT, RESULT }
    public Type type;
    public Object data;

    public Message(Type type, Object data) {
        this.type = type;
        this.data = data;
    }
}
