/**
 * CommunicationController.java
 *
 * Copyright © 1998-2011 Research In Motion Ltd.
 * 
 * Note: For the sake of simplicity, this sample application may not leverage
 * resource bundles and resource strings.  However, it is STRONGLY recommended
 * that application developers make use of the localization features available
 * within the BlackBerry development platform to ensure a seamless application
 * experience across a variety of languages and geographies.  For more information
 * on localizing your application, please refer to the BlackBerry Java Development
 * Environment Development Guide associated with this release.
 */

package com.rim.samples.device.communicationapidemo.local;

import com.rim.samples.device.communicationapidemo.util.Utils;

import net.rim.device.api.io.URI;
import net.rim.device.api.io.messaging.Context;
import net.rim.device.api.io.messaging.DestinationFactory;
import net.rim.device.api.io.messaging.FireAndForgetDestination;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.Dialog;



/**
 * Contains methods which demonstrate Communication API use cases
 */
public final class CommunicationController
{
    // URI of the simple echo server which responds to http get request sent from
    // the application by sending back requested resource (xml, text, json, etc).
    public static String ECHO_SERVER_URI;

    public static int TIMEOUT; // Response timeout
    private static UiApplication _app;

    private Context _context;


    /**
     * Creates a new CommunicationController object
     */
    public CommunicationController()
    {
        _context = new Context("MyContext");
        _app = UiApplication.getUiApplication();
        try
        {
            // Read settings from config.xml
            ECHO_SERVER_URI = Utils.getEchoServerUri();
            TIMEOUT = Utils.getTimeout();
        }
        catch(Exception e)
        {
            alertDialog(e.toString());
        }
    }


    /**
     * Sends message to the provided URI using FireAndForget destination
     * 
     * @param uriSenderStr Sender destination URI
     */
    public void sendFireForget(String uriSenderStr)
    {
        FireAndForgetDestination fireForgetDest = null;

        try
        {
            fireForgetDest = (FireAndForgetDestination) DestinationFactory.getSenderDestination(_context.getName(), URI.create(uriSenderStr));

            if(fireForgetDest == null)
            {
                fireForgetDest = DestinationFactory.createFireAndForgetDestination(_context, URI.create(uriSenderStr));
            }

            int msgId = fireForgetDest.sendNoResponse();

            alertDialog("Message [id:" + msgId + "] has been sent!");
        }
        catch(Exception e)
        {
            alertDialog(e.toString());
        }
    }


    /**
     * Displays a pop-up dialog to the user with a given message
     * 
     * @param message The text to display
     */
    public static void alertDialog(final String message)
    {
        _app.invokeLater(new Runnable()
        {
            public void run()
            {
                Dialog.alert(message);
            }
        });
    }
}
