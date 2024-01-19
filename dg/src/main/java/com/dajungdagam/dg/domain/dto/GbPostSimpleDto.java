package com.dajungdagam.dg.domain.dto;

import com.dajungdagam.dg.domain.entity.Area;
import com.dajungdagam.dg.domain.entity.ItemCategory;
import com.dajungdagam.dg.domain.entity.TradeStatus;
import io.swagger.models.auth.In;
import lombok.*;

import java.util.Date;

@Getter
@Setter // Setter랑 ToString 없으면 DB 내에 저장이 안되더라.
@ToString
@NoArgsConstructor
public class GbPostSimpleDto {

    private Area area;
    private String title;
    private String content;
    private TradeStatus tradeStatus;
    private String chatLink;
    private ItemCategory itemCategory;
    private Integer price;
    private Integer personCount;
    private Integer personCurrCount;
    private Date deadline;
    @Builder
    public GbPostSimpleDto(Area area, String title, String content, TradeStatus tradeStatus,
                           String chatLink, ItemCategory itemCategory, Integer price,
                           Integer personCount, Integer personCurrCount, Date deadline) {
        this.area = area;
        this.title = title;
        this.content = content;
        this.tradeStatus = tradeStatus;
        this.chatLink = chatLink;
        this.itemCategory = itemCategory;
        this.price = price;
        this.personCount = personCount;
        this.personCurrCount = personCurrCount;
        this.deadline = deadline;
    }
}