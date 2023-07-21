package com.reminiscence.member.dto;

import com.reminiscence.domain.Member;
import com.reminiscence.domain.Role;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@NoArgsConstructor
public class MemberResponseDto {

    @Column(name="email", nullable = false)
    private String email;
    @Column(name="password", nullable = false)
    private String password;
    @Column(name="nickname", nullable = false)
    private String nickname;
    @Enumerated(EnumType.STRING)
    @Column(name="role", nullable = false)
    private Role role;
    @Column(name = "gender_fm")
    private char genderFm;
    @Column(name="age")
    private int age;
    @Column(name="name")
    private String name;
    @Column(name="profile")
    private String profile;
    @Column(name = "sns_id")
    private String snsId;
    @Column(name = "sns_type")
    private String snsType;
    @Column(name = "personal_agreement_yn")
    private char personalAgreement;

    @Builder
    public MemberResponseDto(String email, String password, String nickname, Role role, char genderFm, int age, String name, String profile, String snsId, String snsType, char personalAgreement) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
        this.genderFm = genderFm;
        this.age = age;
        this.name = name;
        this.profile = profile;
        this.snsId = snsId;
        this.snsType = snsType;
        this.personalAgreement = personalAgreement;
    }

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .role(role)
                .genderFm(genderFm)
                .age(age)
                .name(name)
                .profile(profile)
                .snsId(snsId)
                .snsType(snsType)
                .personalAgreement(personalAgreement)
                .build();
    }
}
