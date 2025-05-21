package com.cloudbee.trainticket.service;

import com.cloudbee.trainticket.model.Ticket;
import com.cloudbee.trainticket.model.TicketRequest;
import com.cloudbee.trainticket.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TrainTicketService {

    private final Map<String, Ticket> ticketMap = new HashMap<>();
    private int seatCounterA = 1;
    private int seatCounterB = 1;
    private final int maxSeatsPerSection = 50;

    public Ticket bookTicket(TicketRequest ticketRequest) {

        User user = new User(ticketRequest.getFirstName(), ticketRequest.getLastName(), ticketRequest.getEmail());
        String email = user.getEmail();

        if (ticketMap.containsKey(email)) {
            return ticketMap.get(email); // already booked
        }

        String section;
        String seatNumber;

        if (seatCounterA <= maxSeatsPerSection) {
            section = "A";
            seatNumber = section + seatCounterA++;
        } else if (seatCounterB <= maxSeatsPerSection) {
            section = "B";
            seatNumber = section + seatCounterB++;
        } else {
            throw new IllegalStateException("Train is fully booked.");
        }

        Ticket ticket = new Ticket(ticketRequest.getSourceCity(), ticketRequest.getDestinationCity(), ticketRequest.getTicketPrice(), user, section, seatNumber);
        ticketMap.put(email, ticket);
        return ticket;
    }

    public Ticket getReceipt(String email) {
        return ticketMap.get(email);
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

    public boolean removeUser(String email) {
        return ticketMap.remove(email) != null;
    }

    public boolean modifySeat(String email, String newSection) {
        Ticket ticket = ticketMap.get(email);
        if (ticket == null) return false;

        String newSeatNumber;
        if (newSection.equalsIgnoreCase("A") && seatCounterA <= maxSeatsPerSection) {
            newSeatNumber = newSection + seatCounterA++;
        } else if (newSection.equalsIgnoreCase("B") && seatCounterB <= maxSeatsPerSection) {
            newSeatNumber = newSection + seatCounterB++;
        } else {
            return false; // no seats available in desired section
        }

        ticket.setSection(newSection);
        ticket.setSeatNumber(newSeatNumber);
        return true;
    }
}
