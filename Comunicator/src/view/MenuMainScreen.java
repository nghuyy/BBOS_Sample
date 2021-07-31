/*
 * MenuMainScreen.java
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

package com.rim.samples.device.communicationapidemo.view;

import com.rim.samples.device.communicationapidemo.ui.FullWidthButton;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.FocusChangeListener;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.container.MainScreen;


public final class MenuMainScreen extends MainScreen
{
    private LabelField _instructions;

    /**
     * Creates a new MenuMainScreen object
     */
    public MenuMainScreen(final MenuManager menuManger)
    {
        setTitle("Communication API Demo");

        // Initialize UI components
        _instructions = new LabelField("", Field.NON_FOCUSABLE);
        FullWidthButton sendMessagesButton = new FullWidthButton("Send Messages");
        sendMessagesButton.setChangeListener(new FieldChangeListener()
        {
            /**
             * @see FieldChangeListener#fieldChanged(Field, int)
             */
            public void fieldChanged(Field field, int context)
            {
                menuManger.showMenuSendScreen();
            }
        });

        sendMessagesButton.setFocusListener(new FocusChangeListener()
        {
            /**
             * @see FocusChangeListener#focusChanged(Field, int)
             */
            public void focusChanged(Field field, int eventType)
            {
                setInstructions("Test communication and message processing for various data protocols.");
            }
        });

        FullWidthButton receiveMessagesButton = new FullWidthButton("Receive Messages");
        receiveMessagesButton.setChangeListener(new FieldChangeListener()
        {
            /**
             * @see FieldChangeListener#fieldChanged(Field, int)
             */
            public void fieldChanged(Field field, int context)
            {
                menuManger.showMenuReceiveScreen();
            }
        });

        FullWidthButton testScreen = new FullWidthButton("Other Test");
        testScreen.setChangeListener(new FieldChangeListener()
        {
            /**
             * @see FieldChangeListener#fieldChanged(Field, int)
             */
            public void fieldChanged(Field field, int context)
            {
                menuManger.showTestScreen();
            }
        });
        add(testScreen);


        receiveMessagesButton.setFocusListener(new FocusChangeListener()
        {
            /**
             * @see FocusChangeListener#focusChanged(Field, int)
             */
            public void focusChanged(Field field, int eventType)
            {
                setInstructions("Test push messaging using an external push server, BPDS pushes and IPC communication. ");
            }
        });

        FullWidthButton cancelMessagesButton = new FullWidthButton("Cancel Messages");
        cancelMessagesButton.setChangeListener(new FieldChangeListener()
        {
            /**
             * @see FieldChangeListener#fieldChanged(Field, int)
             */
            public void fieldChanged(Field field, int context)
            {
                menuManger.showCancellationScreen();
            }
        });

        cancelMessagesButton.setFocusListener(new FocusChangeListener()
        {
            /**
             * @see FocusChangeListener#focusChanged(Field, int)
             */
            public void focusChanged(Field field, int eventType)
            {
                setInstructions("Test message cancellation with different types of settings. ");
            }
        });

        FullWidthButton authButton = new FullWidthButton("Basic Authentication");
        authButton.setChangeListener(new FieldChangeListener()
        {
            /**
             * @see FieldChangeListener#fieldChanged(Field, int)
             */
            public void fieldChanged(Field field, int context)
            {
                menuManger.showAuthenticationScreen();
            }
        });

        authButton.setFocusListener(new FocusChangeListener()
        {
            /**
             * @see FocusChangeListener#focusChanged(Field, int)
             */
            public void focusChanged(Field field, int eventType)
            {
                setInstructions("Test basic authentication. ");
            }
        });

        FullWidthButton streamButton = new FullWidthButton("Stream Data Upload");
        streamButton.setChangeListener(new FieldChangeListener()
        {
            /**
             * @see FieldChangeListener#fieldChanged(Field, int)
             */
            public void fieldChanged(Field field, int eventType)
            {
                menuManger.showStreamDataScreen();
            }
        });

        streamButton.setFocusListener(new FocusChangeListener()
        {
            /**
             * @see FocusChangeListener#focusChanged(Field, int)
             */
            public void focusChanged(Field field, int eventType)
            {
                setInstructions("Test handling of stream data.");

            }
        });

        // Add components to screen
        add(sendMessagesButton);
        add(receiveMessagesButton);
        add(cancelMessagesButton);
        add(authButton);
        add(streamButton);
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


    private void setInstructions(String instruction)
    {
        _instructions.setText(instruction);
    }
}
