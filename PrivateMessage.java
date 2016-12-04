package seriaf.poo.structs;

/**
 *
 * @author rhobincu
 */
public class PrivateMessage extends Message {

    private static final long serialVersionUID = 1L;

    private final String mRecipient;

    public PrivateMessage(String recipient, String sender, String content) {
        super(sender, content);
        mRecipient = recipient;
    }

    @Override
    public String toString() {
        return "(priv) " + super.toString();
    }

    public String getRecipient() {
        return mRecipient;
    }
}
