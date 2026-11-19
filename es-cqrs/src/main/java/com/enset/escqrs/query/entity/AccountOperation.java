package com.enset.escqrs.query.entity;

import com.enset.escqrs.enumirations.OperationType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity @Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class AccountOperation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private double amount;
    @Enumerated(value = EnumType.STRING)
    private OperationType type;
    private String currency;
    @ManyToOne
    private Account account;
}
