package huynguyen.bbos;

import net.rim.device.api.ui.UiApplication;

public class UI {
    public static void r(Runnable r){
        UiApplication.getUiApplication().invokeLater(r);
    }
}
