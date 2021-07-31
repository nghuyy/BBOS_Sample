package huynguyen.bbos;

import huynguyen.bbos.java.IMessageCallback;
import net.rim.device.api.io.messaging.*;

public class Messages implements MessageListener {
    private final IMessageCallback success;

    public Messages(IMessageCallback iMessageCallback){
        this.success = iMessageCallback;
    }
    public void onMessage(Destination destination, Message message) {
        String stringPayload = ((ByteMessage) message).getStringPayload();
        if(stringPayload != null)
        {
            success.onMessage(stringPayload);
        }
    }

    public void onMessageFailed(Destination destination, MessageFailureException e) {
        success.onMessage(" ");
        e.printStackTrace();
    }

    public void onMessageCancelled(Destination destination, int i) {

    }
}
