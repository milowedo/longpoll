package com.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="employee")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee extends Resolvable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="employee_id")
	private int employee_id;

    @Column(name="name")
    private String name;

    @Column(name="team_id")
    private int team_id;

    @Transient
    private String teamName;

//	@Column(name="email")
//	private String email;
	

}





