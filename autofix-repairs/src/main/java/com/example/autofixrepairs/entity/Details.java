package com.example.autofixrepairs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Details {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(unique = true, nullable = false)

    private Long id;
   // private Long repair_id; //id de repair para conectarlo con lo detalles de reparacion

    private String patent; //patente del auto

    private String repairType;
    private int repairDay; //fecha de reparacion
    private int repairMonth;

    private int repairHour; //hora de reparacion

    private double totalAmount; //el costo total de las reparaciones, solo el total que implica las reparaciones en el auto
}
