package com.example.Library_Management_System.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "patrons")
public class Patron {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String contactInfo;
}

