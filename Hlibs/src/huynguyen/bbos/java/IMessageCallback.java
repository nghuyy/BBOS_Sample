package huynguyen.bbos.java;

import net.rim.device.api.io.messaging.Destination;
import net.rim.device.api.io.messaging.Message;

public interface IMessageCallback {
    void onMessage(String mess);
}
