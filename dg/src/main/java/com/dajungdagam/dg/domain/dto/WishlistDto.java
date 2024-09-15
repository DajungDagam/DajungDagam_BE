package com.dajungdagam.dg.domain.dto;

import com.dajungdagam.dg.domain.entity.Post;
import com.dajungdagam.dg.domain.entity.User;
import com.dajungdagam.dg.domain.entity.Wishlist;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter // Setter랑 ToString 없으면 DB 내에 저장이 안되더라.
@ToString
@NoArgsConstructor
public class WishlistDto {
    private Long id;
    private int userId;
    private int postCategory;
    private Long postId;
    private LocalDateTime createdTime;

    @Builder
    public WishlistDto(Long id, int userId, int postCategory, Long postId, LocalDateTime createdTime) {
        this.id = id;
        this.userId = userId;
        this.postCategory = postCategory;
        this.postId = postId;
        this.createdTime = createdTime;
    }
}
