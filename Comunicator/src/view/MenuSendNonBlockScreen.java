/*
 * MenuSendNonBlockScreen.java
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

package com.rim.samples.device.communicationapidemo.view;

import com.rim.samples.device.communicationapidemo.ui.FullWidthButton;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.container.MainScreen;



public final class MenuSendNonBlockScreen extends MainScreen
{
    /**
     * Create a new MenuSendNonBlockScreen object
     */
    public MenuSendNonBlockScreen(final MenuManager menuManager)
    {
        setTitle("Non-Blocking");

        FullWidthButton atomBtn = new FullWidthButton("Atom");
        FullWidthButton jsonBtn = new FullWidthButton("JSON");
        FullWidthButton rssBtn = new FullWidthButton("RSS");
        FullWidthButton soapBtn = new FullWidthButton("SOAP");
        FullWidthButton xmlBtn = new FullWidthButton("XML");

        atomBtn.setChangeListener(new FieldChangeListener()
        {
            /**
             * @see FieldChangeListener#fieldChanged(Field, int)
             */
            public void fieldChanged(Field field, int context)
            {
                menuManager.showSendNonBlockAtomScreen();
            }
        });

        jsonBtn.setChangeListener(new FieldChangeListener()
        {
            /**
             * @see FieldChangeListener#fieldChanged(Field, int)
             */
            public void fieldChanged(Field field, int context)
            {
                menuManager.showSendNonBlockJsonScreen();
            }
        });

        rssBtn.setChangeListener(new FieldChangeListener()
        {
            /**
             * @see FieldChangeListener#fieldChanged(Field, int)
             */
            public void fieldChanged(Field field, int context)
            {
                menuManager.showSendNonBlockRssScreen();
            }
        });

        soapBtn.setChangeListener(new FieldChangeListener()
        {
            /**
             * @see FieldChangeListener#fieldChanged(Field, int)
             */
            public void fieldChanged(Field field, int context)
            {
                menuManager.showSendNonBlockSoapScreen();
            }
        });

        xmlBtn.setChangeListener(new FieldChangeListener()
        {
            /**
             * @see FieldChangeListener#fieldChanged(Field, int)
             */
            public void fieldChanged(Field field, int context)
            {
                menuManager.showSendNonBlockXmlScreen();
            }
        });

        LabelField instructions = new LabelField("Request and parse data from echo server.", Field.NON_FOCUSABLE);

        // Add UI components to screen
        add(atomBtn);
        add(jsonBtn);
        add(rssBtn);
        add(soapBtn);
        add(xmlBtn);
        add(new SeparatorField());
        add(instructions);
    }


    /**
     * @see MainScreen#onSavePrompt()
     */
    public boolean onSavePrompt()
    {
        // Suppress the save dialog
        return true;
    }
}
