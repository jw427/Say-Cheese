package com.reminiscence.article.image.service;

import com.reminiscence.article.common.Pagination;
import com.reminiscence.article.config.auth.UserDetail;
import com.reminiscence.article.domain.Image;
import com.reminiscence.article.domain.ImageOwner;
import com.reminiscence.article.domain.ImageTag;
import com.reminiscence.article.domain.NoticeArticle;
import com.reminiscence.article.exception.customexception.ImageException;
import com.reminiscence.article.exception.message.ImageExceptionMessage;
import com.reminiscence.article.image.dto.ImageWriteRequestDto;
import com.reminiscence.article.image.dto.OwnerImageListResponseDto;
import com.reminiscence.article.image.dto.RandomTagResponseDto;
import com.reminiscence.article.image.repository.ImageOwnerRepository;
import com.reminiscence.article.image.repository.ImageRepository;
import com.reminiscence.article.image.repository.ImageTagRepository;
import com.reminiscence.article.image.repository.TagRepository;
import com.reminiscence.article.image.vo.ImageVo;
import com.reminiscence.article.notice.dto.NoticeArticleListResponseDto;
import com.reminiscence.article.notice.vo.NoticeArticleVo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageServiceImpl implements ImageService{
    private final ImageRepository imageRepository;
    private final ImageOwnerRepository imageOwnerRepository;
    private final TagRepository tagRepository;
    private final ImageTagRepository imageTagRepository;


    @Override
    public OwnerImageListResponseDto getReadRecentOwnImages(Long memberId, Pageable requestPageable) {
        int page = requestPageable.getPageNumber();
        if(page<=0){
            page=1;
        }
        Pageable pageable= PageRequest.of(page-1, Pagination.DEFAULT_OWN_IMAGE_PER_PAGE_SIZE, Sort.Direction.DESC,"id");
        Optional<Page<ImageVo>> images = imageRepository.findRecentOwnerImage(memberId, pageable);
        images.orElseThrow(()->
                new ImageException(ImageExceptionMessage.NOT_FOUND_IMAGE));

        OwnerImageListResponseDto ownerImageListResponseDto = new OwnerImageListResponseDto(page, images.get().getTotalPages(), images.get().getTotalElements(), images.get().getContent());
        
        return ownerImageListResponseDto;
    }

    @Override
    public List<RandomTagResponseDto> getRandomTags() {
        Optional<List<RandomTagResponseDto>> randomTags = tagRepository.findRandomTags();
        randomTags.orElseThrow(()->
                new ImageException(ImageExceptionMessage.NOT_FOUND_RANDOM_TAGS));
        return randomTags.get();
    }

    // 이미지 소유자 저장 기능 추가 필요!!
    // 방 참여자 로직이 정해지지 않아서 나중에 추가해야 한다.
    @Override
    public void saveImage(UserDetail userDetail, ImageWriteRequestDto requestDto) {
        String fileType = getFileType(requestDto.getImageName());
        String name = getFileName(requestDto.getImageName());
        Image image = Image.builder()
                .link(requestDto.getImageLink())
                .type(fileType)
                .name(name)
                .build();
        imageRepository.save(image);
        for(int i=0;i<4;i++){
            imageTagRepository.save(new ImageTag(image,requestDto.getTags().get(i)));
        }

    }

    @Transactional
    @Override
    public void deleteImage(Long imageId, Long memberId) {
        Optional<ImageOwner> findImage = imageOwnerRepository.findByImageIdAndMemberId(imageId, memberId);

        findImage.orElseThrow(()-> new ImageException(ImageExceptionMessage.NOT_FOUND_IMAGE_OWNER));

        imageOwnerRepository.delete(findImage.get());
    }

    private String getFileType(String fileName){
        return fileName.substring(fileName.lastIndexOf(".")+1);
    }
    private String getFileName(String fileName){
        return fileName.substring(0, fileName.lastIndexOf("."));
    }

}