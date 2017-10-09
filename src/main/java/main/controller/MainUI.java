package main.controller;


import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.UI;
import main.views.LoginView;
import main.views.MainView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringUI
@SpringViewDisplay
@Theme("valo")
@Widgetset("vaadin.widgetset.MapsWidgetSet")
public class MainUI extends UI {

    @Autowired
    ApplicationContext applicationContext;

    @Override
    protected void init(VaadinRequest request) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth==null)
            UI.getCurrent().getNavigator().navigateTo("login");

        else
        UI.getCurrent().getNavigator().navigateTo(MainView.VIEW_NAME);

    }
}