package com.services;

import com.LongPolling.ServicePoll;
import com.dao.TicketDaoInterface;
import com.entity.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
public class TicketServiceImpl extends ServicePoll implements TicketServiceInterface{

    private final TicketDaoInterface ticketDaoInterface;

    @Autowired
    public TicketServiceImpl(TicketDaoInterface ticketDaoInterface) {
        this.ticketDaoInterface = ticketDaoInterface;
    }

    @Override
    @Transactional
    public void addTicket(Ticket ticket) {
        ticketDaoInterface.addTicket(ticket);
        this.notifyOfChange(ticket);
    }

    @Override
    @Transactional
    public Ticket getTicket(int ticketID) {
        return ticketDaoInterface.getTicket(ticketID);
    }

    @Override
    @Transactional
    public List<Ticket> getAllTickets() {
        return ticketDaoInterface.getAllTickets();
    }


}
