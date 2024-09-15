package com.dajungdagam.dg.Controller;

import com.dajungdagam.dg.domain.dto.PostDto;
import com.dajungdagam.dg.domain.dto.UserMypageInfoResponseDto;
import com.dajungdagam.dg.domain.dto.UserResponseDto;
import com.dajungdagam.dg.domain.dto.WishlistResponseDto;
import com.dajungdagam.dg.domain.entity.User;
import com.dajungdagam.dg.domain.entity.Wishlist;
import com.dajungdagam.dg.service.PostService;
import com.dajungdagam.dg.service.UserService;
import com.dajungdagam.dg.service.WishlistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MypageController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private WishlistService wishlistService;


    // 자신만 볼 수 있게 한거 롤백
    @PostMapping("/mypage/{user_id}")
    public ResponseEntity<UserMypageInfoResponseDto> getUserInfo(@PathVariable("user_id") int user_id) {
        UserMypageInfoResponseDto userMypageInfoResponseDto = null;
        User user = userService.findByUserId(user_id);

        // kakao Name으로 받아오기
        userMypageInfoResponseDto = new UserMypageInfoResponseDto(user.getNickName(),
                user.getInfo(), user.getArea().getGuName(), user.getArea().getDongName());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        return new ResponseEntity<>(userMypageInfoResponseDto, headers, HttpStatus.OK);
    }

    @PostMapping("/mypage/{user_id}/posts")
    public ResponseEntity<List<PostDto>> getWrittenPosts(@PathVariable("user_id") int user_id) {
        List<PostDto> postDtoList = null;

        // kakao Name으로 받아오기
        postDtoList = postService.searchPostsByUserId(user_id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        return new ResponseEntity<>(postDtoList, headers, HttpStatus.OK);
    }

    @PostMapping("/mypage/{user_id}/wishlist")
    public ResponseEntity<WishlistResponseDto> getWishlist(Authentication authentication, @PathVariable("user_id") int user_id) {
        WishlistResponseDto wishlistResponseDto = null;
        try {
            if(authentication == null)
                throw new Exception("authentication is null");

            String kakaoName = authentication.getName();
            UserResponseDto userResponseDto = userService.findByUserKakaoNickName(kakaoName);

            //kakaoName으로 찾은 유저의 id와 pathVariable로 받은 id가 같은지 검증
            if(!userService.isSameUser(user_id, userResponseDto)){
                throw new Exception("user is not same");
            }

            //
            Wishlist wishlist = wishlistService.getWishlistByUserId(user_id);
            wishlistResponseDto = new WishlistResponseDto(wishlist.getId(), wishlist.getTradePosts());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

            return new ResponseEntity<>(wishlistResponseDto, headers, HttpStatus.OK);
        } catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }


}
