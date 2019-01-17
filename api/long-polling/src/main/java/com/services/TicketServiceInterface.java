package com.services;

import com.entity.Ticket;

import java.util.List;

public interface TicketServiceInterface extends ServiceInterface{

    void addTicket(Ticket ticket);
    Ticket getTicket(int ticketID);

    List<Ticket> getAllTickets();
}
