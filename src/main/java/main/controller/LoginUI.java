package main.controller;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.*;
import main.Application;
import main.security.LocalUserAuthenticationProvider;
import main.views.MainView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


/**
 * @author n.shaldenkov on 09.10.2017
 */
@SpringUI
@Title("Portal")
@Theme("valo")
@PreserveOnRefresh
@Push
public class LoginUI extends UI {

    @Autowired
    SpringViewProvider viewProvider;

    private final static Logger log = LoggerFactory.getLogger(LoginUI.class);

    private FormLayout formLayout = new FormLayout();
    private Label label = new Label("Welcome");
    private TextField userName = new TextField("Login");
    private PasswordField password = new PasswordField("Password");
    private Button loginButton = new Button("Login");


    @Autowired
    private LocalUserAuthenticationProvider localUserAuthenticationProvider;


    @Override
    protected void init(VaadinRequest request) {
        log.info("UI init {}", this);

        addStyleName("user-menu");

        formLayout.setSizeUndefined();

        label.setSizeUndefined();
        label.addStyleName("colored");
        label.addStyleName("h2");
        formLayout.addComponent(label);

        formLayout.addComponent(userName);
        formLayout.addComponent(password);

        loginButton.addStyleName("primary");
        formLayout.addComponent(loginButton);

        label.setValue("Portal");
        userName.setCaption("Логин");
        password.setCaption("Пароль");

        loginButton.setCaption("Войти");

        loginButton.addClickListener(e ->{
            authorize();
        });

        setContent(formLayout);

        Navigator navigator = new Navigator(this,this);
        navigator.addProvider(viewProvider);
    }

    private void authorize(){

        Authentication auth = new UsernamePasswordAuthenticationToken(userName.getValue(),password.getValue());
        Authentication authenticated = localUserAuthenticationProvider.authenticate(auth);

        if(authenticated!=null && authenticated.isAuthenticated()){
            SecurityContextHolder.getContext().setAuthentication(authenticated);
            getPage().setLocation("localhost:8080/");
            UI.getCurrent().getNavigator().navigateTo(MainView.VIEW_NAME);
        }else{
            Notification.show("Wrong login or password!", Notification.Type.WARNING_MESSAGE);
        }
    }
}
