/*
 * FullWidthButton.java
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

package com.rim.samples.device.communicationapidemo.ui;

import net.rim.device.api.system.Display;
import net.rim.device.api.ui.component.ButtonField;



/**
 * A button which uses the full width of the screen
 */
public final class FullWidthButton extends ButtonField
{
    /**
     * Creates a new FullWidthButton object with provided label text
     * 
     * @param label Label text to display on the button
     */
    public FullWidthButton(String label)
    {
        this(label, ButtonField.CONSUME_CLICK | ButtonField.NEVER_DIRTY);
    }


    /**
     * Creates a new FullWidthButton object with provided label text and style
     * 
     * @param label Label text to display on the button
     * @param style Style for the button
     */
    public FullWidthButton(String label, long style)
    {
        super(label, style);
    }


    /**
     * * @see ButtonField#getPreferredWidth()
     */
    public int getPreferredWidth()
    {
        return Display.getWidth();
    }
}
