package com.dajungdagam.dg.service;

import com.dajungdagam.dg.domain.entity.*;
import com.dajungdagam.dg.domain.dto.UserKakaoLoginResponseDto;
import com.dajungdagam.dg.domain.dto.UserResponseDto;
import com.dajungdagam.dg.jwt.jwtTokenProvider;
import com.dajungdagam.dg.repository.AreaJpaRepository;
import com.dajungdagam.dg.repository.UserJpaRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserJpaRepository repository;

    @Autowired
    private WishlistService wishlistService;

    @Autowired
    private AreaJpaRepository areaRepository;

    @Autowired
    private PostService postService;

    @Value("${jwt.secret}")
    private String secretKey;

    private Long expiredMs = 1000* 60 * 60l;

    private static int userIdx = 0;

    public UserKakaoLoginResponseDto kakaoLogin(Map<String, Object> userInfo) {
        boolean newUser = false;
        
        String kakaoName = (String)userInfo.get("kakaoName");
        log.info("kakaoName: "+kakaoName);

        UserResponseDto userResponseDto = findByUserKakaoNickName(kakaoName);

        if(userResponseDto == null) {
            // 기존에 등록된 유저가 아니면, DB에 저장 후 다시 불러오기
            signUp(userInfo);
            userResponseDto = findByUserKakaoNickName(kakaoName);
            
            newUser = true;
        }

        // JWT 토큰 발행
        String token = jwtTokenProvider.createToken(kakaoName, secretKey, expiredMs);
        return new UserKakaoLoginResponseDto(HttpStatus.OK, token, userResponseDto.getUser(), newUser);
    }

    public UserResponseDto findByUserKakaoNickName(String kakaoName){
        User user = repository.findByKakaoName(kakaoName);
        //log.info("user: " + user.getKakaoName());
        if(user == null){
            return null;
        }
        return new UserResponseDto(user);
    }

    public User findByUserId(int userId){
        Optional<User> userObj = repository.findById(userId);
        if(userObj.isEmpty()){
            log.error("유저가 없습니다.");
            return null;
        }

        return userObj.get();
    }

    // 회원가입 시, 찜목록 만들기
    @Transactional
    public int signUp(Map<String, Object> userInfo) {
        int id = 0;
        String kakaoName = (String)userInfo.get("kakaoName");
        log.info(kakaoName + " in userInfo.");
        try{
            User user = new User(kakaoName, RoleType.USER);
            log.info(user.getKakaoName() + " 가 저장되었습니다.");
            id = repository.save(user).getId();

            Wishlist wishlist = wishlistService.addWishlist(user.getKakaoName());
            log.info("새로운 회원 로그인 만들어짐.");
            log.info("찜목록: " + wishlist.toString());

        } catch(Exception e){
            System.out.println(e);
        }
        return id;
    }


    // 유저 별명 업데이트
    @Transactional
    public int updateUserNickName(UserResponseDto userResponseDto, int userId, String nickName) {
        int id = -1;
        try{

            Optional<User> userObj = repository.findById(userId);
            User user = userObj.get();

            // 요청 보낸 유저와 같은지 체크
            if(!isSameUser(userId, userResponseDto))   throw new Exception("권한이 없습니다.");

            user.setNickName(nickName);

            id = repository.save(user).getId();

        } catch(Exception e){
            e.getStackTrace();
            return id;
        }

        return id;

    }

    // 유저 사는곳 업데이트

    @Transactional
    public int updateUserArea(UserResponseDto userResponseDto, int userId, String gu, String dong){
        int id = -1;
        try{

            Optional<User> userObj = repository.findById(userId);
            User user = userObj.get();

            // 요청 보낸 유저와 같은지 체크
            if(!isSameUser(userId, userResponseDto))   throw new Exception("권한이 없습니다.");

            Area area = areaRepository.findByGuNameAndDongName(gu, dong);
            user.setArea(area);

            id = repository.save(user).getId();

        } catch(Exception e){
            e.getStackTrace();
        }

        return id;
    }

    @Transactional
    public int updateUserInfo(UserResponseDto userResponseDto, int userId, String info) {
        int id = -1;
        try{

            Optional<User> userObj = repository.findById(userId);
            User user = userObj.get();

            // 요청 보낸 유저와 같은지 체크
            if(!isSameUser(userId, userResponseDto))   throw new Exception("권한이 없습니다.");

            user.setInfo(info);

            id = repository.save(user).getId();

        } catch(Exception e){
            e.getStackTrace();
        }

        return id;
    }


    @Transactional
    public boolean deleteUser(UserResponseDto userResponseDto, int userId) {
        try {
            Optional<User> userObj = repository.findById(userId);
            log.info("userObj: " + userObj.toString());
            User user = userObj.get();


            // 요청 보낸 유저와 같은지 체크
            if(!isSameUser(userId, userResponseDto))   throw new Exception("권한이 없습니다.");

            boolean res = postService.deleteAllPost(user);
            Wishlist wishlist = wishlistService.getWishlistByUserId(userId);

            repository.deleteById(userId);
            wishlistService.deleteWishlistTable(wishlist);

        if (!res) throw new Exception("User delete failed!");

        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }

        return true;
    }
//    @Transactional
//    public boolean deleteUser(UserResponseDto userResponseDto) {
//        try {
//            User user = userResponseDto.getUser();
//
//            boolean res = tradePostService.deleteAllPost(user);
//            if (!res) throw new Exception("User delete failed!");
//
//        } catch (Exception e) {
//            log.error(e.getMessage());
//            return false;
//        }
//
//        return true;
//    }



    public static boolean isSameUser(int userId, UserResponseDto userResponseDto){
        User user = userResponseDto.getUser();

        return user.getId() == userId;
    }

    public static boolean isSameUser(UserResponseDto authUserResponseDto, UserResponseDto wishlistResponseDto) {
        User authUser = authUserResponseDto.getUser();
        User wishUser = wishlistResponseDto.getUser();

        return authUser.getId() == wishUser.getId();
    }


}
