package main.dao;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.database.*;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.ui.Notification;
import main.models.Item;
import main.models.ItemMapMarker;
import main.viewcontent.GoogleMapContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

@Component
@UIScope
public class GoogleFireBaseDao {
    private final static Logger log = LoggerFactory.getLogger(GoogleFireBaseDao.class);

    private volatile GoogleMapContent googleMapContent;
    private FirebaseApp defaultApp;
    private FirebaseDatabase defaultDatabase;
    private DatabaseReference ref;

    public GoogleFireBaseDao(GoogleMapContent googleMapContent) {
        this.googleMapContent = googleMapContent;
    }

    @PreDestroy
    public void closeConnection() {
        defaultApp.delete();
    }

    @PostConstruct
    public void init() {
        log.info("init repo");
        FirebaseOptions options = null;

        try (FileInputStream serviceAccount = new FileInputStream("key.json")) {

            options = new FirebaseOptions.Builder()
                    .setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
                    .setDatabaseUrl("https://hologrammer-a6294.firebaseio.com/")
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            defaultApp = FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            defaultApp = FirebaseApp.getInstance();
        }

        defaultDatabase = FirebaseDatabase.getInstance(defaultApp);

        ref = defaultDatabase.getReference("holograms/");

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
                DatabaseReference userRef = snapshot.getRef();

                log.debug("new user`s ref is {}", userRef.toString());

                userRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        log.debug("new hologram`s ref is {}", dataSnapshot.getRef().toString());
                        ItemMapMarker marker = initMarker(dataSnapshot);

                        googleMapContent.addMarker(marker);
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        ItemMapMarker marker = initMarker(dataSnapshot);

                        googleMapContent.updateMarker(marker);
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        ItemMapMarker marker = initMarker(dataSnapshot);

                        googleMapContent.removeMarker(marker);
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Notification.show("Database error", Notification.Type.WARNING_MESSAGE);
                    }
                });
            }

            @Override
            public void onChildChanged(DataSnapshot snapshot, String previousChildName) {

            }

            @Override
            public void onChildRemoved(DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot snapshot, String previousChildName) {

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    private ItemMapMarker initMarker(DataSnapshot dataSnapshot) {

        Item item = dataSnapshot.getValue(Item.class);
        item.setId(Long.valueOf(dataSnapshot.getKey()));

        ItemMapMarker marker = new ItemMapMarker();

        marker.setId(item.getId());
        marker.setPosition(new LatLon(item.getLatitude(), item.getLongitude()));
        marker.setCaption(item.getName());
        marker.setText(item.getText());
        marker.setAuthorID(item.getAuthorID());

        marker.setDraggable(false);

        return marker;
    }

    public void updateMarker(ItemMapMarker marker) {
        DatabaseReference curHologram = ref.child(marker.getAuthorID()).child(String.valueOf(marker.getId()));

        curHologram.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Item item = snapshot.getValue(Item.class);
                item.setName(marker.getCaption());
                item.setText(marker.getText());

                Map<String, Object> params = new HashMap<>();

                params.put(String.valueOf(marker.getId()), item);

                ref.child(marker.getAuthorID()).updateChildren(params);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

    }

    public void deleteMarker(ItemMapMarker marker) {
        ref.child(marker.getAuthorID()).child(String.valueOf(marker.getId())).removeValue();
    }


}
