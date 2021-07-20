/**
 * MessageListDemoViewer.java
 * 
 * Copyright � 1998-2011 Research In Motion Ltd.
 * 
 * Note: For the sake of simplicity, this sample application may not leverage
 * resource bundles and resource strings.  However, it is STRONGLY recommended
 * that application developers make use of the localization features available
 * within the BlackBerry development platform to ensure a seamless application
 * experience across a variety of languages and geographies.  For more information
 * on localizing your application, please refer to the BlackBerry Java Development
 * Environment Development Guide associated with this release.
 */

package huynguyen.bbos.MessageList;

import java.util.Date;

import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.container.MainScreen;


/**
 * A MainScreen subclass for displaying messages
 */
public final class DemoMessageScreen extends MainScreen
{
    /**
     * Constructs a screen to view a message
     * 
     * @param demoMessage The message to display
     */
    public DemoMessageScreen(DemoMessage demoMessage)
    {
        LabelField title = new LabelField("Demo Message Screen");
        setTitle(title);

        add(new LabelField("From: " + demoMessage.getContact()));
        add(new LabelField("Subject: " + demoMessage.getSubject()));
        add(new LabelField("Received: " + new Date(demoMessage.getTimestamp())));
        add(new LabelField("Replied? : " + (demoMessage.hasReplied() ? "YES" : "NO")));
        add(new SeparatorField());

        if(demoMessage.getMessage() != null)
        {
            add(new LabelField(demoMessage.getMessage()));
        }
    }
}
