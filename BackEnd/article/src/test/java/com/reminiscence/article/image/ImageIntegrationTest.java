package com.reminiscence.article.image;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reminiscence.article.domain.Member;
import com.reminiscence.article.image.dto.ImageType;
import com.reminiscence.article.image.dummy.DummyDeleteImageOwnerRequestDto;
import com.reminiscence.article.image.dummy.DummyImagePreSignRequestDto;
import com.reminiscence.article.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.sql.SQLException;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith({SpringExtension.class, RestDocumentationExtension.class})
@Transactional
public class ImageIntegrationTest {
    @Autowired
    WebApplicationContext applicationContext;

    @Autowired
    private MemberRepository memberRepository;

    MockMvc mvc;
    @Autowired
    private Environment env;

    ObjectMapper objectMapper = new ObjectMapper();
    String adminToken;
    String memberToken;
    @BeforeEach
    public void init(RestDocumentationContextProvider restDocumentation) {
        mvc= MockMvcBuilders.webAppContextSetup(applicationContext)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .apply(springSecurity())
                .apply(documentationConfiguration(restDocumentation).operationPreprocessors()
                        .withRequestDefaults(prettyPrint())
                        .withResponseDefaults(prettyPrint()))
                .build();
        Member admin = memberRepository.findById(1L).orElse(null);
        Member member = memberRepository.findById(2L).orElse(null);
        adminToken= JWT.create()
                .withClaim("memberId",String.valueOf(admin.getId()))
                .sign(Algorithm.HMAC512(env.getProperty("jwt.secret")));
        memberToken= JWT.create()
                .withClaim("memberId",String.valueOf(member.getId()))
                .sign(Algorithm.HMAC512(env.getProperty("jwt.secret")));
    }
    @Test
    @DisplayName("PreSignedUrl 생성 테스트")
    public void createPreSignedUrlTest() throws Exception{
        String imageName = "test.jpg";
        ImageType imageType = ImageType.IMAGE;
        DummyImagePreSignRequestDto.Builder builder = new DummyImagePreSignRequestDto.Builder();
        builder.imageName(imageName);
        builder.imageType(imageType);
        DummyImagePreSignRequestDto dummyRequestDto = builder.build();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer "+adminToken);
        mvc.perform(
                post("/api/image/presigned")
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dummyRequestDto)))
                .andExpect(status().isOk())
                .andDo(MockMvcRestDocumentation.document("{ClassName}/{methodName}",
                        requestHeaders(
                                headerWithName("Authorization").description("로그인 성공한 토큰 ")
                        ),
                        requestFields(
                                fieldWithPath("imageName").type(JsonFieldType.STRING).description("이미지 이름").attributes(key("constraints").value("Not Null")),
                                fieldWithPath("imageType").type(JsonFieldType.STRING).description("이미지 타입").attributes(key("constraints").value("[Image, Frame, Profile]"))
                        ),
                        responseFields(
                                fieldWithPath("preSignUrl").type(JsonFieldType.STRING).description("이미지 업로드 URL"),
                                fieldWithPath("fileName").type(JsonFieldType.STRING).description("UUID로 저장될 파일 이름")
                        )
                ));
    }
    @Test
    @DisplayName("이미지 소유자 삭제 테스트")
    public void deleteImageOwnerSuccessTest() throws Exception {
        Long imageId = 2L;
        DummyDeleteImageOwnerRequestDto.Builder builder = new DummyDeleteImageOwnerRequestDto.Builder();
        builder.imageId(imageId);
        DummyDeleteImageOwnerRequestDto dummyRequestDto = builder.build();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer "+ memberToken);
        mvc.perform(delete("/api/image")
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(dummyRequestDto)))
                .andExpect(status().isOk())
                .andDo(MockMvcRestDocumentation.document("{ClassName}/{methodName}",
                        requestHeaders(
                                headerWithName("Authorization").description("로그인 성공한 토큰 ")
                        ),
                        requestFields(
                                fieldWithPath("imageId").type(JsonFieldType.NUMBER).description("이미지 ID").attributes(key("constraints").value("Not Null"))
                        ),
                        responseFields(
                                fieldWithPath("message").type(JsonFieldType.STRING).description("이미지 소유자 삭제 성공 메시지")
                        )
                ));
    }
}
