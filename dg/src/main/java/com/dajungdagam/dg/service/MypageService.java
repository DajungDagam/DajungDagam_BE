package com.dajungdagam.dg.service;

import com.dajungdagam.dg.domain.dto.UserWrittenPostDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MypageService {

    @Autowired
    private PostService postService;

    public void getUserWrittenPosts() {

    }

}
