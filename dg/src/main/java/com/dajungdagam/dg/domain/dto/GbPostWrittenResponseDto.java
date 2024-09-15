package com.dajungdagam.dg.domain.dto;

import com.dajungdagam.dg.domain.entity.Area;
import com.dajungdagam.dg.domain.entity.TradeStatus;
import com.dajungdagam.dg.domain.entity.User;
import com.dajungdagam.dg.domain.entity.Wishlist;

import java.time.LocalDateTime;

// 사진빼고 받기
// 사진은 컨트롤러에서 받음
public class GbPostWrittenResponseDto {
    private int postType;
    private String title;
    private String itemCategory;
    private int price;
    private String content;
    private String guName;
    private String dongName;
    private String chatLink;
    private int personCount;
    private LocalDateTime deadline;

}
