package com.dajungdagam.dg.domain.dto;

import com.dajungdagam.dg.domain.entity.TradeStatus;
import com.dajungdagam.dg.domain.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@Setter // Setter랑 ToString 없으면 DB 내에 저장이 안되더라.
@ToString
@NoArgsConstructor
public class TradePostSummaryDto {

    private Long id;
    @JsonIgnore
    private User user;
    private String title;
    private String tradeArea;
    private String content;
    private int viewCount;
    private Long wishlistCount;
    private TradeStatus tradeStatus;
    private int postType;

    @Builder
    public TradePostSummaryDto(Long id, User user, String title, String tradeArea,
                               String content, int viewCount, Long wishlistCount,
                               TradeStatus tradeStatus, int postType) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.tradeArea = tradeArea;
        this.content = content;
        this.viewCount = viewCount;
        this.wishlistCount = wishlistCount;
        this.tradeStatus = tradeStatus;
        this.postType = postType;
    }
}