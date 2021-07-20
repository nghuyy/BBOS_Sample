/*
 * SendFireForgetScreen.java
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
import com.rim.samples.device.communicationapidemo.util.Utils;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.CheckboxField;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.container.MainScreen;


public final class SendFireForgetScreen extends MainScreen
{
    private EditField _uriSenderField;
    private EditField _pathField;
    private CheckboxField _isLocal;
    private static final String PATH_STRING = "/test9";


    /**
     * Creates a new SendFireForgetScreen object
     */
    public SendFireForgetScreen(final CommunicationController controller)
    {
        setTitle("Fire-and-Forget");

        // Initialize UI components        
        LabelField instructions = new LabelField("Enter a destination URL and send a fire-and-forget message to it. " 
                                  + "Responses are not processed.", Field.NON_FOCUSABLE);

        _isLocal = new CheckboxField("Local Address ", true);
        _isLocal.setChangeListener(new FieldChangeListener()
        {
            /**
             * @see FieldChangeListener#fieldChanged(Field, int)
             */
            public void fieldChanged(Field field, int context)
            {
                if(_isLocal.getChecked())
                {
                    // Local
                    _pathField.setText(PATH_STRING);
                    updateSenderField();
                }
                else
                {
                    // Http
                    _pathField.setText("--NOT USED--");
                    _uriSenderField.setText(CommunicationController.ECHO_SERVER_URI + "TEXT");
                }
            }
        });

        _pathField = new EditField("Path:", PATH_STRING, 140, 0);
        _pathField.setChangeListener(new FieldChangeListener()
        {
            /**
             * @see FieldChangeListener#fieldChanged(Field, int)
             */
            public void fieldChanged(Field field, int context)
            {
                updateSenderField();
            }
        });

        _uriSenderField = new EditField("Sender URI:", "", 140, 0);

        FullWidthButton backButton = new FullWidthButton("Back");
        backButton.setChangeListener(new FieldChangeListener()
        {
            /**
             * @see FieldChangeListener#fieldChanged(Field, int)
             */
            public void fieldChanged(Field field, int context)
            {
                ((UiApplication) (SendFireForgetScreen.this.getApplication())).popScreen(SendFireForgetScreen.this);
            }
        });

        FullWidthButton postButton = new FullWidthButton("Send message");
        postButton.setChangeListener(new FieldChangeListener()
        {
            /**
             * @see FieldChangeListener#fieldChanged(Field, int)
             */
            public void fieldChanged(Field field, int context)
            {
                controller.sendFireForget(_uriSenderField.getText());
            }
        });

        updateSenderField();

        // Add components to screen
        add(backButton);
        add(_isLocal);
        add(_pathField);
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


    private void updateSenderField()
    {
        String path = _pathField.getText();
        String appName = "CommunicationAPIDemo";
        _uriSenderField.setText(Utils.createLocalClientUri(appName, path));
    }
}
