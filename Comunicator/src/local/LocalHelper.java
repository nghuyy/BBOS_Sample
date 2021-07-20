/*
 * LocalHelper.java
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

import net.rim.device.api.ui.UiApplication;



public final class LocalHelper extends UiApplication
{
    /**
     * Creates a new LocalHelper object
     */
    LocalHelper()
    {
        pushScreen(new MenuMainScreen());
    }


    /**
     * Entry point for the application
     */
    public static void main(String[] args)
    {
        // Create a new instance of the application and make the currently
        // running thread the application's event dispatch thread.
        LocalHelper theApp = new LocalHelper();
        theApp.enterEventDispatcher();
    }
}
