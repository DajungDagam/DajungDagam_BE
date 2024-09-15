package com.dajungdagam.dg.Controller;

import com.dajungdagam.dg.domain.dto.*;
import com.dajungdagam.dg.domain.entity.Area;
import com.dajungdagam.dg.domain.entity.ItemCategory;
import com.dajungdagam.dg.domain.entity.TradeStatus;
import com.dajungdagam.dg.service.PostService;
import com.dajungdagam.dg.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
public class GroupBuyingController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    public GroupBuyingController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping("/group-buying")
    public ResponseEntity<List<PostDto>> list() {
        List<PostDto> postDtoList = postService.getPostlist(0);

        return new ResponseEntity<>(postDtoList, HttpStatus.OK);
    }

    @GetMapping("/group-buying/like-posts")
    public ResponseEntity<List<TradePostSummaryDto>> liked_list() {

        List<TradePostSummaryDto> likePostsSummaryDtos = postService.getLikePosts(0);

        return new ResponseEntity<>(likePostsSummaryDtos, HttpStatus.OK);
    }

    @PostMapping(value = "/group-buying/posts")
    public ResponseEntity<String> write(@RequestPart PostWriteDto postWriteDto, Authentication authentication, @RequestPart MultipartFile[] images) throws IOException {

        try {
            if(authentication == null)
                throw new Exception("authentication is null. non user Info");

            String kakaoName = authentication.getName();
            UserResponseDto userResponseDto = userService.findByUserKakaoNickName(kakaoName);
//            postWriteDto.setUser(userResponseDto.getUser());

        }catch(Exception e){
            e.getStackTrace();
        } finally {
            postService.savePost(postWriteDto, images);
        }

        return ResponseEntity.ok().body("게시글 생성 완료");
    }

    @GetMapping("/group-buying/posts/{post_id}")
    public ResponseEntity<PostDto> detail(@PathVariable("post_id") Long post_id) {
        postService.updateView(post_id);
        PostDto postDto = postService.getPost(post_id);

        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    @GetMapping("/group-buying/posts/update/{post_id}")
    public ResponseEntity<GbPostSimpleDto> edit(@PathVariable("post_id") Long post_id) {
        PostDto postDto = postService.getPost(post_id);

        ModelMapper modelMapper = new ModelMapper();
        GbPostSimpleDto gbPostSimpleDto = modelMapper.map(postDto, GbPostSimpleDto.class);

        return ResponseEntity.ok(gbPostSimpleDto);
    }

    @PatchMapping("/group-buying/posts/update/{post_id}")
    public ResponseEntity<?> update(@PathVariable("post_id") Long post_id, PostDto postDto,
                                    @RequestPart MultipartFile[] images) {
        postDto.setId(post_id);
        postService.updatePost(postDto);

        return ResponseEntity.ok().body("게시글 수정 완료");
    }


    @DeleteMapping("group-buying/posts/{post_id}")
    public ResponseEntity<?> delete(@PathVariable("post_id") Long post_id) {
        postService.deletePost(post_id);

        return ResponseEntity.ok().body("게시글 삭제 완료");
    }

    @GetMapping("/group-buying/posts/search")
    public ResponseEntity<List<PostDto>> search(@RequestParam String keyword) {
        List<PostDto> postDtoList = postService.searchPosts(keyword, 0);

        return new ResponseEntity<>(postDtoList, HttpStatus.OK);
    }

    @GetMapping("/group-buying/posts/category/{itemCategory}")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable ItemCategory itemCategory) {
        List<PostDto> postsByCategory = postService.getPostsByCategory(itemCategory,0);
        return new ResponseEntity<>(postsByCategory, HttpStatus.OK);
    }


    @GetMapping("/group-buying/posts/status/{tradeStatus}")
    public ResponseEntity<List<PostDto>> getPostsByStatus(@PathVariable TradeStatus tradeStatus) {
        List<PostDto> postsByStatus = postService.getPostsByStatus(tradeStatus,0);
        return new ResponseEntity<>(postsByStatus, HttpStatus.OK);
    }

    @GetMapping("/group-buying/posts/area/{area}")
    public ResponseEntity<List<PostDto>> getPostsByArea(@PathVariable Area area) {
        List<PostDto> postsByArea = postService.getPostsByArea(area,0);
        return new ResponseEntity<>(postsByArea, HttpStatus.OK);
    }

}