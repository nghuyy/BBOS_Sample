/**
 * CryptoDemo.java
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

package huynguyen.sample.crypto;

import java.io.*;

import net.rim.device.api.command.*;
import net.rim.device.api.crypto.*;
import net.rim.device.api.util.*;
import net.rim.device.api.ui.*;
import net.rim.device.api.ui.component.*;
import net.rim.device.api.ui.container.*;


/**
 * This sample application demonstrates basic functionality of the
 * net.rim.device.api.crypto package.  For more information please
 * see the Developer Knowledge Base on www.blackberry.com/developers. 
 */
public class CryptoDemo extends UiApplication
{    
    private RichTextField _statusField;
    private EditField _inputField;

    /**
     * Entry point for application
     * @param args Command line arguments (not used)
     */
    public static void main( String[] args )
    {
        // Create a new instance of the application and make the currently
        // running thread the application's event dispatch thread.
        CryptoDemo theApp = new CryptoDemo();
        theApp.enterEventDispatcher();
    }

    /**
     * Creates a new CryptoDemo object
     */
    public CryptoDemo()
    {
        MainScreen screen = new MainScreen();
        screen.setTitle("Crypto Demo");
        
        _inputField = new EditField("Text: ", "");
        screen.add(_inputField);
        screen.add(new SeparatorField());
        
        _statusField = new RichTextField("Select 'Go' from the menu to perform the test.");
        screen.add(_statusField);
        
        // Add the menu item
        MenuItem go = new MenuItem(new StringProvider("Go") , 0x230010, 0);
        go.setCommand(new Command(new CommandHandler() 
        {
            /**
             * @see net.rim.device.api.command.CommandHandler#execute(ReadOnlyCommandMetadata, Object)
             */
            public void execute(ReadOnlyCommandMetadata metadata, Object context) 
            {                
                String text = _inputField.getText();
                if(text.length() > 0)
                {                    
                    runTest(text);
                }
                else
                {
                    Dialog.alert("Please enter some text");
                }                
            }
        }));
        
        screen.addMenuItem(go);
        
        pushScreen(screen);
    }
    
    
     /**
      * Encrypts, then decrypts test data
      * @param text The text to encrpt and decrypt
      */
    private void runTest(String text)
    {
        try 
        {
            // We are going to use TripleDES as the algorithm for encrypting and
            // decrypting the data. It is a very common algorithm and was chosen
            // for this reason            

            // Create a new random AES
            AESKey key = new AESKey(256);

            // Create the encryption engine for encrypting the data
            AESEncryptorEngine encryptionEngine = new AESEncryptorEngine( key );

            // Due to the fact that in most cases the data that we are going to
            // encrypt will not fit perfectly into the block length of a cipher,
            // we want to use a padding algorithm to pad out the last block
            // (if necessary). We are going to use PKCS5 to do the padding
            // for us.
            PKCS5FormatterEngine formatterEngine = new PKCS5FormatterEngine( encryptionEngine );

            // Use the byte array output stream to catch the
            // encrypted information.
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            // Create a block encryptor which will help us use the triple
            // des engine.
            BlockEncryptor encryptor = new BlockEncryptor( formatterEngine, outputStream );

            // Encrypt the actual data            
            encryptor.write( text.getBytes() );

            // Close the stream. This forces the extra bytes to be padded out if
            // there were not enough bytes to fill all of the blocks.
            encryptor.close();

            // Get the actual encrypted data
            byte[] encryptedData = outputStream.toByteArray();

            // End of Encryption
            //-----------------------------------------------------------------------------------------------
            // Beginning of Decryption

            // We are now going to perform the decryption.  We want to ensure
            // that the message we get back is the same as the original. Note
            // that since this is a symmetric algorithm we want to use the same
            // key as was used for encryption.
            AESDecryptorEngine decryptorEngine = new AESDecryptorEngine( key );

            // Create the unformatter engine that will remove any of the
            // padding bytes.
            PKCS5UnformatterEngine unformatterEngine = new PKCS5UnformatterEngine( decryptorEngine );

            // Set up an input stream to hand the encrypted data to the
            // block decryptor.
            ByteArrayInputStream inputStream = new ByteArrayInputStream( encryptedData );

            // Create the block decryptor passing in the unformatter engine and
            // the encrypted data.
            BlockDecryptor decryptor = new BlockDecryptor( unformatterEngine, inputStream );

            // Now we want to read from the stream. We are going to read the
            // data 10 bytes at a time and then add the new data to the
            // decryptedData array.  It is important to note that for
            // efficiency one would most likely want to use a larger
            // value than 10.  We use a small value so that we can
            // demonstrate several iterations through the loop.
            byte[] temp = new byte[10];
            DataBuffer db = new DataBuffer();
            
            for( ;; ) 
            {
                int bytesRead = decryptor.read( temp );
                
                if( bytesRead <= 0 )
                {
                    // We have run out of information to read, bail out of loop
                    break;
                }
                
                db.write(temp, 0, bytesRead);
            }

            // Now we want to ensure that the decrypted data is the same as the
            // data we passed into the encryptor.
            byte[] decryptedData = db.toArray();
            
            String decryptedText = new String(decryptedData);
            
            if( Arrays.equals( text.getBytes(), decryptedData ) ) 
            {                                
                _statusField.setText("Test passed, the message is identical.\nDecrypted text: " + decryptedText);                
            } 
            else 
            {                                
                _statusField.setText("Test failed, the messages are different.\nDecrypted text: " + decryptedText);
            }
        } 
        catch( CryptoTokenException e ) 
        {
            errorDialog(e.toString());
        } 
        catch (CryptoUnsupportedOperationException e) 
        {
            errorDialog(e.toString());
        }  
        catch( IOException e ) 
        {
            errorDialog(e.toString());
        }
    }   
    
    /**
     * Presents a dialog to the user with a given message
     * @param message The text to display
     */
    public static void errorDialog(final String message)
    {
        UiApplication.getUiApplication().invokeLater(new Runnable()
        {
            public void run()
            {
                Dialog.alert(message);
            } 
        });
    }
}

