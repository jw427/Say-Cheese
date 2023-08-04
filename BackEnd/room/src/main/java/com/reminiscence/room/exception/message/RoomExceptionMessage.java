package com.reminiscence.room.exception.message;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum RoomExceptionMessage {
    NOT_FOUND_ROOM("해당 방을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    NOT_MATCH_PASSWORD("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus httpStatus;

    RoomExceptionMessage(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
