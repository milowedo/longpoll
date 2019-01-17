package com.dao;

import com.entity.Ticket;

import java.util.List;

public interface TicketDaoInterface {

    public void addTicket(Ticket ticket);
    public Ticket getTicket(int ticketID);

    List<Ticket> getAllTickets();
}
