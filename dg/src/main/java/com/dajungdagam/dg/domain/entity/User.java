package com.dajungdagam.dg.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity(name="users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private int id;

    @Column(unique = true, length = 40)
    @Nullable
    private String email;

    @Column(unique = true, length = 10)
    @Nullable
    private String nickName;

    @Column(unique = true, length = 10)
    @Nullable
    private String kakaoName;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="area_id")
    @JsonIgnore
    private Area area;

    @Column
    private RoleType role;

    @Column(length = 50)
    private String info;

    @Builder
    public User(int id, String email, String nickName, String kakaoName, Area area, RoleType role, String info) {
        this.id = id;
        this.email = email;
        this.nickName = nickName;
        this.kakaoName = kakaoName;
        this.area = area;
        this.role = role;
        this.info = info;
    }

    public User(String kakaoName, RoleType role) {
        this.kakaoName = kakaoName;
        this.role = role;
    }

    public User() {
    }
}
