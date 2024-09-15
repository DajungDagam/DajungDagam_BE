package com.dajungdagam.dg.domain.dto;

import com.dajungdagam.dg.domain.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;


@Getter
@Setter
public class UserMypageInfoResponseDto {
    private String nickName;
    private String info;
    private String guName;
    private String dongName;

    public UserMypageInfoResponseDto(String nickName, String info, String guName, String dongName) {
        this.nickName = nickName;
        this.info = info;
        this.guName = guName;
        this.dongName = dongName;
    }
}
