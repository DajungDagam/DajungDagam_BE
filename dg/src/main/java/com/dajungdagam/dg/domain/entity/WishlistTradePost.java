package com.dajungdagam.dg.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class WishlistTradePost {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "wishlist")
    private Wishlist wishlist;

    @ManyToOne
    @JoinColumn(name = "tradePost")
    private Post tradePost;

}
