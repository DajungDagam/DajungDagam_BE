package com.dajungdagam.dg.Controller;

import com.dajungdagam.dg.domain.dto.UserResponseDto;
import com.dajungdagam.dg.domain.entity.User;
import com.dajungdagam.dg.service.UserService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class HomeController {



    @GetMapping("/")
    public ResponseEntity<String> home(Authentication authentication) {

        return ResponseEntity.ok("Welcome to the Home Page!");
    }
}
