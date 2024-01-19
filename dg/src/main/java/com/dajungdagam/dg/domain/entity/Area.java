package com.dajungdagam.dg.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "areas")
@Table
@Getter
@Setter
public class Area {

    @Id
    @GeneratedValue
    @Column(name = "area_id")

    private int id;

    @Column(name = "GUNAME")
    private String guName;

    @Column(name = "DONGNAME")
    public String dongName;
}
