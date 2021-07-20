/*
 * SendNonBlockSoapScreen.java
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
import com.rim.samples.device.communicationapidemo.ResponseCallback;
import com.rim.samples.device.communicationapidemo.ui.FullWidthButton;

import java.util.Vector;

import net.rim.device.api.io.messaging.Message;
import net.rim.device.api.io.messaging.MessageProcessorException;
import net.rim.device.api.io.parser.soap.SOAPBody;
import net.rim.device.api.io.parser.soap.SOAPElement;
import net.rim.device.api.io.parser.soap.SOAPEnvelope;
import net.rim.device.api.io.parser.soap.SOAPHeader;
import net.rim.device.api.io.parser.soap.SOAPMessageProcessor;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.component.RichTextField;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.container.MainScreen;


/**
 * SOAP processing functionality screen
 */
public final class SendNonBlockSoapScreen extends MainScreen
{
    private EditField _uriSenderField;
    private RichTextField _formattedMsgContentField;
    private RichTextField _msgContentField;
    private ResponseCallback _callback;
    private boolean _pending;


    /**
     * Creates a new SendNonBlockSoapScreen object
     * 
     * @param controller
     */
    public SendNonBlockSoapScreen(final CommunicationController controller)
    {
        setTitle("SOAP");

        _callback = new SendNonBlockXmlScreenCallback(this);

        _uriSenderField = new EditField("URI:", CommunicationController.ECHO_SERVER_URI + "SOAP", 140, 0);

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
                    clearSoapVew("* No SOAP Message *");
                    controller.sendNonBlocking(_uriSenderField.getText(), true, _callback);
                }
                else
                {
                    Dialog.alert("Previous request pending state...");
                }
            }
        });

        _formattedMsgContentField = new RichTextField("* No SOAP Message *");
        _msgContentField = new RichTextField("");

        // Add UI components to screen
        add(_uriSenderField);
        add(postButton);
        add(_formattedMsgContentField);
        add(new SeparatorField());
        add(_msgContentField);
    }


    private void updateSoapView(Message message)
    {
        if(process(message))
        {
            displaySoap(message);
        }
        else
        {
            clearSoapVew("* No SOAP Message *");
        }
    }


    /**
     * Displays parts of the SOAP Message
     */
    private void displaySoap(Message message)
    {
        Object soap = message.getObjectPayload();

        SOAPEnvelope env = (SOAPEnvelope) soap;
        SOAPHeader header = env.getHeader();
        SOAPBody body = env.getBody();

        String str = "====== Envelope:";
        str += "\nName: " + env.getName();
        str += "\nNamespace: " + env.getNamespace();
        str += "\n====== Header:";
        if(header != null)
        {
            str += "\nName: " + header.getName();
            str += "\nNamespace: " + header.getNamespace();
            str += "\nNumber of children: " + header.getChildren().size();
        }
        else
        {
            str += "\n null";
        }

        str += "\n====== Body:";
        if(body != null)
        {
            str += "\nName: " + body.getName();
            str += "\nNamespace: " + body.getNamespace();

            Vector children = body.getChildren();

            str += "\nNumber of children: " + children.size();
            for(int i = 0; i < children.size(); i++)
            {
                SOAPElement element = (SOAPElement) children.elementAt(i);
                if(element != null)
                {
                    str += "\n  [child " + i + "]";
                    str += "\n   Name: " + element.getName();
                    str += "\n   Type: " + element.getType();
                    str += "\n   Namespace: " + element.getNamespace();
                }
            }
        }
        else
        {
            str += "\n null";
        }

        _formattedMsgContentField.setText(str);
        _msgContentField.setText(" ******* SOAP Request *******\n" + env.toSoapRequest());
    }


    /**
     * Processes message using SOAP Message Processor
     */
    private boolean process(Message msg)
    {
        boolean isProcessed = true;

        SOAPMessageProcessor processor = new SOAPMessageProcessor();
        try
        {
            processor.process(msg);
        }
        catch(MessageProcessorException mpe)
        {
            System.out.println(mpe.toString());
            isProcessed = false;
        }

        return isProcessed;
    }


    private void clearSoapVew(String statusString)
    {
        _formattedMsgContentField.setText(statusString);
        _msgContentField.setText("");
    }


    /**
     * @see MainScreen#onSavePrompt()
     */
    public boolean onSavePrompt()
    {
        // Suppress the save dialog
        return true;
    }
    
    
    private static final class SendNonBlockXmlScreenCallback extends ResponseCallback
    {

        private SendNonBlockSoapScreen _screen;


        private SendNonBlockXmlScreenCallback(SendNonBlockSoapScreen screen)
        {
            _screen = screen;
        }


        public void onResponse(Message message)
        {
            if(message != null)
            {
                _screen.updateSoapView(message);
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
