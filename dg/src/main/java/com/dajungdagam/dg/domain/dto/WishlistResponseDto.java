package com.dajungdagam.dg.domain.dto;

import com.dajungdagam.dg.domain.entity.Post;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class WishlistResponseDto {
    private Long wishlist_id;
    private List<Post> tradePosts;

    public WishlistResponseDto(Long wishlist_id, List<Post> tradePosts) {
        this.wishlist_id = wishlist_id;
        this.tradePosts = tradePosts;
    }
}
