package com.dajungdagam.dg.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    private String imagePath;

    private String imageName;

    private String uuid;

    private String imageType;

    private Long imageSize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @JsonBackReference
    private Post post;

    @Builder
    public Image(Long id, String imagePath, String imageName, String uuid,
                 String imageType, Long imageSize, Post post) {
        this.id = id;
        this.imagePath = imagePath;
        this.imageName = imageName;
        this.uuid = uuid;
        this.imageType = imageType;
        this.imageSize = imageSize;
        this.post = post;
    }

}
