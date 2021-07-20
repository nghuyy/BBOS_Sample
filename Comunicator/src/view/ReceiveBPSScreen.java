/*
 * ReceiveBPSScreen.java
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
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.container.MainScreen;



public final class ReceiveBPSScreen extends MainScreen
{
    private EditField _contentField;
    private EditField _uriField;
    private EditField _appIdField;
    private EditField _uriReceiverField;


    /**
     * Create a new ReceiveBPSScreen object
     */
    public ReceiveBPSScreen(final CommunicationController controller)
    {
        setTitle("BPS Push");

        // Initialize UI components        
        _uriField = new EditField("BPS URI:", "http://pushapi.eval.blackberry.com", 140, 0);
        _uriReceiverField = new EditField("Listen URI:", "local://:11111/test2", 140, 0);    //where 11111 - is the push port
        _appIdField = new EditField("Application ID:", "", 140, 0);
        

        FullWidthButton backButton = new FullWidthButton("Back");
        backButton.setChangeListener(new FieldChangeListener()
        {
            /**
             * @see FieldChangeListener#fieldChanged(Field, int)
             */
            public void fieldChanged(Field field, int context)
            {
                ((UiApplication) (ReceiveBPSScreen.this.getApplication())).popScreen(ReceiveBPSScreen.this);
            }
        });

        _contentField = new EditField("Content provider URI:", "https://10.11.23.45:8443/sample-app/subscribe", 140, 0);

        FullWidthButton postButton = new FullWidthButton("Subscribe for push");
        postButton.setChangeListener(new FieldChangeListener()
        {
            /**
             * @see FieldChangeListener#fieldChanged(Field, int)
             */
            public void fieldChanged(Field field, int context)
            {
                controller.registerBPSPush(_appIdField.getText(), _uriField.getText(), _contentField.getText(), _uriReceiverField.getText());
            }
        });

        LabelField instructions = new LabelField("This test allows you to register for pushes from a content provider. "
                                      + "You must enter the URL of the BPS server as well as the content provider URL.", Field.NON_FOCUSABLE);

        // Add components to screen
        add(backButton);
        add(_uriField);
        add(_appIdField);
        add(_contentField);
        add(_uriReceiverField);
        add(postButton);
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
