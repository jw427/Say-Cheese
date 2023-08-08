package com.reminiscence.article.image.controller;

import com.reminiscence.article.config.auth.UserDetail;
import com.reminiscence.article.image.dto.*;
import com.reminiscence.article.image.service.ImageService;
import com.reminiscence.article.message.Response;
import com.reminiscence.article.message.custom_message.ImageResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;


    @GetMapping
    public ResponseEntity<OwnerImageListResponseDto> readOwnerRecentImages(
            @AuthenticationPrincipal UserDetail userDetail,
            @PageableDefault(size=10, page=1, direction = Sort.Direction.DESC) Pageable pageable){
        OwnerImageListResponseDto recentOwnImages = imageService.getReadRecentOwnImages(userDetail.getMember().getId(), pageable);
        return new ResponseEntity<>(recentOwnImages, HttpStatus.OK);
    }

    @GetMapping("/random/tag")
    public ResponseEntity<List<RandomTagResponseDto>> readRandomTag(){
        List<RandomTagResponseDto> randomTags = imageService.getRandomTags();
        return new ResponseEntity(randomTags, HttpStatus.OK);
    }


    /**
     * 이미지 정보 저장
     * @param
     * userDetail : JWT 토큰으로 인증된 사용자 정보
     * @param
     * requestDto : JWT 토큰으로 인증된 사용자 정보
     *     imageLink : 이미지 링크
     *     imageName : 이미지 파일 이름
     *     roomCode : 방 코드
     *     tags : 태그 리스트
     * @return
     * Response : HttpStatus.OK
     */

    @PostMapping
    public ResponseEntity<Response> writeImageInfo(@AuthenticationPrincipal UserDetail userDetail,
                                              @RequestBody @Valid ImageWriteRequestDto requestDto) {
        imageService.saveImage(userDetail, requestDto);
        return new ResponseEntity<>(Response.of(ImageResponseMessage.INSERT_IMAGE_SUCCESS),HttpStatus.OK);
    }

    /**
     * 이미지 소유자 삭제
     * @param
     * userDetail : JWT 토큰으로 인증된 사용자 정보
     * @param
     * requestDto : JWT 토큰으로 인증된 사용자 정보
     *     imageId : 이미지 ID
     * @return
     * Response : HttpStatus.OK
     */
    @DeleteMapping
    public ResponseEntity<Response> deleteImageOwner(@AuthenticationPrincipal UserDetail userDetail,
                                                    @RequestBody @Valid DeleteImageOwnerRequestDto requestDto) {

        imageService.deleteImage(requestDto.getImageId(), userDetail.getMember().getId());
        return new ResponseEntity<>(Response.of(ImageResponseMessage.DELETE_IMAGE_SUCCESS), HttpStatus.OK);
    }



}
