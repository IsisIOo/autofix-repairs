package com.example.autofixrepairs.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//ESTE ES EL MICROSERVICIO 2


@Data
@NoArgsConstructor
@AllArgsConstructor

public class RepairList {
    private Long id;

    private String repairType;
    private double gasolineAmount;
    private double dieselAmount;
    private double hibridAmount;
    private double electricAmount;

}
