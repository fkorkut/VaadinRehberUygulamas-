package egitim.uniyaz;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;

/**
 *
 */
@Theme("mytheme")
@Widgetset("egitim.uniyaz.MyAppWidgetset")
public class MyUI extends UI {
Islemler islemler=new Islemler();
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        FormLayout formLayout = new FormLayout();
        islemler.components(formLayout);

        setContent(formLayout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }





}
