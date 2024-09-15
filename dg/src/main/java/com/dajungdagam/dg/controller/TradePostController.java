package com.dajungdagam.dg.Controller;



import com.dajungdagam.dg.domain.dto.*;
import com.dajungdagam.dg.domain.entity.*;
import com.dajungdagam.dg.service.UserService;

import com.dajungdagam.dg.service.PostService;

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
public class TradePostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    public TradePostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping("/trade")
    public ResponseEntity<List<PostDto>> list() {
        List<PostDto> postDtoList = postService.getPostlist(1);

        return new ResponseEntity<>(postDtoList, HttpStatus.OK);
    }

    @GetMapping("/trade/like-posts")
    public ResponseEntity<List<TradePostSummaryDto>> liked_list() {

        List<TradePostSummaryDto> likePostsSummaryDtos = postService.getLikePosts(1);

        return new ResponseEntity<>(likePostsSummaryDtos, HttpStatus.OK);
    }

    @PostMapping(value = "/trade/posts")
    public ResponseEntity<String> write(@RequestPart PostWriteDto postWriteDto, Authentication authentication, @RequestPart(required = false) MultipartFile[] images) throws IOException {

        try {
            if(authentication == null)
                throw new Exception("authentication is null. non user Info");

            log.info("게시글 작성됨");
            log.info(postWriteDto.toString());
            log.info(images.toString());
            
            String kakaoName = authentication.getName();
            UserResponseDto userResponseDto = userService.findByUserKakaoNickName(kakaoName);


        }catch(Exception e){
            e.getStackTrace();
        } finally {
            postService.savePost(postWriteDto, images);
        }

        return ResponseEntity.ok().body("게시글 생성 완료");
    }


    @GetMapping("/trade/posts/{post_id}")
    public ResponseEntity<PostDto> detail(@PathVariable("post_id") Long post_id) {
        postService.updateView(post_id);
        PostDto postDto = postService.getPost(post_id);

        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }



    @GetMapping("/trade/posts/update/{post_id}")
    public ResponseEntity<TradePostSimpleDto> edit(@PathVariable("post_id") Long post_id) {
        PostDto postDto = postService.getPost(post_id);

        ModelMapper modelMapper = new ModelMapper();
        TradePostSimpleDto tradePostSimpleDto = modelMapper.map(postDto, TradePostSimpleDto.class);

        return ResponseEntity.ok(tradePostSimpleDto);
    }

    @PatchMapping("/trade/posts/update/{post_id}")
    public ResponseEntity<?> update(@PathVariable("post_id") Long post_id, PostDto postDto,
                                    @RequestPart MultipartFile[] images) {
        postDto.setId(post_id);
        postService.updatePost(postDto);

        return ResponseEntity.ok().body("게시글 수정 완료");
    }


    @DeleteMapping("trade/posts/{post_id}")
    public ResponseEntity<?> delete(@PathVariable("post_id") Long post_id) {
        postService.deletePost(post_id);

        return ResponseEntity.ok().body("게시글 삭제 완료");
    }

    @GetMapping("/trade/posts/search")
    public ResponseEntity<List<PostDto>> search(@RequestParam String keyword) {
        List<PostDto> postDtoList = postService.searchPosts(keyword, 1);

        return new ResponseEntity<>(postDtoList, HttpStatus.OK);
    }
    

    @GetMapping("/trade/posts/category/{itemCategory}")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable ItemCategory itemCategory) {
        List<PostDto> postsByCategory = postService.getPostsByCategory(itemCategory,1);
        return new ResponseEntity<>(postsByCategory, HttpStatus.OK);
    }


    @GetMapping("/trade/posts/status/{tradeStatus}")
    public ResponseEntity<List<PostDto>> getPostsByStatus(@PathVariable TradeStatus tradeStatus) {
        List<PostDto> postsByStatus = postService.getPostsByStatus(tradeStatus,1);
        return new ResponseEntity<>(postsByStatus, HttpStatus.OK);
    }

    @GetMapping("/trade/posts/area/{area}")
    public ResponseEntity<List<PostDto>> getPostsByArea(@PathVariable Area area) {
        List<PostDto> postsByArea = postService.getPostsByArea(area,1);
        return new ResponseEntity<>(postsByArea, HttpStatus.OK);
    }

}