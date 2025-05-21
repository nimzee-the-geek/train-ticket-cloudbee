package com.cloudbee.trainticket.controller;

import com.cloudbee.trainticket.model.Ticket;
import com.cloudbee.trainticket.model.TicketRequest;
import com.cloudbee.trainticket.service.TrainTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/train/ticket")
public class TrainTicketController {

    @Autowired
    private TrainTicketService trainTicketService;

    @PostMapping("/book")
    public Ticket bookTicket(@RequestBody TicketRequest request) {
        return trainTicketService.bookTicket(request);
    }

    @GetMapping("/receipt")
    public Ticket getReceiptByEmail(@RequestParam String email) {
        return trainTicketService.getReceipt(email);
    }

    @GetMapping("/section/{section}")
    public List<Ticket> getUsersBySection(@PathVariable String section) {
        return trainTicketService.getUsersBySection(section);
    }

    @DeleteMapping("/remove")
    public String removeUser(@RequestParam String email) {
        boolean removed = trainTicketService.removeUser(email);
        return removed ? "User removed." : "User not found.";
    }

    @PutMapping("/modify")
    public String modifySeat(@RequestParam String email, @RequestParam String section) {
        boolean success = trainTicketService.modifySeat(email, section);
        return success ? "Seat modified." : "Modification failed.";
    }
}
