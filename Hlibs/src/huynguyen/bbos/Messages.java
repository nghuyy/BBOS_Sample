package huynguyen.bbos;

import huynguyen.bbos.java.IMessageCallback;
import net.rim.device.api.io.messaging.*;

public class Messages implements MessageListener {
    private final IMessageCallback success;
    private SenderDestination destination;

    public Messages(IMessageCallback iMessageCallback){
        this.success = iMessageCallback;
    }
    public void onMessage(Destination destination, Message message) {
        String stringPayload = ((ByteMessage) message).getStringPayload();
        if(stringPayload != null)
        {
            success.onMessage(stringPayload);
            if(destination != null)destination.release();
        }
    }
    public void setDestination(SenderDestination destination){
        this.destination = destination;
    }
    public void onMessageFailed(Destination destination, MessageFailureException e) {
        success.onMessage(" ");
        if(destination != null)destination.release();
        e.printStackTrace();
    }

    public void onMessageCancelled(Destination destination, int i) {

    }
}
