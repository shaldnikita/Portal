package main.dataloader;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import main.models.Item;
import main.viewcontent.GoogleMapContent;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author n.shaldenkov on 04.10.2017
 */
public class Test {
    private volatile GoogleMapContent googleMapContent;
    private FirebaseApp defaultApp;
    private FirebaseDatabase defaultDatabase;
    private DatabaseReference ref;

    public static void main(String[] args) {
        new Test().init();

    }

    public void init() {
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


        clear();
        fill();
    }

    public void fill() {
        for (int i = 0; i <= 10; i++) {
            for (int j = 0; j <= 10; j++) {
                DatabaseReference newRef = ref.child("user" + i);
                Map params = new HashMap<>();
                params.put(String.valueOf(j),
                        new Item((long) j, "user" + i, "name" + j, "NoP", "size", "text" + i + "text" +
                                j, Double.valueOf("37.61"  + (int) (Math.random() * 10) + "" + (int) (Math.random() * 10) + ""  + (int) (Math.random() * 10) + "" + (int) (Math.random() * 10) + "" + (int) (Math.random() * 100)),
                                Double.valueOf("55.85"  + (int) (Math.random() * 10) + "" + (int) (Math.random() * 10) + "" + (int) (Math.random() * 10) + "" + (int) (Math.random() * 10) + "" + (int) (Math.random() * 100)), "date", "color"));

                newRef.updateChildren(params);
            }
        }
    }

    public void clear() {
                ref.setValue(null);
    }
}
