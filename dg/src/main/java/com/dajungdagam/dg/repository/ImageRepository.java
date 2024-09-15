package com.dajungdagam.dg.repository;

import com.dajungdagam.dg.domain.entity.Image;
import com.dajungdagam.dg.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByPost(Post post);

}
