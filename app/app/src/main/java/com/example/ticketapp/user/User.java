package com.example.ticketapp.user;

import com.example.ticketapp.content.Content;
import com.example.ticketapp.flight.Flight;

import java.util.ArrayList;

public class User {

    private String id ;
    private String photo;
    private String name ;
    private String lastname;
    private String email ;
    private String password ;
    private String address ;
    private String tc  ;
    private String phone ;
    private String dob;
    private ArrayList<Flight> flightsID;
    private ArrayList<Content> favs;
    private ArrayList<User> friends;

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTc() {
        return tc;
    }

    public void setTc(String tc) {
        this.tc = tc;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public ArrayList<Flight> getFlightsID() {
        return flightsID;
    }

    public void setFlightsID(ArrayList<Flight> flightsID) {
        this.flightsID = flightsID;
    }

    public ArrayList<Content> getFavs() {
        return favs;
    }

    public void setFavs(ArrayList<Content> favs) {
        this.favs = favs;
    }

    public ArrayList<User> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<User> friends) {
        this.friends = friends;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", photo='" + photo + '\'' +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", tc='" + tc + '\'' +
                ", phone='" + phone + '\'' +
                ", dob='" + dob + '\'' +
                ", flightsID=" + flightsID +
                ", favs=" + favs +
                ", friends=" + friends +
                '}';
    }
}
