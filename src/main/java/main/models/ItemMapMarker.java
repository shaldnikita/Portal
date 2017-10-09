package main.models;

import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapMarker;

/**
 * @author n.shaldenkov on 04.10.2017
 */
public class ItemMapMarker extends GoogleMapMarker {
    private String text;

    private String authorID;
    public ItemMapMarker() {

    }
    public ItemMapMarker(String caption, LatLon position, boolean draggable, String text,String authorId) {
        super(caption, position, draggable);
        this.text = text;
        this.authorID=authorId;
    }

    public ItemMapMarker(String caption, LatLon position, boolean draggable, String iconUrl,String text,String authorId) {
        super(caption, position, draggable, iconUrl);
        this.text = text;
        this.authorID=authorId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthorID() {
        return authorID;
    }

    public void setAuthorID(String authorID) {
        this.authorID = authorID;
    }
}
