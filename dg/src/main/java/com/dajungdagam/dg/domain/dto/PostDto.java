package com.dajungdagam.dg.domain.dto;


import com.dajungdagam.dg.domain.entity.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.models.auth.In;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.*;
import java.time.LocalDate;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

@Getter
@Setter // Setter랑 ToString 없으면 DB 내에 저장이 안되더라.
@ToString
@NoArgsConstructor
public class PostDto {

    private Long id;

    @JsonIgnore
    private User user;
    private Area area;
    private String title;
    private int postType;
    private String content;
    private LocalDateTime createdTime;
    private LocalDateTime updateTime;
    private int viewCount;
    private Long wishlistCount;
    private String chatLink;
    private TradeStatus tradeStatus;
    private Wishlist wishlist;
    private ItemCategory itemCategory;
    private Integer price;
    private Integer personCount;
    private Integer personCurrCount;
    @JsonFormat(pattern = "yyyy-MM-dd") //데이터 포맷 변환
    private LocalDate deadline;


    public Post toEntity() {
        return Post.builder()
                .id(id)
                .user(user)
                .area(area)
                .title(title)
                .postType(postType)
                .content(content)
                .createdTime(createdTime)
                .updateTime(updateTime)
                .viewCount(viewCount)
                .wishlistCount(wishlistCount)
                .chatLink(chatLink)
                .tradeStatus(tradeStatus)
                .itemCategory(itemCategory)
                .price(price)
                .personCount(personCount)
                .personCurrCount(personCurrCount)
                .deadline(deadline)
                .build();
    }

    @Builder
    public PostDto(Long id, User user, Area area, String title, int postType,
                   String content, LocalDateTime createdTime, LocalDateTime updateTime, int viewCount, Long wishlistCount,
                   String chatLink, TradeStatus tradeStatus, ItemCategory itemCategory,
                   Integer price, Integer personCount, Integer personCurrCount, LocalDate deadline)

    {
        this.id = id;
        this.user = user;
        this.area = area;
        this.title = title;
        this.postType = postType;
        this.content = content;
        this.createdTime = createdTime;
        this.updateTime = updateTime;
        this.viewCount = viewCount;
        this.wishlistCount = wishlistCount;
        this.chatLink = chatLink;
        this.tradeStatus = tradeStatus;
        this.itemCategory = itemCategory;
        this.price = price;
        this.personCount = personCount;
        this.personCurrCount = personCurrCount;
        this.deadline = deadline;
    }

}