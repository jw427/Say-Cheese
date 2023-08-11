package com.reminiscence.message.custom_message;

import com.reminiscence.message.ResponseMessage;
import lombok.Getter;

@Getter
public enum MemberResponseMessage implements ResponseMessage {

    MEMBER_JOIN_SUCCESS( "회원 가입이 완료되었습니다."),
    MEMBER_JOIN_FAILURE_EAMIL_DUPLICATED( "아이디가 중복됩니다."),
    MEMBER_JOIN_FAILURE_NICKNAME_DUPLICATED( "닉네임이 중복됩니다."),
    MEMBER_UPDATE_SUCCESS( "회원 정보 수정이 완료되었습니다."),
    MEMBER_ID_CHECK_SUCCESS( "사용 가능한 아이디입니다."),
    MEMBER_ID_CHECK_FAILURE( "이미 사용중인 아이디입니다."),
    MEMBER_NICKNAME_CHECK_SUCCESS( "사용 가능한 닉네임입니다."),
    MEMBER_NICKNAME_CHECK_FAILURE( "이미 사용중인 닉네임입니다."),
    MEMBER_DELETE_SUCCESS( "회원 탈퇴가 완료되었습니다.");


    private final String message;

    private MemberResponseMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}