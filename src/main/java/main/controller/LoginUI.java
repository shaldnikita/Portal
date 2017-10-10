package main.controller;

import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import main.security.LocalUserAuthenticationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author n.shaldenkov on 10.10.2017
 */
@SpringUI(path = "/login")
public class LoginUI extends UI{
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
        log.info("init UI");

        VerticalLayout mainHolder = new VerticalLayout();
        mainHolder.setSizeFull();
        setContent(mainHolder);

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

        mainHolder.addComponent(formLayout);
        mainHolder.setComponentAlignment(formLayout,Alignment.MIDDLE_CENTER);
    }

    private void authorize(){

        Authentication auth = new UsernamePasswordAuthenticationToken(userName.getValue(),password.getValue());
        Authentication authenticated = localUserAuthenticationProvider.authenticate(auth);

        if(authenticated!=null && authenticated.isAuthenticated()){
            SecurityContextHolder.getContext().setAuthentication(authenticated);

            getPage().setLocation("/");
        }else{
            Notification.show("Wrong userName or password!", Notification.Type.WARNING_MESSAGE);
        }
    }
}
