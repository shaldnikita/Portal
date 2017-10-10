package main.controller;

import com.vaadin.annotations.*;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


/**
 * @author n.shaldenkov on 09.10.2017
 */

@SpringUI
@Title("Portal")
@Theme("valo")
@Push
@Widgetset("vaadin.widgetset.MapsWidgetSet")
@SpringViewDisplay
public class MainUI extends UI {
    private final static Logger log = LoggerFactory.getLogger(MainUI.class);

    @Override
    protected void init(VaadinRequest request) {
        setContent(new Label("test"));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        System.out.println("------------------------------------------------------------------------------");
        if(auth!=null && auth.isAuthenticated()){
            getUI().getNavigator().navigateTo("");
        }else {
            getPage().setLocation("/login");
        }
    }
}
