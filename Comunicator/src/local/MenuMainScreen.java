/*
 * MenuMainScreen.java
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

import com.rim.samples.device.communicationapidemo.ui.FullWidthButton;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.FocusChangeListener;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.container.MainScreen;



public final class MenuMainScreen extends MainScreen
{
    private LabelField _instructions;


    /**
     * Creates a new MenuMainScreen object
     */
    public MenuMainScreen()
    {
        setTitle("Communication API Local Helper");

        final CommunicationController controller = new CommunicationController();

        // Initialize UI components        
        FullWidthButton sendFireForgetButton = new FullWidthButton("Send IPC Messages (Fire-and-Forget)");
        sendFireForgetButton.setChangeListener(new FieldChangeListener()
        {
            /**
             * @see FieldChangeListener#fieldChanged(Field, int)
             */
            public void fieldChanged(Field field, int context)
            {
                UiApplication.getUiApplication().pushScreen(new SendFireForgetScreen(controller));
            }
        });

        sendFireForgetButton.setFocusListener(new FocusChangeListener()
        {
            /**
             * @see FocusChangeListener#focusChanged(Field, int)
             */
            public void focusChanged(Field field, int eventType)
            {
                setInstructions("Send local messages to applications on the device using Fire-and-Forget destination.");
            }
        });

        _instructions = new LabelField("", Field.NON_FOCUSABLE);

        // Add components to screen
        add(sendFireForgetButton);
        add(new SeparatorField());
        add(_instructions);
    }


    /**
     * @see MainScreen#onSavePrompt()
     */
    public boolean onSavePrompt()
    {
        return true;
    }


    private void setInstructions(String instruction)
    {
        _instructions.setText(instruction);
    }
}
