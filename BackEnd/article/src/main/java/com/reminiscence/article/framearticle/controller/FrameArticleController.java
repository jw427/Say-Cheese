package com.reminiscence.article.framearticle.controller;

import com.reminiscence.article.config.auth.UserDetail;
import com.reminiscence.article.framearticle.dto.FrameArticleAndMemberRequestDto;
import com.reminiscence.article.framearticle.dto.FrameArticleRequestDto;
import com.reminiscence.article.framearticle.service.FrameArticleService;
import com.reminiscence.article.message.Response;
import com.reminiscence.article.message.custom_message.FrameResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/article/frame")
public class FrameArticleController {
    private final FrameArticleService frameArticleService;

    /**
     * 프레임 저장
     * @param
     * userDetail : JWT 토큰으로 인증된 사용자 정보
     * @param
     * frameArticleRequestDto : 프레임 정보를 담은 Dto
     *     frameName : 프레임 파일 이름
     *     frameType : 프레임 타입(Frame, Image)
     * @return
     * Response : HttpStatus.OK
     */
    @PostMapping("")
    public ResponseEntity<Response> saveImage(@AuthenticationPrincipal UserDetail userDetail,
                                              @Valid @RequestBody FrameArticleRequestDto frameArticleRequestDto) {
        FrameArticleAndMemberRequestDto frameArticleAndMemberRequestDto= FrameArticleAndMemberRequestDto.builder()
                .member(userDetail.getMember())
                .frameArticleRequestDto(frameArticleRequestDto)
                .build();
        frameArticleService.saveFrame(frameArticleAndMemberRequestDto);
        return new ResponseEntity<>(Response.of(FrameResponseMessage.FRAME_WRITE_SUCCESS),HttpStatus.OK);
    }
}
