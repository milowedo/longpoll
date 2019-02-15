package com.entity;

import com.LongPolling.Resolvable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Table(name = "ticket")
@Data
@Entity(name = "Ticket")
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
