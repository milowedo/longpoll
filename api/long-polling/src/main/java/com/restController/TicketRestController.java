package com.restController;

import com.LongPolling.Overseer;
import com.LongPolling.RequestPromise;
import com.entity.Ticket;
import com.exceptionHandlingStuff.EmployeeNotFoundException;
import com.services.ServiceInterface;
import com.services.TicketServiceImpl;
import com.services.TicketServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/subscribe")
    public RequestPromise handleAsync(HttpSession session){
        RequestPromise output = new RequestPromise(ticketService);
        output.setSession(session);
        overseer.subscribe(output);
        return output;
    }

    @GetMapping("/trigger/{ticketID}")
    public ResponseEntity<?> updateTicket(@PathVariable int ticketID){
        Ticket temp = ticketService.getTicket(ticketID);
        ticketService.addTicket(temp);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/ticket/{ticketID}")
    public Ticket getTicket(@PathVariable int ticketID){
        Ticket returnedTicket = ticketService.getTicket(ticketID);
//        session.invalidate();
        if(returnedTicket !=null){
            return returnedTicket;
        }
        else throw new EmployeeNotFoundException("Employee not found: " + ticketID);
    }

    @GetMapping("/all")
    public List<Ticket> getAllTickets(){
        return ticketService.getAllTickets();
    }


}
