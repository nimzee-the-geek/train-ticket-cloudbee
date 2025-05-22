package com.cloudbee.trainticket.service;

import com.cloudbee.trainticket.exception.TrainFullyBookedException;
import com.cloudbee.trainticket.exception.UserNotFoundException;
import com.cloudbee.trainticket.model.Ticket;
import com.cloudbee.trainticket.model.TicketRequest;
import com.cloudbee.trainticket.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class TrainTicketService {

    private final Map<String, Ticket> ticketMap = new HashMap<>();
    private int seatCounterA = 1;
    private int seatCounterB = 1;
    private final Set<String> sectionASeats = new HashSet<>();
    private final Set<String> sectionBSeats = new HashSet<>();
    @Value("${ticket.max.seats.per.section}")
    private int maxSeatsPerSection;

    @Value("${ticket.default.source}")
    private String defaultSource;

    @Value("${ticket.default.destination}")
    private String defaultDestination;

    @Value("${ticket.default.price}")
    private Double defaultPrice;

    public Ticket bookTicket(TicketRequest ticketRequest) {

        String email = ticketRequest.getEmail().toLowerCase();

        if (ticketMap.containsKey(email)) {
            return ticketMap.get(email); // already booked
        }

        String sourceCity = Optional.ofNullable(ticketRequest.getSourceCity())
                .filter(s -> !s.isBlank())
                .orElse(defaultSource);

        String destinationCity = Optional.ofNullable(ticketRequest.getDestinationCity())
                .filter(s -> !s.isBlank())
                .orElse(defaultDestination);

        Double ticketPrice = Optional.ofNullable(ticketRequest.getTicketPrice())
                .orElse(defaultPrice);

        String section;
        String seatNumber;

        seatNumber = getRandomAvailableSeat("A");
        if (seatNumber != null) {
            section = "A";
            sectionASeats.add(seatNumber);
        } else {
            // Try Section B
            seatNumber = getRandomAvailableSeat("B");
            if (seatNumber != null) {
                section = "B";
                sectionBSeats.add(seatNumber);
            } else {
                throw new TrainFullyBookedException("Train is fully booked.");
            }
        }

        User user = new User(ticketRequest.getFirstName(), ticketRequest.getLastName(), email);
        Ticket ticket = new Ticket(sourceCity, destinationCity, ticketPrice, user, section, seatNumber);
        ticketMap.put(email, ticket);
        return ticket;
    }

    public Ticket getReceipt(String email) {

        Ticket ticket = ticketMap.get(email.toLowerCase());
        if (ticket == null) {
            throw new UserNotFoundException("User with email " + email + " not found");
        }
        return ticket;
    }

    public List<Ticket> getUsersBySection(String section) {
        List<Ticket> result = new ArrayList<>();
        for (Ticket ticket : ticketMap.values()) {
            if (ticket.getSection().equalsIgnoreCase(section)) {
                result.add(ticket);
            }
        }
        return result;
    }

    public void removeUser(String email) {

        Ticket removed = ticketMap.remove(email.toLowerCase());
        if (removed == null) {
            throw new UserNotFoundException("User with email " + email + " not found");
        }

        String seat = removed.getSeatNumber();
        if (seat.startsWith("A")) {
            sectionASeats.remove(seat);
        } else if (seat.startsWith("B")) {
            sectionBSeats.remove(seat);
        }

    }

    public Ticket modifyUserSeat(String email, String newSection) {

        Ticket ticket = ticketMap.get(email.toLowerCase());
        if (ticket == null) {
            throw new UserNotFoundException("User with email " + email + " not found");
        }

        // Remove old seat allocation
        String oldSeat = ticket.getSeatNumber();
        if (oldSeat.startsWith("A")) {
            sectionASeats.remove(oldSeat);
        } else if (oldSeat.startsWith("B")) {
            sectionBSeats.remove(oldSeat);
        }

        // Allocate new seat
        String newSeat = getRandomAvailableSeat(newSection.toUpperCase());
        if (newSeat == null) {
            throw new TrainFullyBookedException("No available seats in section " + newSection);
        }

        if (newSection.equalsIgnoreCase("A")) {
            sectionASeats.add(newSeat);
        } else {
            sectionBSeats.add(newSeat);
        }

        ticket.setSection(newSection);
        ticket.setSeatNumber(newSeat);
        return ticket;
    }


    /**
     * Helper method to find a random available seat in a section.
     * Returns seat number like "A23" or "B10", or null if no seats available.
     */
    private String getRandomAvailableSeat(String section) {
        Set<String> allocatedSeats = section.equalsIgnoreCase("A") ? sectionASeats : sectionBSeats;

        if (allocatedSeats.size() >= maxSeatsPerSection) {
            return null; // section full
        }

        int maxAttempts = 100; // to avoid infinite loops

        for (int i = 0; i < maxAttempts; i++) {
            int randomSeatNum = ThreadLocalRandom.current().nextInt(1, maxSeatsPerSection + 1);
            String candidateSeat = section.toUpperCase() + randomSeatNum;

            if (!allocatedSeats.contains(candidateSeat)) {
                return candidateSeat;
            }
        }

        // fallback: if no seat found in random tries (very unlikely), scan sequentially
        for (int i = 1; i <= maxSeatsPerSection; i++) {
            String candidateSeat = section.toUpperCase() + i;
            if (!allocatedSeats.contains(candidateSeat)) {
                return candidateSeat;
            }
        }

        return null; // no seats available
    }
}
