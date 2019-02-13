package com.entity;

import com.LongPolling.Resolvable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "ticket")
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Ticket extends Resolvable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private int ticketID;

    @Column(name="status")
    private String ticketStatus;
}
