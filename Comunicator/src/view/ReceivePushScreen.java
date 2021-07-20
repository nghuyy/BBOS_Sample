/*
 * ReceivePushScreen.java
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

import com.rim.samples.device.communicationapidemo.CommunicationController;
import com.rim.samples.device.communicationapidemo.ui.FullWidthButton;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.CheckboxField;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.container.MainScreen;


public final class ReceivePushScreen extends MainScreen
{
    private EditField _uriReceiverField;
    private CheckboxField _autoStartEnabled;
    private CheckboxField _isBlocking;
    private static final String PATH_STRING = "local://:1992/test8";


    /**
     * Creates a new ReceivePushScreen object
     */
    public ReceivePushScreen(final CommunicationController controller)
    {
        setTitle("BES Push");

        // Initialize UI components

        _uriReceiverField = new EditField("Receiver URI:", PATH_STRING, 140, 0);

        _isBlocking = new CheckboxField("Blocking", false);
        _isBlocking.setChangeListener(new FieldChangeListener()
        {
            /**
             * @see FieldChangeListener#fieldChanged(Field, int)
             */
            public void fieldChanged(Field field, int context)
            {
                //Avoid conflict blocking and non-blocking destinations with the same name
                if(_isBlocking.getChecked())
                {
                    _uriReceiverField.setText(PATH_STRING + "9");
                }
                else
                {
                    _uriReceiverField.setText(PATH_STRING);
                }
            }
        });

        _autoStartEnabled = new CheckboxField("Auto-start when message arrives", true);

        FullWidthButton backButton = new FullWidthButton("Back");
        backButton.setChangeListener(new FieldChangeListener()
        {
            /**
             * @see FieldChangeListener#fieldChanged(Field, int)
             */
            public void fieldChanged(Field field, int context)
            {
                ((UiApplication) (ReceivePushScreen.this.getApplication())).popScreen(ReceivePushScreen.this);
            }
        });

        FullWidthButton startButton = new FullWidthButton("Start Receiver");
        startButton.setChangeListener(new FieldChangeListener()
        {
            /**
             * @see FieldChangeListener#fieldChanged(Field, int)
             */
            public void fieldChanged(Field field, int context)
            {
                if(_isBlocking.getChecked())
                {
                    // Blocking receiver
                    controller.startBlockingReceiverBES(_uriReceiverField.getText(), _autoStartEnabled.getChecked(),
                        CommunicationController.TIMEOUT);
                }
                else
                {
                    // Non blocking receiver 
                    controller.startNonBlockingReceiverBES(_uriReceiverField.getText(), _autoStartEnabled.getChecked());
                }

            }
        });

        FullWidthButton pauseButton = new FullWidthButton("Pause Receiver");
        pauseButton.setChangeListener(new FieldChangeListener()
        {
            /**
             * @see FieldChangeListener#fieldChanged(Field, int)
             */
            public void fieldChanged(Field field, int context)
            {
                controller.pauseReceiver(_uriReceiverField.getText());
            }
        });

        FullWidthButton resumeButton = new FullWidthButton("Resume Receiver");
        resumeButton.setChangeListener(new FieldChangeListener()
        {
            /**
             * @see FieldChangeListener#fieldChanged(Field, int)
             */
            public void fieldChanged(Field field, int context)
            {
                controller.resumeReceiver(_uriReceiverField.getText());
            }
        });

        FullWidthButton releaseButton = new FullWidthButton("Release Receiver");
        releaseButton.setChangeListener(new FieldChangeListener()
        {
            /**
             * @see FieldChangeListener#fieldChanged(Field, int)
             */
            public void fieldChanged(Field field, int context)
            {
                controller.releaseReceiver(_uriReceiverField.getText());

            }
        });

        FullWidthButton destroyButton = new FullWidthButton("Destroy Receiver");
        destroyButton.setChangeListener(new FieldChangeListener()
        {
            /**
             * @see FieldChangeListener#fieldChanged(Field, int)
             */
            public void fieldChanged(Field field, int context)
            {
                controller.destroyReceiver(_uriReceiverField.getText());

            }
        });

        LabelField instructions = new LabelField(
                        "This test allows you to start listening for push messages. On the push server you have to"
                       + " specify receiver as '/test8'.  The push messages can be paused, restarted or stopped completely using the 'Pause',"
                       + " 'Resume' and 'Destroy' buttons, respectively. Registering for pushes also registers auto-start where the app wakes up if"
                       + " it is closed for any incoming push to it.", Field.NON_FOCUSABLE);

        // Add components to screen
        add(backButton);
        add(_uriReceiverField);
        add(_isBlocking);
        add(_autoStartEnabled);
        add(startButton);
        add(pauseButton);
        add(resumeButton);
        add(releaseButton);
        add(destroyButton);
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
