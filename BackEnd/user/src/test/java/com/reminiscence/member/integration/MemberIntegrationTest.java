package com.reminiscence.member.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.reminiscence.filter.JwtProperties;
import com.reminiscence.member.dto.MemberInfoUpdateRequestDto;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.reminiscence.JwtService;
import com.reminiscence.domain.Member;
import com.reminiscence.domain.Role;
import com.reminiscence.member.dto.MemberJoinRequestDto;
import com.reminiscence.member.dto.MemberLoginRequestDto;
import com.reminiscence.member.repository.MemberRepository;
import com.reminiscence.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
@Transactional
@Slf4j
public class MemberIntegrationTest {

    @Autowired
    MemberService memberService;
    MockMvc mvc; // mockMvc 생성

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    String adminToken;

    String memberToken;

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private Environment env;
    @Autowired
    private WebApplicationContext applicationContext;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @BeforeEach
    public void init(RestDocumentationContextProvider restDocumentation) throws SQLException {
        mvc= MockMvcBuilders.webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .apply(documentationConfiguration(restDocumentation).operationPreprocessors()
                        .withRequestDefaults(prettyPrint())
                        .withResponseDefaults(prettyPrint()))
                .build();
//        Member admin=memberRepository.findById(1L).orElse(null);
//        Member member=memberRepository.findById(2L).orElse(null);
//        adminToken= JWT.create()
//                .withClaim("memberId",String.valueOf(admin.getId()))
//                .sign(Algorithm.HMAC512(env.getProperty("jwt.secret")));
//        memberToken= JWT.create()
//                .withClaim("memberId",String.valueOf(member.getId()))
//                .sign(Algorithm.HMAC512(env.getProperty("jwt.secret")));

        memberRepository.deleteAll();
        // H2 Database에서 auto_increment 값을 초기화하는 쿼리 실행
        jdbcTemplate.execute("ALTER TABLE member ALTER COLUMN id RESTART WITH 1");
    }

    @Test
    @DisplayName("회원가입 성공 테스트")
    public void testJoinMemberSuccess() throws Exception {
        //given
        String email = "b088081@gmail.com";
        String password = "1234";
        String nickname = "검정";
        char genderFm = 'F';
        int age = 31;
        String name = "고무신";
        String profile = "xxxxxxx";
        String snsId = "nosns";
        String snsType = "facebook";

        MemberJoinRequestDto memberJoinRequestDto = MemberJoinRequestDto.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .genderFm(genderFm)
                .age(age)
                .name(name)
                .profile(profile)
                .snsId(snsId)
                .snsType(snsType)
                .build();

        mvc.perform(post("/api/member/join")
                .content(objectMapper.writeValueAsString(memberJoinRequestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // 응답 status를 ok로 테스트
                .andDo(MockMvcRestDocumentation.document("{ClassName}/{methodName}"));

        //when
        List<Member> membersList = memberRepository.findAll();

        //then
        Member member = membersList.get(0);
        assertThat(member.getEmail()).isEqualTo(email);
        assertThat(bCryptPasswordEncoder.matches(password, member.getPassword())).isTrue();
        assertThat(member.getNickname()).isEqualTo(nickname);
        assertThat(member.getRole()).isEqualTo(Role.MEMBER);
        assertThat(member.getGenderFm()).isEqualTo(genderFm);
        assertThat(member.getAge()).isEqualTo(age);
        assertThat(member.getName()).isEqualTo(name);
        assertThat(member.getProfile()).isEqualTo(profile);
        assertThat(member.getSnsId()).isEqualTo(snsId);
        assertThat(member.getSnsType()).isEqualTo(snsType);
    }

    @Test
    @DisplayName("회원가입 실패 테스트_이메일 중복")
    public void testJoinMemberFailure_DuplicatedEmail() throws Exception {
        //given
        String email = "b088081@gmail.com";
        String password = "1234";
        String nickname = "검정";
        char genderFm = 'F';
        int age = 31;
        String name = "고무신";
        String profile = "xxxxxxx";
        String snsId = "nosns";
        String snsType = "facebook";

        memberRepository.save(MemberJoinRequestDto.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .genderFm(genderFm)
                .age(age)
                .name(name)
                .profile(profile)
                .snsId(snsId)
                .snsType(snsType)
                .build().toEntity());

        MemberJoinRequestDto memberJoinRequestDto = MemberJoinRequestDto.builder()
                .email(email)
                .password("password")
                .nickname("nickname")
                .genderFm('F')
                .age(age)
                .name(name)
                .profile(profile)
                .snsId(snsId)
                .snsType(snsType)
                .build();

        mvc.perform(post("/api/member/join")
                        .content(objectMapper.writeValueAsString(memberJoinRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAlreadyReported())// 응답 status를 alreadyReported로 테스트
                .andDo(MockMvcRestDocumentation.document("{ClassName}/{methodName}"));


        //when
        List<Member> membersList = memberRepository.findAll();

        //then
        assertThat(membersList.size()).isOne();
    }

    @Test
    @DisplayName("회원가입 실패 테스트_닉네임 중복")
    public void testJoinMemberFailure_DuplicatedNickname() throws Exception {
        //given
        String email = "b088081@gmail.com";
        String password = "1234";
        String nickname = "검정";
        char genderFm = 'F';
        int age = 31;
        String name = "고무신";
        String profile = "xxxxxxx";
        String snsId = "nosns";
        String snsType = "facebook";

        memberRepository.save(MemberJoinRequestDto.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .genderFm(genderFm)
                .age(age)
                .name(name)
                .profile(profile)
                .snsId(snsId)
                .snsType(snsType)
                .build().toEntity());

        MemberJoinRequestDto memberJoinRequestDto = MemberJoinRequestDto.builder()
                .email("email")
                .password("password")
                .nickname(nickname)
                .genderFm('F')
                .age(age)
                .name(name)
                .profile(profile)
                .snsId(snsId)
                .snsType(snsType)
                .build();

        mvc.perform(post("/api/member/join")
                        .content(objectMapper.writeValueAsString(memberJoinRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict()) // 응답 status를 conflict로 테스트
                .andDo(MockMvcRestDocumentation.document("{ClassName}/{methodName}"));

        //when
        List<Member> membersList = memberRepository.findAll();

        //then
        assertThat(membersList.size()).isOne();
    }


    @Test
    @DisplayName("아이디(이메일) 중복확인 성공 테스트")
    public void testIdCheckSuccess() throws Exception {
        //given
        String email = "b088081@gmail.com";
        String password = "1234";
        String nickname = "검정";
        char genderFm = 'F';
        int age = 31;
        String name = "고무신";
        String profile = "xxxxxxx";
        String snsId = "nosns";
        String snsType = "facebook";

        memberRepository.save(Member.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .genderFm(genderFm)
                .age(age)
                .name(name)
                .profile(profile)
                .snsId(snsId)
                .snsType(snsType)
                .build());

        mvc.perform(get("/join/{email}/id-check", "another-email")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // 응답 status를 ok로 테스트
                .andDo(MockMvcRestDocumentation.document("{ClassName}/{methodName}"));
    }

    @Test
    @DisplayName("아이디(이메일) 중복확인 실패 테스트")
    public void testIdCheckFailure() throws Exception {
        //given
        String email = "b088081@gmail.com";
        String password = "1234";
        String nickname = "검정";
        char genderFm = 'F';
        int age = 31;
        String name = "고무신";
        String profile = "xxxxxxx";
        String snsId = "nosns";
        String snsType = "facebook";

        memberRepository.save(Member.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .genderFm(genderFm)
                .age(age)
                .name(name)
                .profile(profile)
                .snsId(snsId)
                .snsType(snsType)
                .build());

        mvc.perform(get("/join/{email}/id-check", email)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()) // 응답 status를 BadRequest로 테스트
                .andDo(MockMvcRestDocumentation.document("{ClassName}/{methodName}"));
    }

    @Test
    @DisplayName("닉네임 중복확인 성공 테스트")
    public void testNicknameCheckSuccess() throws Exception {
        //given
        String email = "b088081@gmail.com";
        String password = "1234";
        String nickname = "검정";
        char genderFm = 'F';
        int age = 31;
        String name = "고무신";
        String profile = "xxxxxxx";
        String snsId = "nosns";
        String snsType = "facebook";

        memberRepository.save(Member.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .genderFm(genderFm)
                .age(age)
                .name(name)
                .profile(profile)
                .snsId(snsId)
                .snsType(snsType)
                .build());

        mvc.perform(get("/join/{email}/nickname-check", "another-nickname")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // 응답 status를 ok로 테스트
                .andDo(MockMvcRestDocumentation.document("{ClassName}/{methodName}"));
    }

    @Test
    @DisplayName("닉네임 중복확인 실패 테스트")
    public void testNicknameCheckFailure() throws Exception {
        //given
        String email = "b088081@gmail.com";
        String password = "1234";
        String nickname = "검정";
        char genderFm = 'F';
        int age = 31;
        String name = "고무신";
        String profile = "xxxxxxx";
        String snsId = "nosns";
        String snsType = "facebook";

        memberRepository.save(Member.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .genderFm(genderFm)
                .age(age)
                .name(name)
                .profile(profile)
                .snsId(snsId)
                .snsType(snsType)
                .build());

        mvc.perform(get("/join/{email}/nickname-check", nickname)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()) // 응답 status를 BadRequest로 테스트
                .andDo(MockMvcRestDocumentation.document("{ClassName}/{methodName}"));
    }

    @Test
    @DisplayName("로그인 성공")
    public void testLoginSuccess() throws Exception {
        //given
        String email = "b088081@gmail.com";
        String password = "1234";
        String nickname = "검정";
        char genderFm = 'F';
        int age = 31;
        String name = "고무신";
        String profile = "xxxxxxx";
        String snsId = "nosns";
        String snsType = "facebook";

        memberRepository.save(Member.builder()
                .email(email)
                .password(bCryptPasswordEncoder.encode(password))
                .nickname(nickname)
                .role(Role.MEMBER)
                .genderFm(genderFm)
                .age(age)
                .name(name)
                .profile(profile)
                .snsId(snsId)
                .snsType(snsType)
                .build());

        MemberLoginRequestDto memberLoginRequestDto = MemberLoginRequestDto.builder()
                .email(email)
                .password(password)
                .build();

//        assertThat(memberService.login(memberLoginRequestDto)).isNotNull();

        mvc.perform(post("/api/login")
                        .content(objectMapper.writeValueAsString(memberLoginRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // 응답 status를 ok로 테스트
                .andExpect(header().exists(HttpHeaders.AUTHORIZATION))
                .andDo(MockMvcRestDocumentation.document("{ClassName}/{methodName}"));
//                .andExpect(jsonPath(email).value(email))
//                .andExpect(jsonPath(password).value(password));

//        objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);;
//        objectMapper.registerModule(new JavaTimeModule());
//        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

//        MemberResponseDto memberResponseDto = objectMapper.convertValue(memberRepository.findByEmailAndPassword(memberLoginRequestDto.getEmail(), memberLoginRequestDto.getPassword()), MemberResponseDto.class);
//        assertThat(memberResponseDto).isNotNull();
    }


    @Test
    @DisplayName("로그인 실패")
    public void testLoginFail() throws Exception {
        //given
        String email = "b088081@gmail.com";
        String password = "1234";
        String nickname = "검정";

        memberRepository.save(Member.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .role(Role.MEMBER)
                .build());

        MemberLoginRequestDto memberLoginRequestDto = MemberLoginRequestDto.builder()
                .email(email)
                .password("incorrect_password")
                .build();

        mvc.perform(post("/api/login")
                        .content(objectMapper.writeValueAsString(memberLoginRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized()) // 응답 status를 unauthorized로 테스트
                .andExpect(header().doesNotExist(HttpHeaders.AUTHORIZATION))
                .andDo(MockMvcRestDocumentation.document("{ClassName}/{methodName}"));

//        objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);;
//        objectMapper.registerModule(new JavaTimeModule());
//        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//        MemberResponseDto memberResponseDto = objectMapper.convertValue(memberRepository.findByEmailAndPassword(memberLoginRequestDto.getEmail(), memberLoginRequestDto.getPassword()), MemberResponseDto.class);
//
//        assertThat(memberResponseDto).isNull();
    }

    @Test
    @DisplayName("회원정보 검색")
    public void testGetMemberInfo() throws Exception {
        //given
        String email = "b088081@gmail.com";
        String password = "1234";
        String nickname = "검정";
        char genderFm = 'F';
        int age = 31;
        String name = "고무신";
        String profile = "xxxxxxx";
        String snsId = "nosns";
        String snsType = "facebook";

        memberRepository.save(Member.builder()
                .email(email)
                .password(bCryptPasswordEncoder.encode(password))
                .nickname(nickname)
                .role(Role.MEMBER)
                .genderFm(genderFm)
                .age(age)
                .name(name)
                .profile(profile)
                .snsId(snsId)
                .snsType(snsType)
                .build());


        MemberLoginRequestDto memberLoginRequestDto = MemberLoginRequestDto.builder()
                .email(email)
                .password(password)
                .build();


        MvcResult result = mvc.perform(post("/api/login")
                        .content(objectMapper.writeValueAsString(memberLoginRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // 응답 status를 ok로 테스트
                .andDo(MockMvcRestDocumentation.document("{ClassName}/{methodName}"))
                .andReturn();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", result.getResponse().getHeader(JwtProperties.HEADER_STRING));

        mvc.perform(get("/api/member/info")
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // 응답 status를 ok로 테스트
                .andDo(MockMvcRestDocumentation.document("{ClassName}/{methodName}"));

        //when
        List<Member> membersList = memberRepository.findAll();

        //then
        Member member = membersList.get(0);
        assertThat(member.getEmail()).isEqualTo(email);
        assertThat(bCryptPasswordEncoder.matches(password, member.getPassword())).isTrue();
        assertThat(member.getNickname()).isEqualTo(nickname);
        assertThat(member.getRole()).isEqualTo(Role.MEMBER);
        assertThat(member.getGenderFm()).isEqualTo(genderFm);
        assertThat(member.getAge()).isEqualTo(age);
        assertThat(member.getName()).isEqualTo(name);
        assertThat(member.getProfile()).isEqualTo(profile);
        assertThat(member.getSnsId()).isEqualTo(snsId);
        assertThat(member.getSnsType()).isEqualTo(snsType);
    }
    @Test
    @DisplayName("회원정보 수정")
    public void testUpdateMemberInfo() throws Exception {
        //given
        String email = "b088081@gmail.com";
        String password = "1234";
        String nickname = "검정";
        char genderFm = 'F';
        int age = 31;
        String name = "고무신";
        String profile = "xxxxxxx";
        String snsId = "nosns";
        String snsType = "facebook";

        memberRepository.save(Member.builder()
                .email(email)
                .password(bCryptPasswordEncoder.encode(password))
                .nickname("nickname")
                .role(Role.MEMBER)
                .genderFm('F')
                .age(30)
                .name("name")
                .profile("profile")
                .snsId("snsId")
                .snsType("snsType")
                .build());

        MemberLoginRequestDto memberLoginRequestDto = MemberLoginRequestDto.builder()
                .email(email)
                .password(password)
                .build();

        MvcResult result = mvc.perform(post("/api/login")
                        .content(objectMapper.writeValueAsString(memberLoginRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // 응답 status를 ok로 테스트
                .andDo(MockMvcRestDocumentation.document("{ClassName}/{methodName}"))
                .andReturn();

        MemberInfoUpdateRequestDto memberInfoUpdateRequestDto = MemberInfoUpdateRequestDto.builder()
                .password(password)
                .nickname(nickname)
                .genderFm(genderFm)
                .age(age)
                .name(name)
                .profile(profile)
                .snsId(snsId)
                .snsType(snsType)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", result.getResponse().getHeader(JwtProperties.HEADER_STRING));
        mvc.perform(put("/api/member/modify")
                        .headers(headers)
                        .content(objectMapper.writeValueAsString(memberInfoUpdateRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // 응답 status를 ok로 테스트
                .andDo(MockMvcRestDocumentation.document("{ClassName}/{methodName}"));

        //when
        List<Member> membersList = memberRepository.findAll();

        //then
        Member member = membersList.get(0);
        assertThat(member.getEmail()).isEqualTo(email);
        assertThat(bCryptPasswordEncoder.matches(password, member.getPassword())).isTrue();
        assertThat(member.getNickname()).isEqualTo(nickname);
        assertThat(member.getRole()).isEqualTo(Role.MEMBER);
        assertThat(member.getGenderFm()).isEqualTo(genderFm);
        assertThat(member.getAge()).isEqualTo(age);
        assertThat(member.getName()).isEqualTo(name);
        assertThat(member.getProfile()).isEqualTo(profile);
        assertThat(member.getSnsId()).isEqualTo(snsId);
        assertThat(member.getSnsType()).isEqualTo(snsType);
    }

    @Test
    @DisplayName("계정삭제 성공")
    public void testDeleteMemberSuccess() throws Exception {
        //given
        String email = "b088081@gmail.com";
        String password = "1234";

        memberRepository.save(Member.builder()
                .email(email)
                .password(bCryptPasswordEncoder.encode(password))
                .nickname("검정")
                .role(Role.MEMBER)
                .genderFm('F')
                .age(31)
                .name("고무신")
                .profile("xxxxxxxx")
                .delYn('N')
                .snsId("nosns")
                .snsType("facebook")
                .build());

        MemberLoginRequestDto memberLoginRequestDto = MemberLoginRequestDto.builder()
                .email(email)
                .password(password)
                .build();

        MvcResult result = mvc.perform(post("/api/login")
                        .content(objectMapper.writeValueAsString(memberLoginRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // 응답 status를 ok로 테스트
                .andDo(MockMvcRestDocumentation.document("{ClassName}/{methodName}"))
                .andReturn();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", result.getResponse().getHeader(JwtProperties.HEADER_STRING));
        mvc.perform(delete("/api/member/delete")
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // 응답 status를 ok로 테스트
                .andDo(MockMvcRestDocumentation.document("{ClassName}/{methodName}"));

        //when
        List<Member> membersList = memberRepository.findAll();

        //then
        Member member = membersList.get(0);
        assertThat(member.getDelYn()).isEqualTo('Y');
        assertThat(member.getEmail()).isEqualTo(email);
    }

    @Test
    @DisplayName("계정삭제 실패")
    public void testDeleteMemberFailure() throws Exception {
        //given
        String email = "b088081@gmail.com";
        String password = "1234";

        memberRepository.save(Member.builder()
                .email(email)
                .password(bCryptPasswordEncoder.encode(password))
                .nickname("검정")
                .role(Role.MEMBER)
                .genderFm('F')
                .age(31)
                .name("고무신")
                .profile("xxxxxxxx")
                .delYn('N')
                .snsId("nosns")
                .snsType("facebook")
                .build());

        mvc.perform(delete("/api/member/delete")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized()) // 응답 status를 ok로 테스트
                .andDo(MockMvcRestDocumentation.document("{ClassName}/{methodName}"));

        //when
        List<Member> membersList = memberRepository.findAll();

        //then
        Member member = membersList.get(0);
        assertThat(member.getDelYn()).isNotEqualTo('Y');
        assertThat(member.getEmail()).isEqualTo(email);
    }

    @Test
    @DisplayName("회원명단불러오기")
    public void testGetMembersList() throws Exception {
        //given
        String email = "memories";
        String nickname = "검정";

        memberRepository.save(Member.builder()
                .email("memories1@gmail.com")
                .password(bCryptPasswordEncoder.encode("1234"))
                .nickname("검정")
                .role(Role.MEMBER)
                .genderFm('F')
                .age(31)
                .name("고무신")
                .profile("xxxxxxxx")
                .delYn('N')
                .snsId("nosns")
                .snsType("facebook")
                .build());


        memberRepository.save(Member.builder()
                .email("memories2@gmail.com")
                .password(bCryptPasswordEncoder.encode("1234"))
                .nickname("빨강")
                .role(Role.MEMBER)
                .genderFm('F')
                .age(31)
                .name("고무신")
                .profile("xxxxxxxx")
                .delYn('N')
                .snsId("nosns")
                .snsType("facebook")
                .build());


        memberRepository.save(Member.builder()
                .email("memory1@gmail.com")
                .password(bCryptPasswordEncoder.encode("1234"))
                .nickname("검정색")
                .role(Role.MEMBER)
                .genderFm('F')
                .age(31)
                .name("고무신")
                .profile("xxxxxxxx")
                .delYn('N')
                .snsId("nosns")
                .snsType("facebook")
                .build());

        // 이메일로 검색 시
        MvcResult result1 = mvc.perform(get("/api/member/search-member/{email-nickname}", email)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // 응답 status를 ok로 테스트
                .andDo(MockMvcRestDocumentation.document("{ClassName}/{methodName}"))
                .andReturn();

        //when
        String response1 = result1.getResponse().getContentAsString();
        List<Member> membersList1 = objectMapper.readValue(response1, new TypeReference<List<Member>>(){});

        //then
        assertThat(membersList1.size()).isEqualTo(2);
        Member member_email1 = membersList1.get(0);
        assertThat(member_email1.getEmail()).containsIgnoringCase(email);
        Member member_email2 = membersList1.get(1);
        assertThat(member_email2.getEmail()).containsIgnoringCase(email);

        // 닉네임으로 검색 시
        MvcResult result2 = mvc.perform(get("/api/member/search-member/{email-nickname}", nickname)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcRestDocumentation.document("{ClassName}/{methodName}"))
                .andReturn(); // 응답 status를 ok로 테스트

        //when
        String response2 = result2.getResponse().getContentAsString();
        List<Member> membersList2 = objectMapper.readValue(response2, new TypeReference<List<Member>>(){});

        //then
        assertThat(membersList2.size()).isEqualTo(2);
        Member member_nickname1 = membersList2.get(0);
        assertThat(member_nickname1.getNickname()).containsIgnoringCase(nickname);
        Member member_nickname2 = membersList2.get(1);
        assertThat(member_nickname2.getNickname()).containsIgnoringCase(nickname);
    }

    @Test
    @DisplayName("로그아웃 성공 후 회원 정보 수정 실패")
    public void testLogoutSuccess() throws Exception {
        //given
        String email = "b088081@gmail.com";
        String password = "1234";
        String nickname = "검정";
        Role role = Role.MEMBER;
        char genderFm = 'F';
        int age = 31;
        String name = "고무신";
        String profile = "xxxxxxx";
        String snsId = "nosns";
        String snsType = "facebook";

        memberRepository.save(Member.builder()
                .email(email)
                .password(bCryptPasswordEncoder.encode(password))
                .nickname("빨강")
                .role(Role.MEMBER)
                .genderFm('M')
                .age(34)
                .name("나막신")
                .profile("yyyyyyyyyy")
                .snsId("snsno")
                .snsType("twitter")
                .build());

        MemberLoginRequestDto memberLoginRequestDto = MemberLoginRequestDto.builder()
                .email(email)
                .password(password)
                .build();

        MvcResult loginResult = mvc.perform(post("/api/login")
                        .content(objectMapper.writeValueAsString(memberLoginRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // 응답 status를 ok로 테스트
                .andDo(MockMvcRestDocumentation.document("{ClassName}/{methodName}"))
                .andReturn();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", loginResult.getResponse().getHeader(JwtProperties.HEADER_STRING));


        MvcResult logoutResult = mvc.perform(get("/logout")
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // 응답 status를 ok로 테스트
                .andDo(MockMvcRestDocumentation.document("{ClassName}/{methodName}"))
                .andReturn();

        MemberInfoUpdateRequestDto memberInfoUpdateRequestDto = MemberInfoUpdateRequestDto.builder()
                .password(password)
                .nickname(nickname)
                .genderFm(genderFm)
                .age(age)
                .name(name)
                .profile(profile)
                .snsId(snsId)
                .snsType(snsType)
                .build();

        headers.clear();
        headers.add("Authorization", logoutResult.getResponse().getHeader(JwtProperties.HEADER_STRING));

        mvc.perform(put("/api/member/modify")
                        .headers(headers)
                        .content(objectMapper.writeValueAsString(memberInfoUpdateRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized()) // 응답 status를 ok로 테스트
                .andDo(MockMvcRestDocumentation.document("{ClassName}/{methodName}"));

        //when
        List<Member> membersList = memberRepository.findAll();

        //then
        Member member = membersList.get(0);
        assertThat(member.getNickname()).isNotEqualTo(nickname);
        assertThat(member.getGenderFm()).isNotEqualTo(genderFm);
    }
}