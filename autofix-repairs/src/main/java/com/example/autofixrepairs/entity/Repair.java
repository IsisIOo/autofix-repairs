package com.example.autofixrepairs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "repair")
@Data
@NoArgsConstructor
@AllArgsConstructor

//ESTE CORRESPONDE AL MICROSERVICIO 3
public class Repair {
    //clase para el historial de reparaciones
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(unique = true, nullable = false)
    private Long id;


    private String patent; //patente de letras y numeros

    private String admissionDateDayName; //nombre del dia de llegada
    private int admissionDateDay; //fecha de llega al taller
    private int admissionDateMonth;
    private int admissionHour;   //hora de llegada

    private String repairType; //tipo de reparacion/es


    private int departureDateDay; //fecha de salida del vehiculo
    private int departureDateMonth;
    private int departureHour; //hora de salida, asumo que deberia ser igual a la de llegada

    private int clientDateDay; //fecha en la que el cliente se lleva el vehiculo
    private int clientDateMonth;
    private int clientHour; //hora en la que el cliente se lleva el vehiculo

    private double totalAmount;
    private double totalDiscounts;
    private double totalRecharges;
    private double totalIva;
}

