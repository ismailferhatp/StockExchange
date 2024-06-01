package com.ing.stockexchange.entity;

import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
@Table(name = "USER_INFO")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;

}
