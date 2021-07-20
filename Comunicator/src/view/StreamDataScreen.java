/*
 * StreamDataScreen.java
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

import com.rim.samples.device.communicationapidemo.CommunicationController;
import com.rim.samples.device.communicationapidemo.ResponseCallback;
import com.rim.samples.device.communicationapidemo.ui.FullWidthButton;

import net.rim.device.api.io.messaging.ByteMessage;
import net.rim.device.api.io.messaging.Message;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.container.MainScreen;


public final class StreamDataScreen extends MainScreen
{
    private EditField _uriSenderField;
    private ResponseCallback _callback;
    private boolean _pending;


    /**
     * Creates a new StreamDataScreen object
     */
    public StreamDataScreen(final CommunicationController controller)
    {
        setTitle("Stream Data Upload");

        _callback = new StreamDataScreenCallback(this);

        // Initialize UI components
        LabelField instructions = new LabelField("", Field.NON_FOCUSABLE);
        _uriSenderField = new EditField("Upload URI:", CommunicationController.ECHO_SERVER_URI + "upload", 140, 0);

        FullWidthButton backButton = new FullWidthButton("Back");
        backButton.setChangeListener(new FieldChangeListener()
        {
            /**
             * @see FieldChangeListener#fieldChanged(Field, int)
             */
            public void fieldChanged(Field field, int context)
            {
                ((UiApplication) (StreamDataScreen.this.getApplication())).popScreen(StreamDataScreen.this);
            }
        });

        FullWidthButton postButton = new FullWidthButton("Upload Data");
        postButton.setChangeListener(new FieldChangeListener()
        {
            /**
             * @see FieldChangeListener#fieldChanged(Field, int)
             */
            public void fieldChanged(Field field, int context)
            {
                if(!_pending)
                {
                    _pending = true;
                    controller.uploadPOST(_uriSenderField.getText(), _callback);
                }
                else
                {
                    Dialog.alert("Previous request pending state...");
                }

            }
        });

        // Add components to screen
        add(backButton);
        add(_uriSenderField);
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

    private static final class StreamDataScreenCallback extends ResponseCallback
    {
        private StreamDataScreen _screen;

        
        private StreamDataScreenCallback(StreamDataScreen screen)
        {
            _screen = screen;
        }


        public void onResponse(Message message)
        {
            if(message != null)
            {

                String alertString = "RECEIVED[id: " + message.getMessageId() + "]:";

                String stringPayload = ((ByteMessage) message).getStringPayload();
                if(stringPayload != null)
                {
                    alertString += "\n" + stringPayload;
                }
                Dialog.alert(alertString);

            }
            _screen._pending = false;
        }


        public void onTimeout(int timeout)
        {
            _screen._pending = false;
            String timeoutMessage = "Wait for response: timed out after " + timeout + " sec";
            Dialog.alert(timeoutMessage);
        }
    }
}
