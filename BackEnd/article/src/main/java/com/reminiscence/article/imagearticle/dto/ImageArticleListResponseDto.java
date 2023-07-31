package com.reminiscence.article.imagearticle.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class ImageArticleListResponseDto {

    private String imageLink;
    private Long loverCnt;
    private LocalDateTime createdDate;
    private String author;
    private Long loverYn;

    public ImageArticleListResponseDto(String imageLink, Long loverCnt, LocalDateTime createdDate, String author, Long loverYn) {
        this.imageLink = imageLink;
        this.loverCnt = loverCnt;
        this.createdDate = createdDate;
        this.author = author;
        this.loverYn = loverYn;
    }

    public ImageArticleListResponseDto(String imageLink, Long loverCnt, LocalDateTime createdDate, String author) {
        this.imageLink = imageLink;
        this.loverCnt = loverCnt;
        this.createdDate = createdDate;
        this.author = author;
        this.loverYn = 0L;
    }
    public void changeLoverYn(Long loverYn){
        this.loverYn = loverYn;
    }
}
