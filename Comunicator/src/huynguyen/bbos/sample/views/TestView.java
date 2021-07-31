package huynguyen.bbos.sample.views;

import com.rim.samples.device.communicationapidemo.CommunicationController;
import com.rim.samples.device.communicationapidemo.ui.FullWidthButton;
import com.rim.samples.device.communicationapidemo.view.StreamDataScreen;
import huynguyen.bbos.Net;
import huynguyen.bbos.UI;
import huynguyen.bbos.java.IMessageCallback;
import huynguyen.bbos.sample.App;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Ui;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.container.MainScreen;

public final class TestView extends MainScreen {
    public TestView(final CommunicationController controller) {
        setTitle("Test");

        FullWidthButton openBrowser = new FullWidthButton("Open Link");
        openBrowser.setChangeListener(new FieldChangeListener() {
            public void fieldChanged(Field field, int context) {
                {
                    UI.showWeb(App.HOST_URL);
                }
            }
        });
        add(openBrowser);


        FullWidthButton postButton = new FullWidthButton("POST");
        postButton.setChangeListener(new FieldChangeListener()
        {
            public void fieldChanged(Field field, int context)
            {
                {
                    new Net().POST(App.HOST_URL + "/upload","user=test&pass=123", new IMessageCallback() {
                        public void onMessage(final String mess) {
                            UI.r(new Runnable() {
                                public void run() {
                                    Dialog.alert("Success:" + mess);
                                }
                            });
                        }
                    });
                }

            }
        });

        add(postButton);

        FullWidthButton getButton = new FullWidthButton("GET");
        getButton.setChangeListener(new FieldChangeListener()
        {
            public void fieldChanged(Field field, int context)
            {
                {
                    new Net().GET(App.HOST_URL, new IMessageCallback() {
                        public void onMessage(final String mess) {
                            UI.r(new Runnable() {
                                public void run() {
                                    Dialog.alert("Success:" + mess);
                                }
                            });
                        }
                    });
                }

            }
        });
        add(getButton);

    }
}
