/**
 * ResponseCallback.java
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

package com.rim.samples.device.communicationapidemo;

import net.rim.device.api.io.messaging.Message;
import net.rim.device.api.ui.UiApplication;



public abstract class ResponseCallback
{
    private UiApplication _app;


    public ResponseCallback()
    {
        _app = UiApplication.getUiApplication();
    }

    public abstract void onResponse(Message message);


    public abstract void onTimeout(int timeout);


    /**
     * Invokes onResponse() method for ResponseCallback object on application
     * thread to update UI component depending on results of response message
     * 
     * @param message The text to display
     */
    public void updateUI(final Message message)
    {
        _app.invokeLater(new Runnable()
        {
            public void run()
            {
                onResponse(message);
            }
        });
    }


    /**
     * Invokes onTimeout() method for ResponseCallback object on application
     * thread For example, displays a pop-up dialog to the user with a given
     * message
     * 
     * @param timeout Timeout, in seconds
     */
    public void timeoutDialog(final int timeout)
    {
        _app.invokeLater(new Runnable()
        {
            public void run()
            {
                onTimeout(timeout);
            }
        });
    }
}
