package main.models;



public class Item {
    private Long id;

    private String authorID;

    private String name;
    private String nameOfPlace;

    private String size;
    private String text;

    private Double longitude;
    private Double latitude;

    private String creationDate;

    private String textColor;

    public Item() {
    }

    public Item(Double latitude,Double longitude,String name) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Item(Long id, String authorID, String name, String nameOfPlace, String size, String text, Double longitude, Double latitude, String creationDate, String textColor) {
        this.id = id;
        this.authorID = authorID;
        this.name = name;
        this.nameOfPlace = nameOfPlace;
        this.size = size;
        this.text = text;
        this.longitude = longitude;
        this.latitude = latitude;
        this.creationDate = creationDate;
        this.textColor = textColor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthorID() {
        return authorID;
    }

    public void setAuthorID(String authorID) {
        this.authorID = authorID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameOfPlace() {
        return nameOfPlace;
    }

    public void setNameOfPlace(String nameOfPlace) {
        this.nameOfPlace = nameOfPlace;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }
}

