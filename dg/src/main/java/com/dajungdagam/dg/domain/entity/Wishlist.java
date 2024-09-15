package com.dajungdagam.dg.domain.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@ToString
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Wishlist {
    @Id
    @GeneratedValue
    @Column(name = "wishlist_id")
    private Long id;


    @OneToMany(fetch = FetchType.LAZY)
    @Setter
    @Getter
    //@JsonManagedReference
    @JsonIgnore
    private List<Post> tradePosts;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime createdTime;

    @OneToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;


    public void addTradePost(Post tradePost){
        tradePosts.add(tradePost);
    }

    @Builder
    public Wishlist(Long id, List<Post> tradePosts, LocalDateTime createdTime, User user) {
        this.id = id;
        this.tradePosts = tradePosts;
        this.createdTime = createdTime;
        this.user = user;
    }

    public Wishlist(User user) {
        this.user = user;
    }

    public Wishlist() {

    }
}
