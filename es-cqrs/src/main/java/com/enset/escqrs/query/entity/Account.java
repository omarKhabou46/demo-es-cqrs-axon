package com.enset.escqrs.query.entity;

import com.enset.escqrs.enumirations.AccountStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Account {
    @Id
    private String accountId;
    private double balance;
    @CreationTimestamp
    private LocalDate createdAt;
    @Enumerated(value = EnumType.STRING)
    private AccountStatus status;
    private String currency;
}
