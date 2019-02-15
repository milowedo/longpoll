package com.dao;

import com.entity.Ticket;

import java.util.List;

public interface TicketDaoInterface {

    void addTicket(Ticket ticket);
    Ticket getTicket(int ticketID);
    List<Ticket> getAllTickets();
}
