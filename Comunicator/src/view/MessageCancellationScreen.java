/**
 * MessageCancellationScreen.java
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
import net.rim.device.api.ui.component.RichTextField;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.container.MainScreen;


public final class MessageCancellationScreen extends MainScreen
{
    private EditField _uriSenderField;
    private EditField _contentField1;
    private EditField _contentField2;


    /**
     * Creates a new MessageCancellationScreen object
     */
    public MessageCancellationScreen(final CommunicationController controller)
    {
        setTitle("Cancel Messages");

        // Initialize UI components     
        FullWidthButton backButton = new FullWidthButton("Back");
        backButton.setChangeListener(new FieldChangeListener()
        {
            /**
             * @see FieldChangeListener#fieldChanged(Field, int)
             */
            public void fieldChanged(Field field, int context)
            {
                ((UiApplication) (MessageCancellationScreen.this.getApplication())).popScreen(MessageCancellationScreen.this);
            }
        });

        // Set the destination to which messages will be to be sent:
        _uriSenderField = new EditField("Destination:", CommunicationController.ECHO_SERVER_URI + "TEXT");

        FullWidthButton destinationButton = new FullWidthButton("Set Destination");
        destinationButton.setChangeListener(new FieldChangeListener()
        {
            /**
             * @see FieldChangeListener#fieldChanged(Field, int)
             */
            public void fieldChanged(Field field, int context)
            {
                controller.setNonBlockingSenderDestination(_uriSenderField.getText());
            }
        });

        // First Message
        _contentField1 = new EditField("Message:", "Message One");
        FullWidthButton postButton1 = new FullWidthButton("Send and Cancel (ALL)");
        postButton1.setChangeListener(new FieldChangeListener()
        {
            /**
             * @see FieldChangeListener#fieldChanged(Field, int)
             */
            public void fieldChanged(Field field, int context)
            {
                controller.testMessageCancellable(_contentField1.getText(), true); // cancellable = true

            }
        });

        // Second Message
        _contentField2 = new EditField("Message:", "Message Two");
        FullWidthButton postButton2 = new FullWidthButton("Send and Cancel (NEVER)");
        postButton2.setChangeListener(new FieldChangeListener()
        {
            /**
             * @see FieldChangeListener#fieldChanged(Field, int)
             */
            public void fieldChanged(Field field, int context)
            {
                controller.testMessageCancellable(_contentField2.getText(), false); // Cancellable = false
            }
        });

        // Cancel all cancellable
        FullWidthButton cancelButton = new FullWidthButton("Cancel All Cancellable");
        cancelButton.setChangeListener(new FieldChangeListener()
        {
            /**
             * @see FieldChangeListener#fieldChanged(Field, int)
             */
            public void fieldChanged(Field field, int context)
            {
                controller.testCancelAllCancellable();
            }
        });

        RichTextField messages = new RichTextField("Cancel All Cancellable tries to send four different messages with different"
                                     + " cancellation options set on them and cancels them all.", Field.NON_FOCUSABLE);

        // Add components to screen
        add(backButton);
        add(_uriSenderField);
        add(destinationButton);
        add(_contentField1);
        add(postButton1);
        add(_contentField2);
        add(postButton2);
        add(cancelButton);
        add(new SeparatorField());
        add(messages);
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
