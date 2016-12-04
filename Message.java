package seriaf.poo.structs;

import java.io.Serializable;

/**
 *
 * @author professor
 */
public class Message implements Serializable{

    private static final long serialVersionUID = 1L;

    private String mSender;
    private String mContent;

    public Message(String sender, String content) {
        mSender = sender;
        mContent = content;
    }

    @Override
    public String toString() {
        return mSender + ": " + mContent;
    }

    public String getSender() { // returneaza senderul.... pretty obvious
        return this.mSender;
    }
}
