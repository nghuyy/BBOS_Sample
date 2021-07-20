/*
 * AuthenticationScreen.java
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



public final class AuthenticationScreen extends MainScreen
{
    private EditField _uriField;
    private EditField _userIdField;
    private EditField _passwordField;
    private ResponseCallback _callback;
    private boolean _pending;


    /**
     * Creates a new AuthenticationScreen object
     */
    public AuthenticationScreen(final CommunicationController controller)
    {
        setTitle("Basic Authentication");

        _callback = new AuthentificationScreenCallback(this);

        // Initialize UI components
        FullWidthButton backButton = new FullWidthButton("Back");
        _uriField = new EditField("Enter URL:", "http://dev.vnapps.com/", 140, 0);
        _userIdField = new EditField("Username:", "debobb", 140, 0);
        _passwordField = new EditField("Password:", "debo2010", 140, 0);

        backButton.setChangeListener(new FieldChangeListener()
        {
            /**
             * @see FieldChangeListener#fieldChanged(Field, int)
             */
            public void fieldChanged(Field field, int context)
            {
                ((UiApplication) (AuthenticationScreen.this.getApplication())).popScreen(AuthenticationScreen.this);
            }
        });

        FullWidthButton postButton = new FullWidthButton("Get Data");
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
                    controller.authenticate(_uriField.getText(), _userIdField.getText(), _passwordField.getText(), _callback);
                }
                else
                {
                    Dialog.alert("Previous request pending state...");
                }

            }
        });

        LabelField instructions = new LabelField("This screen allows you to set credentials on your request. To test authentication, "
                        + "enter a URL that needs basic authentication and add the username and password parameters to the request.",
                        Field.NON_FOCUSABLE);

        // Add components to screen
        add(backButton);
        add(_uriField);
        add(_userIdField);
        add(_passwordField);
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

    private static final class AuthentificationScreenCallback extends ResponseCallback
    {

        private AuthenticationScreen _screen;


        private AuthentificationScreenCallback(AuthenticationScreen screen)
        {
            _screen = screen;
        }


        public void onResponse(Message message)
        {
            if(message != null)
            {

                String alertString = " :RECEIVED[id: " + message.getMessageId() + "]:";

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
