package moki.manager.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleMonth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private User user;

    private Integer year;

    private Integer month;

    private Integer count;

    private Integer sum;

    private Integer time0;
    private Integer time1;
    private Integer time2;
    private Integer time3;
    private Integer time4;
    private Integer time5;
    private Integer time6;
    private Integer time7;
    private Integer time8;
    private Integer time9;
    private Integer time10;
    private Integer time11;
    private Integer time12;
    private Integer time13;
    private Integer time14;
    private Integer time15;
    private Integer time16;
    private Integer time17;
    private Integer time18;
    private Integer time19;
    private Integer time20;
    private Integer time21;
    private Integer time22;
    private Integer time23;
}
