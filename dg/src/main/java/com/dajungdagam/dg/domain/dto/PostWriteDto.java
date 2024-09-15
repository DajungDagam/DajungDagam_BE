package com.dajungdagam.dg.domain.dto;

import com.dajungdagam.dg.domain.entity.*;
import com.dajungdagam.dg.repository.AreaJpaRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.time.LocalDate;

@Getter
@Setter // Setter랑 ToString 없으면 DB 내에 저장이 안되더라.
@ToString
@NoArgsConstructor
public class PostWriteDto {

    public Long id;

    public String guName;
    public String dongName;
    public String title;
    public int postType;
    public String content;
    public LocalDateTime createdTime;
    public LocalDateTime updatedTime;
    public int viewCount;
    // price
    public Integer wishlistCount;
    public String chatLink;
   @JsonFormat(pattern = "yyyy-MM-dd") //데이터 포맷 변환
    public LocalDate deadline;
    public String categoryName;

    
    public Post toEntity(Area area, ItemCategory itemCategory) {

        return Post.builder()
                .area(area)
                .itemCategory(itemCategory)
                .title(title)
                .postType(postType)
                .content(content)
                .deadline(deadline)
                .createdTime(createdTime)
                .updateTime(updatedTime)
                .viewCount(viewCount)
                .deadline(deadline)
//                .wishlistCount(wishlistCount)
                .chatLink(chatLink)
                .build();
    }


    @Builder
    public PostWriteDto(Long id, String guName, String dongName, String title, int postType, String content, LocalDateTime createdTime, LocalDateTime updatedTime, int viewCount, Integer wishlistCount, String chatLink, LocalDate deadline, String categoryName) {
        this.id = id;
        this.guName = guName;
        this.dongName = dongName;
        this.title = title;
        this.postType = postType;
        this.content = content;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
        this.viewCount = viewCount;
        this.wishlistCount = wishlistCount;
        this.chatLink = chatLink;
        this.categoryName = categoryName;
    }
}
