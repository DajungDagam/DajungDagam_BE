package com.dajungdagam.dg.domain.dto;

import com.dajungdagam.dg.domain.entity.Area;
import com.dajungdagam.dg.domain.entity.ItemCategory;
import com.dajungdagam.dg.domain.entity.TradeStatus;
import com.dajungdagam.dg.domain.entity.User;
import lombok.*;

@Getter
@Setter // Setter랑 ToString 없으면 DB 내에 저장이 안되더라.
@ToString
@NoArgsConstructor
public class TradePostSimpleDto {

    private Area area;
    private String title;
    private String content;
    private TradeStatus tradeStatus;
    private String chatLink;
    private ItemCategory itemCategory;

    @Builder
    public TradePostSimpleDto(Area area, String title, String content,
                              TradeStatus tradeStatus, String chatLink, ItemCategory itemCategory) {
        this.area = area;
        this.title = title;
        this.content = content;
        this.tradeStatus = tradeStatus;
        this.chatLink = chatLink;
        this.itemCategory = itemCategory;
    }
}
