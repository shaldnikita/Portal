package main.viewcontent;

import com.vaadin.event.ShortcutAction;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import main.models.ItemMapMarker;
import main.dao.GoogleFireBaseDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author n.shaldenkov on 01.10.2017
 */

@Component
@UIScope
public class GoogleMapContent extends HorizontalLayout {
    private static final Logger log = LoggerFactory.getLogger(GoogleMapContent.class);

    @Autowired
    BeanFactory beanFactory;

    private GoogleFireBaseDao googleFireBaseDao;

    private GoogleMap googleMap;

    private ItemMapMarker curMarker;

    @PreDestroy
    public void closeConnection(){
        googleFireBaseDao.closeConnection();
    }

    @PostConstruct
    public void init() {
        log.info("init map");

        setSizeFull();

        googleMap = new GoogleMap("AIzaSyAYaCAX-wNZdkdsH1XoNPG75BOInwxPfTs", null, "russian");
        googleMap.setSizeFull();
        googleMap.setCenter(new LatLon(55.852357, 37.617480));
        googleMap.setMinZoom(10);

        FormLayout info = new FormLayout();
        info.setVisible(false);

        TextField name = new TextField("Название");
        name.setPlaceholder("name...");

        TextField lat = new TextField("Широта");
        lat.setReadOnly(true);

        TextField lon = new TextField("Долгота");
        lon.setReadOnly(true);

        TextArea text = new TextArea("Текст");
        text.setPlaceholder("text...");


        Button deleteButton = new Button("Удалить");
        deleteButton.addClickListener(e ->{
            googleFireBaseDao.deleteMarker(curMarker);
            removeMarker(curMarker);
        });

        Button updateButton = new Button("Обновить");
        updateButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
        updateButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        updateButton.addClickListener(e ->{

            curMarker.setText(text.getValue());
            curMarker.setCaption(name.getValue());

            googleFireBaseDao.updateMarker(curMarker);
            updateMarker(curMarker);
        });

        HorizontalLayout buttons = new HorizontalLayout(updateButton,deleteButton);
        buttons.setMargin(new MarginInfo(true,true,true,false));

        info.addComponents(name, text,lon,lat,buttons);

        googleMap.addMarkerClickListener(e -> {
            ItemMapMarker marker = (ItemMapMarker)e;

            curMarker = marker;

            name.setValue(marker.getCaption());
            text.setValue(marker.getText());

            lon.setReadOnly(false);
            lat.setReadOnly(false);
            lon.setValue(String.valueOf(marker.getPosition().getLon()));
            lat.setValue(String.valueOf(marker.getPosition().getLat()));
            lon.setReadOnly(true);
            lat.setReadOnly(true);

            info.setVisible(true);
        });

        googleMap.addMapClickListener(e -> info.setVisible(false));

        curMarker=null;

        addComponents(googleMap, info);
        setExpandRatio(googleMap, 1);
        setExpandRatio(info, 0.3f);

        googleFireBaseDao = beanFactory.getBean(GoogleFireBaseDao.class,this);
    }

    public void addMarker(ItemMapMarker marker){
        log.debug("adding marker [id:{},name:{}]",marker.getId(),marker.getCaption());
        googleMap.addMarker(marker);
    }

    public void updateMarker(ItemMapMarker marker){
        log.debug("updating marker [id:{},name:{}]",marker.getId(),marker.getCaption());
        googleMap.removeMarker(marker);
        googleMap.addMarker(marker);
    }

    public void removeMarker(ItemMapMarker marker){
        log.debug("removing marker [id:{},name:{}]",marker.getId(),marker.getCaption());
        googleMap.removeMarker(marker);
    }


}
