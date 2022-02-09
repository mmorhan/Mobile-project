package com.example.ticketapp.content;


import java.util.UUID;

public class Content {


    private String id =UUID.randomUUID().toString();
    private String imagepath;
    private String header;
    private String descrption;
    private String date;
    private String source;
//    private static ArrayList<Content> contentList = new ArrayList<>();
    public Content() {
    }

    @Override
    public String toString() {
        return "Content{" +
                "imagepath=" + imagepath +
                ", header='" + header + '\'' +
                ", descrption='" + descrption + '\'' +
                ", date='" + date + '\'' +
                ", source='" + source + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getDescrption() {
        return descrption;
    }

    public void setDescrption(String descrption) {
        this.descrption = descrption;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }


}
