package com.services;

import com.dao.TicketDaoInterface;
import com.entity.Resolvable;
import com.entity.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketServiceInterface {

    private final TicketDaoInterface ticketDaoInterface;
    private boolean isNewTicketAvailable = false;

    @Autowired
    public TicketServiceImpl(TicketDaoInterface ticketDaoInterface) {
        this.ticketDaoInterface = ticketDaoInterface;
    }

    @Override
    @Transactional
    public void addTicket(Ticket ticket) {
        ticketDaoInterface.addTicket(ticket);
        this.notifyOfChange();
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

    @Transactional
    public Optional<Resolvable> resolve() {
        if (!isNewTicketAvailable) {
            return Optional.empty();
        } else {
            TicketServiceImpl thisObj = this;
            Ticket changedEmployee = getTicket(2);
            System.out.println(changedEmployee.toString());

            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            thisObj.isNewTicketAvailable = false;
                        }
                    },
                    500
            );
            return Optional.of(changedEmployee);
        }
    }

    @Override
    public void notifyOfChange() {
        this.isNewTicketAvailable = true;
    }
}
