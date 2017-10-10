package main.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import main.viewcontent.GoogleMapContent;
import main.design.MainViewDesign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;



@SpringView(name = "")
public class MainView extends MainViewDesign implements View {
    private final static Logger log = LoggerFactory.getLogger(MainView.class);

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    BeanFactory beanFactory;

    public MainView() {
        menu.removeItems();
        content.setSizeFull();

        menu.addItem("Фильтры", (e) -> {

        });

        menu.addItem("Карта", (e) -> {
            GoogleMapContent map = beanFactory.getBean(GoogleMapContent.class);

            map.setHeight("100%");
            setContent(map);

        });

        menu.addItem("Хуйня", (e) -> {

        });
    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }

    public void setContent(Component component){
        content.removeAllComponents();
        content.addComponent(component);
    }
}
