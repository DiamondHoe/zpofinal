package com.example.zpofinal.models;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cheap_wine")
@Data
@NoArgsConstructor
public class Wine {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "brand")
    private String brand;
    @Column(name = "type")
    private String type;
    @Column(name = "volume")
    private double volume;
    @Column(name = "price")
    private double price;
    @Column(name = "voltage")
    private double voltage;

    public Wine(String name, String brand, String type, double volume, int price, int voltage) {
        this.name = name;
        this.brand = brand;
        this.type = type;
        this.volume = volume;
        this.price = price;
        this.voltage = voltage;
    }

    public double getVoltagePerVolume() {
        return voltage / volume;
    }

    public double getClearAlcohol(){
        return volume * (voltage/100) * 0.8;
    }
}
