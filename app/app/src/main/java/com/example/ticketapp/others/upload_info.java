package com.example.ticketapp.others;

public class upload_info {

    public String imageName;
    public String imageURL;
    public upload_info(){}

    public upload_info(String name, String url) {
        this.imageName = name;
        this.imageURL = url;
    }

    public String getImageName() {
        return imageName;
    }
    public String getImageURL() {
        return imageURL;
    }
}