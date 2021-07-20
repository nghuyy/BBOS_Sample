/*
 * MenuReceiveScreen.java
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
import net.rim.device.api.ui.FocusChangeListener;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.container.MainScreen;



public final class MenuReceiveScreen extends MainScreen
{
    private LabelField _instructions;

    /**
     * Creates a new MenuReceiveScreen object
     */
    public MenuReceiveScreen(final MenuManager menuManager)
    {
        setTitle("Receive Messages");

        // Initialize UI components
        _instructions = new LabelField("", Field.NON_FOCUSABLE);

        FullWidthButton testButton = new FullWidthButton("BES Push");
        testButton.setChangeListener(new FieldChangeListener()
        {
            /**
             * @see FieldChangeListener#fieldChanged(Field, int)
             */
            public void fieldChanged(Field field, int context)
            {
                menuManager.showReceivePushScreen();
            }
        });

        testButton.setFocusListener(new FocusChangeListener()
        {
            /**
             * @see FocusChangeListener#focusChanged(Field, int)
             */
            public void focusChanged(Field field, int eventType)
            {
                setInstructions("Receive messages sent from external push server through BES.");

            }
        });

        FullWidthButton ipcButton = new FullWidthButton("IPC/Local Receiver");
        ipcButton.setChangeListener(new FieldChangeListener()
        {
            /**
             * @see FieldChangeListener#fieldChanged(Field, int)
             */
            public void fieldChanged(Field field, int context)
            {
                menuManager.showReceiveIPCScreen();
            }
        });

        ipcButton.setFocusListener(new FocusChangeListener()
        {
            /**
             * @see FocusChangeListener#focusChanged(Field, int)
             */
            public void focusChanged(Field field, int eventType)
            {
                setInstructions("Receive messages sent from other BlackBerry Smartphone device applications using IPC(Inter-Process Communication).");
            }
        });

        FullWidthButton bpsButton = new FullWidthButton("BPS Push");
        bpsButton.setChangeListener(new FieldChangeListener()
        {
            /**
             * @see FieldChangeListener#fieldChanged(Field, int)
             */
            public void fieldChanged(Field field, int context)
            {
                menuManager.showReceiveBPSScreen();
            }
        });

        bpsButton.setFocusListener(new FocusChangeListener()
        {
            /**
             * @see FocusChangeListener#focusChanged(Field, int)
             */
            public void focusChanged(Field field, int eventType)
            {
                setInstructions("Test BPS subscription process. For receiving pushes, this test has to be run on a BlackBerry Smartphone device (will not work on a BlackBerry Smartphone simulator).");
            }
        });

        // Add components to screen
        add(testButton);
        add(ipcButton);
        add(bpsButton);
        add(new SeparatorField());
        add(_instructions);
    }


    /**
     * @see MainScreen#onSavePrompt()
     */
    public boolean onSavePrompt()
    {
        // Suppress the save dialog
        return true;
    }


    public void setInstructions(String instruction)
    {
        _instructions.setText(instruction);
    }

}
