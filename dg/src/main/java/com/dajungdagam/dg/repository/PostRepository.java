package com.dajungdagam.dg.repository;

import com.dajungdagam.dg.domain.dto.PostDto;
import com.dajungdagam.dg.domain.entity.Area;
import com.dajungdagam.dg.domain.entity.ItemCategory;
import com.dajungdagam.dg.domain.entity.Post;
import com.dajungdagam.dg.domain.entity.TradeStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByTitleContainingAndPostType(String keyword, int postType);

    List<Post> findByUserId(int userId);

    List<Post> findAllByUserId(int userId);

    @Query("SELECT p FROM Post p WHERE p.postType = :postType ORDER BY p.wishlistCount DESC")
    List<Post> findTop3ByOrderByWishlistCountDesc(@Param("postType") int postType);

    @Modifying // 조회수 구현
    @Query("update Post p set p.viewCount = p.viewCount + 1 where p.id = :id")
    int updateviewCount(Long id);

     List<Post> findByItemCategoryAndPostType(ItemCategory itemCategory, int postType);


    List<Post> findByTradeStatusAndPostType(TradeStatus tradeStatus, int postType);


    List<Post> findByAreaAndPostType(Area area, int postType);


    List<Post> findByPostType(int postType, Sort sort);


//    // 특정 타입의 글 조회
//    List<PostDto> findByType(int postType);

}
