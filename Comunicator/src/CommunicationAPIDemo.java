/**
 * CommunicationAPIDemo.java
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

import com.rim.samples.device.communicationapidemo.view.MenuManager;

import net.rim.device.api.ui.UiApplication;



/**
 * Sample application to demonstrate Communication API. The Communication and
 * Message Processing Libraries facilitate the development of applications that
 * communicate with web services as well as other BlackBerry applications.
 */
public final class CommunicationAPIDemo extends UiApplication
{
    /**
     * Entry point for the application
     */
    public static void main(String[] args)
    {
        // Create a new instance of the application and make the currently
        // running thread the application's event dispatch thread.
        CommunicationAPIDemo theApp = new CommunicationAPIDemo();
        theApp.enterEventDispatcher();
    }


    /**
     * Creates a new CommunicationAPIDemo object
     */
    public CommunicationAPIDemo()
    {
        new MenuManager().showMenuMainScreen();
    }
}
