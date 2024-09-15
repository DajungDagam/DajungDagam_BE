package com.dajungdagam.dg.domain.dto;

import com.dajungdagam.dg.domain.entity.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@ToString
public class UserKakaoLoginResponseDto {

    private HttpStatus httpStatus;
    private String jwtToken;
    private User user;
    private boolean newUser;

    public UserKakaoLoginResponseDto(HttpStatus httpStatus, String jwtToken, User user, boolean newUser) {
        this.httpStatus = httpStatus;
        this.jwtToken = jwtToken;
        this.user = user;
        this.newUser = newUser;
    }
}
