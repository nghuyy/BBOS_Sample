package huynguyen.bbos;

import net.rim.blackberry.api.browser.Browser;
import net.rim.blackberry.api.browser.BrowserSession;
import net.rim.blackberry.api.invoke.Invoke;
import net.rim.device.api.system.Application;
import net.rim.device.api.ui.UiApplication;

public class UI {
    public static void r(Runnable r){
        UiApplication.getUiApplication().invokeLater(r);
    }
    public static void showWeb(String link){
        BrowserSession visit = Browser.getDefaultSession();
        visit.displayPage(link);
    }
}
