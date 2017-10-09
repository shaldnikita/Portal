package main.views;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import main.design.LoginDesign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@UIScope
@SpringView(name = "loginView")
public class LoginView extends LoginDesign implements View{

    @Autowired
    AuthenticationProvider authenticationProvider;

    public LoginView(){
        label.setValue("Portal");
        login.setCaption("Логин");
        password.setCaption("Пароль");

        loginButton.setCaption("Войти");

        loginButton.addClickListener(e ->{
            authorize();
        });
    }

    private void authorize(){

        Authentication auth = authenticationProvider.
                authenticate(new UsernamePasswordAuthenticationToken(login.getValue(),password.getValue()));

        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        if(SecurityContextHolder.getContext().getAuthentication()!=null)
            UI.getCurrent().getNavigator().navigateTo("");
    }

}