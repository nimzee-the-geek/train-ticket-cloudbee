package com.cloudbee.trainticket.model;

public class Ticket {

    private String sourceCity;
    private String destinationCity;
    private Double ticketPrice;
    private User user;
    private String section; // A or B
    private String seatNumber;

    public Ticket() {
        this.sourceCity = "London";
        this.destinationCity = "France";
        this.ticketPrice = 20.0;
    }

    public Ticket(String sourceCity, String destinationCity, Double ticketPrice, User user, String section, String seatNumber) {
        this.sourceCity = (sourceCity == null || sourceCity.isBlank()) ? "London" : sourceCity;
        this.destinationCity = (destinationCity == null || destinationCity.isBlank()) ? "France" : destinationCity;
        this.ticketPrice = (ticketPrice==null) ? 20.0 : ticketPrice;
        this.user = user;
        this.section = section;
        this.seatNumber = seatNumber;
    }

    public String getSourceCity() {
        return sourceCity;
    }

    public void setSourceCity(String sourceCity) {
        this.sourceCity = sourceCity;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public Double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(Double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "sourceCity='" + sourceCity + '\'' +
                ", destinationCity='" + destinationCity + '\'' +
                ", ticketPrice=" + ticketPrice +
                ", user=" + user +
                ", section='" + section + '\'' +
                ", seatNumber=" + seatNumber +
                '}';
    }
}
