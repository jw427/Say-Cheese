package com.reminiscence.room.room;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reminiscence.room.domain.Member;
import com.reminiscence.room.domain.Mode;
import com.reminiscence.room.member.repository.MemberRepository;
import com.reminiscence.room.room.dto.DummyRoomCheckRequestDto;
import com.reminiscence.room.room.dto.DummyRoomCreateRequestDto;
import com.reminiscence.room.room.dto.DummyRoomDeleteRequestDto;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.sql.SQLException;
import java.time.LocalDateTime;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class RoomIntegrationTest {
    @Autowired
    WebApplicationContext applicationContext;

    @Autowired
    private MemberRepository memberRepository;

    MockMvc mvc;
    @Autowired
    private Environment env;

    ObjectMapper objectMapper = new ObjectMapper();
    String guestToken;
    String memberToken;
    @BeforeEach
    public void init(RestDocumentationContextProvider restDocumentation) throws SQLException {
        mvc= MockMvcBuilders.webAppContextSetup(applicationContext)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .apply(springSecurity())
                .apply(documentationConfiguration(restDocumentation).operationPreprocessors()
                        .withRequestDefaults(prettyPrint())
                        .withResponseDefaults(prettyPrint()))
                .build();
        Member guest = memberRepository.findById(5L).orElse(null);
        Member member = memberRepository.findById(2L).orElse(null);
        guestToken= JWT.create()
                .withClaim("memberId",String.valueOf(guest.getId()))
                .sign(Algorithm.HMAC512(env.getProperty("jwt.secret")));
        memberToken= JWT.create()
                .withClaim("memberId",String.valueOf(member.getId()))
                .sign(Algorithm.HMAC512(env.getProperty("jwt.secret")));
    }
    @Test
    @DisplayName("방 비밀번호 확인 테스트(정상)")
    public void checkRoomPasswordSuccessTest() throws Exception{
        String roomCode = "sessionA";
        DummyRoomCheckRequestDto.Builder builder = new DummyRoomCheckRequestDto.Builder();
        DummyRoomCheckRequestDto requestDto = builder.password("1234").build();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer "+ guestToken);
        mvc.perform(post("/api/room/check/{roomCode}", roomCode)
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andDo(MockMvcRestDocumentation.document("{ClassName}/{methodName}",
                        requestHeaders(
                                headerWithName("Authorization").description("로그인 성공한 토큰 ")
                        ),
                        pathParameters(
                                parameterWithName("roomCode").description("방 코드")
                        ),
                        requestFields(
                                fieldWithPath("password").description("방 비밀번호").attributes(key("constraints").value("Not Null"))
                        )
                ));

    }
    @Test
    @DisplayName("방 비밀번호 틀렸을 때 테스트(비정상)")
    public void checkRoomNotEqPasswordFailTest() throws Exception{
        String roomCode = "sessionA";
        DummyRoomCheckRequestDto.Builder builder = new DummyRoomCheckRequestDto.Builder();
        DummyRoomCheckRequestDto requestDto = builder.password("21234123").build();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer "+ guestToken);
        mvc.perform(post("/api/room/check/{roomCode}", roomCode)
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcRestDocumentation.document("{ClassName}/{methodName}",
                        requestHeaders(
                                headerWithName("Authorization").description("로그인 성공한 토큰 ")
                        ),
                        pathParameters(
                                parameterWithName("roomCode").description("방 코드")
                        ),
                        requestFields(
                                fieldWithPath("password").description("방 비밀번호").attributes(key("constraints").value("Not Null"))
                        )
                ));

    }
    @Test
    @DisplayName("잘못된 방 코드 접근 테스트(비정상)")
    public void checkRoomNotCodePasswordFailTest() throws Exception{
        String roomCode = "tussle";
        DummyRoomCheckRequestDto.Builder builder = new DummyRoomCheckRequestDto.Builder();
        DummyRoomCheckRequestDto requestDto = builder.password("1234").build();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer "+ guestToken);
        mvc.perform(post("/api/room/check/{roomCode}", roomCode)
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcRestDocumentation.document("{ClassName}/{methodName}",
                        requestHeaders(
                                headerWithName("Authorization").description("로그인 성공한 토큰 ")
                        ),
                        pathParameters(
                                parameterWithName("roomCode").description("방 코드")
                        ),
                        requestFields(
                                fieldWithPath("password").description("방 비밀번호").attributes(key("constraints").value("Not Null"))
                        )
                ));

    }

    @Test
    @DisplayName("방 생성 테스트(정상)")
    public void createRoomSuccessTest() throws Exception {
        DummyRoomCreateRequestDto.Builder builder = new DummyRoomCreateRequestDto.Builder();
        DummyRoomCreateRequestDto requestDto = builder.password("1234")
                .roomCode("tussle")
                .mode(Mode.GAME)
                .maxCount(4)
                .specification("Gradle")
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer "+guestToken);
        mvc.perform(post("/api/room")
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andDo(MockMvcRestDocumentation.document("{ClassName}/{methodName}",
                        requestHeaders(
                                headerWithName("Authorization").description("로그인 성공한 토큰 ")
                        ),
                        requestFields(
                                fieldWithPath("roomCode").description("방 코드").attributes(key("constraints").value("Not Null")),
                                fieldWithPath("password").description("방 비밀번호").attributes(key("constraints").value("Not Null")),
                                fieldWithPath("mode").description("방 모드").attributes(key("constraints").value(" game, normal 중 하나 선택")),
                                fieldWithPath("maxCount").description("방 최대 인원").attributes(key("constraints").value("최소 1명, 최대 4명")),
                                fieldWithPath("specification").description("방 규격").attributes(key("constraints").value("gradle, row 중 하나 선택"))
                        )
                )
        );
    }
    @Test
    @DisplayName("방 삭제 테스트(정상)")
    public void deleteRoomSuccessTest() throws Exception{
        DummyRoomDeleteRequestDto.Builder builder = new DummyRoomDeleteRequestDto.Builder();
        DummyRoomDeleteRequestDto requestDto = builder.roomCode("sessionA").build();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer "+guestToken);
        mvc.perform(delete("/api/room")
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andDo(MockMvcRestDocumentation.document("{ClassName}/{methodName}",
                        requestHeaders(
                                headerWithName("Authorization").description("로그인 성공한 토큰 ")
                        ),
                        requestFields(
                                fieldWithPath("roomCode").description("방 코드").attributes(key("constraints").value("Not Null"))
                        )));
    }
}
