package com.example.ticketapp.flight;

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Flight{
    private String id=UUID.randomUUID().toString();
    private String company;
    private String logo;
    private String departure;
    private String arrive;
    private String date;
    private String departureTime;
    private String arriveTime;
    private int price;
    private ArrayList<String> passengers;
    private ArrayList<String> seats;

    public Flight() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getArrive() {
        return arrive;
    }

    public void setArrive(String arrive) {
        this.arrive = arrive;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public ArrayList<String> getPassengers() {
        return passengers;
    }

    public void setPassengers(ArrayList<String> passengers) {
        this.passengers = passengers;
    }


    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }
    @Override
    public String toString() {
        return "Flight{" +
                "id='" + id + '\'' +
                ", departure='" + departure + '\'' +
                ", arrive='" + arrive + '\'' +
                ", date=" + date +
                ", price=" + price +
                ", passengers=" + passengers +
                '}';
    }
}
