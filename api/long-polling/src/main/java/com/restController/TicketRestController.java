package com.restController;

import com.LongPolling.Overseer;
import com.LongPolling.State.RequestPromise;
import com.entity.Ticket;
import com.exceptionHandlingStuff.EmployeeNotFoundException;
import com.services.TicketServiceInterface;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/ticket")
public class TicketRestController {

    private final TicketServiceInterface ticketService;
    private final Overseer overseer;

    @Autowired
    public TicketRestController(TicketServiceInterface ticketService, Overseer overseer) {
        this.ticketService = ticketService;
        this.overseer = overseer;
    }

    // subscribe for data to be sent back when available
    @NotNull
    @GetMapping("/subscribe")
    public RequestPromise handleAsync(HttpSession session){
        return overseer.subscribe(
                Ticket.class.getName(),
                session,
                ticketService);
    }

    @NotNull
    @GetMapping("/trigger/{ticketID}")
    public ResponseEntity<?> updateTicket(@PathVariable("ticketID") int ticketID){
        Ticket temp = ticketService.getTicket(ticketID);
        ticketService.addTicket(temp);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/ticket/{ticketID}")
    public ResponseEntity<Ticket> getTicket(@PathVariable("ticketID") int ticketID){
        Ticket returnedTicket = ticketService.getTicket(ticketID);
        if(returnedTicket !=null){
            return ResponseEntity.ok().body(returnedTicket);
        }
        else throw new EmployeeNotFoundException("Employee not found: " + ticketID);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Ticket>> getAllTickets(){
        return ResponseEntity.ok().body(ticketService.getAllTickets());
    }


}
