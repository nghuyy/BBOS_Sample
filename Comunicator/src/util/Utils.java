/**
 * Utils.java
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

package com.rim.samples.device.communicationapidemo.util;

import java.io.IOException;
import java.io.InputStream;

import net.rim.device.api.io.parser.xml.XMLHashtable;



/**
 * Contains useful utility methods
 */
public final class Utils
{
    private static final String CONFIG_FILE = "/src/config/config.xml";
    private static final int DEFAULT_TIMEOUT = 50;

    /**
     * Reads URI of echo server from config.xml
     */
    public static String getEchoServerUri() throws Exception
    {
        String uriStr = null;
        InputStream is = getResourceStream(CONFIG_FILE);
        XMLHashtable xmlht = new XMLHashtable(is, false, false);
        Object value = xmlht.get("/config/echo-server-uri"); // Path in xml config file

        if(value != null)
        {
            uriStr = value.toString();
        }

        return uriStr;
    }


    /**
     * Reads timeout value from config.xml
     */
    public static int getTimeout() throws Exception
    {
        int timeout = DEFAULT_TIMEOUT;
        InputStream is = getResourceStream(CONFIG_FILE);
        XMLHashtable xmlht = new XMLHashtable(is, false, false);
        Object value = xmlht.get("/config/timeout"); // Path in xml config file

        if(value != null)
        {
            timeout = Integer.parseInt(value.toString());
        }

        return timeout;
    }


    /**
     * Returns a Stream from the specified file
     */
    public static InputStream getResourceStream(String filename) throws IOException
    {
        return Utils.class.getResourceAsStream(filename);
    }


    /**
     * Creates local client URI to register on the device
     */
    public static String createLocalClientUri(String appName, String path)
    {
        if(appName == null || path == null)
        {
            throw new IllegalArgumentException("Device port/resource can not be null");
        }

        return "local://" + appName + path;
    }


    private Utils() // Prevent instantiation
    {
    }
}
