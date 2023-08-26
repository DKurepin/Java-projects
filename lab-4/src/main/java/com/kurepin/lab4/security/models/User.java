package com.kurepin.lab4.security.models;

import com.kurepin.lab4.entities.Employee;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username", unique = true)
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UserStatus status;
    @JoinColumn(name = "employee_id")
    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private Employee employee;
}
