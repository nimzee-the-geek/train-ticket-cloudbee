package com.cloudbee.trainticket.controller;

import com.cloudbee.trainticket.exception.UserNotFoundException;
import com.cloudbee.trainticket.model.Ticket;
import com.cloudbee.trainticket.model.TicketRequest;
import com.cloudbee.trainticket.service.TrainTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/train/ticket")
public class TrainTicketController {

    @Autowired
    private TrainTicketService trainTicketService;

    @PostMapping("/book")
    public ResponseEntity<Ticket> bookTicket(@RequestBody TicketRequest request) {
        Ticket ticket = trainTicketService.bookTicket(request);
        return ResponseEntity.ok(ticket);
    }

    @GetMapping("/receipt")
    public ResponseEntity<Ticket> getReceiptByEmail(@RequestParam String email) {
        try {
            Ticket ticket = trainTicketService.getReceipt(email);
            return ResponseEntity.ok(ticket);
        } catch (UserNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/section")
    public ResponseEntity<List<Ticket>> getUsersBySection(@RequestParam String section) {
        List<Ticket> users = trainTicketService.getUsersBySection(section);
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> removeUser(@RequestParam String email) {
        try {
            trainTicketService.removeUser(email);
            return ResponseEntity.ok("User removed successfully.");
        } catch (UserNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PutMapping("/modify")
    public ResponseEntity<?>  modifySeat(@RequestParam String email, @RequestParam String section) {
        try {
            Ticket updatedTicket = trainTicketService.modifyUserSeat(email, section);
            return ResponseEntity.ok(updatedTicket);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("User not found");
        }
    }
}
