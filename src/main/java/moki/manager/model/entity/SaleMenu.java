package moki.manager.model.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class SaleMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private LocalDate localDate;

    private Integer americano;

    private Integer espresso;

    private Integer iceTea;

    private Integer cappuccino;

    private Integer latte;
}
