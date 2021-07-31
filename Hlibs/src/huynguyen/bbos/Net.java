package huynguyen.bbos;

import huynguyen.bbos.java.IMessageCallback;
import net.rim.device.api.io.URI;
import net.rim.device.api.io.messaging.*;

public class Net {
    private final Context context;

    public Net() {
        this.context = new Context("AppContext");
    }

    public void GET(String url, IMessageCallback _success) {
        try {
            URI uri = URI.create(url);
            Messages messages = new Messages(_success);
            SenderDestination senderDestination = DestinationFactory.getSenderDestination(context.getName(),uri);
            if(senderDestination == null) {
                senderDestination =DestinationFactory.createNonBlockingSenderDestination(context, uri, messages);
            }
            messages.setDestination(senderDestination);
            ByteMessage message = senderDestination.createByteMessage();
            ((HttpMessage) message).setMethod(HttpMessage.GET);
            //message.setPriority(2);
            ((NonBlockingSenderDestination)senderDestination).send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     *
     * @param url
     * @param payload "user=a&host=b"
     * @param _success
     */
    public void POST(String url,String payload, IMessageCallback _success) {
        try {
            URI uri = URI.create(url);
            Messages messages = new Messages(_success);
            SenderDestination senderDestination = DestinationFactory.getSenderDestination(context.getName(),uri);
            if(senderDestination == null) {
                senderDestination =DestinationFactory.createNonBlockingSenderDestination(context, uri, messages);
            }
            messages.setDestination(senderDestination);
            ByteMessage message = senderDestination.createByteMessage();
            ((HttpMessage) message).setMethod(HttpMessage.POST);
            message.setStringPayload(payload);
           // message.setPriority(2);
           //message.setTransportHeader("Ath", "1234");
            message.setTransportHeader("Content-Type", "application/x-www-form-urlencoded");
            ((NonBlockingSenderDestination)senderDestination).send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
