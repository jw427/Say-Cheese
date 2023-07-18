package com.reminiscence.article.domain;

import com.reminiscence.article.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Table(uniqueConstraints = {@UniqueConstraint(name="Unique_nickname",columnNames = {"nickname"}),
                            @UniqueConstraint(name="Unique_email",columnNames = {"email"})})
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="email", nullable = false)
    private String email;
    @Column(name="password", nullable = false)
    private String password;
    @Column(name="nickname", nullable = false)
    private String nickname;
    @Enumerated(EnumType.STRING)
    @Column(name="role", nullable = false)
    private Role role;
    @Column(name="gender_fm")
    private Character genderFm;
    @Column(name="age")
    private Integer age;
    @Column(name="name")
    private String name;

    @Column(name="profile")
    private String profile;

    @Column(name="sns_id")
    private String snsId;

    @Column(name="sns_type")
    private String snsType;

    @Column(name="personal_agreement_yn")
    private Character personalAgreement;

}
